package com.example.watchDog.Repository;

import com.example.watchDog.Entity.Websites;
import com.example.watchDog.Entity.WebsiteRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Repo {
    private final JdbcTemplate jdbcTemplate;

    public Repo(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Websites> findAll(){
        String sql = "Select * from websites";
        return jdbcTemplate.query(sql, new WebsiteRowMapper());
    }

    public void addWebsite(String url, String name){
        String sql = "INSERT INTO websites (url,site_name) VALUES (?,?)";
        jdbcTemplate.update(sql, url, name);
    }

    public List<Websites> findActive(){
        String sql = "SELECT * FROM  websites where active = True";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Websites website = new Websites();
            website.setId(rs.getInt("id"));
            website.setUrl(rs.getString("url"));
            website.setSiteName(rs.getString("site_name"));
            website.setActive(rs.getBoolean("active"));
            // We skip website_status and created_at because we don't need them to ping the site!
            return website;
        });
    }

    public List<Websites> findDown(){
        String sql = "SELECT * FROM  websites where active = False";
        return jdbcTemplate.query(sql, new WebsiteRowMapper());
    }

    public void healthCheckLog(int website_id, int website_status, int response_time_ms){
        String sql = "INSERT INTO health_check (website_id, website_status, response_time_ms) VALUES(?,?,?)";
        jdbcTemplate.update(sql, website_id, website_status, response_time_ms);

        String delSql = "DELETE FROM health-check"+
                "WHERE website_id = ?" +
                "AND created_at <" +
                "(SELECT MAX(created_at) FROM health-check WHERE website_id = ?";
        jdbcTemplate.update(delSql , website_id, website_id);
    }

    public List<Websites>  detailedHealthLog(){
        String sql = "SELECT w.id, w.url, w.site_name, w.active, h.website_status, h.created_at " +
                "FROM websites w " +
                "JOIN health_check h ON w.id = h.website_id " +
                "ORDER BY h.created_at DESC "+
                "LIMIT 10";
        return jdbcTemplate.query(sql, new WebsiteRowMapper());
    }

    public Websites findOrCreate(String url){
        String checkSql = "SELECT * FROM Websites where url = ?";
        List<Websites> sites = jdbcTemplate.query(checkSql, (rs, rowNum) -> {
            Websites w = new Websites();
            w.setId(rs.getInt("id"));
            w.setUrl(rs.getString("url"));
            return w;
        }, url);

        if(!sites.isEmpty()){
            return sites.get(0);
        }

        String insertSql = "INSERT INTO Websites(url, site_name, active) VALUES(?, ?, true) RETURNING id";
        Integer newId =jdbcTemplate.queryForObject(insertSql, Integer.class, url);

        Websites newSite = new Websites();
        newSite.setId(newId);
        newSite.setUrl(url);
        return newSite;
    }
}
