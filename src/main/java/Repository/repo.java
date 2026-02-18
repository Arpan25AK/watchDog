package Repository;

import Entity.Websites;
import Entity.WebsiteRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class repo {
    private final JdbcTemplate jdbcTemplate;

    public repo(JdbcTemplate jdbcTemplate){
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
    }

    public List<Websites>  detailedHealthLog(){
        String sql = "select w.id, w.url, w.site_name," +
                " CASE when h.website_status = 200 then 'up' else 'down' end as status_code," +
                " h.response_time_ms from  websites as w join health_check" +
                " as h on w.id = h.website_id " +
                "order by h.created_at desc;";
        return jdbcTemplate.query(sql, new WebsiteRowMapper());
    }
}
