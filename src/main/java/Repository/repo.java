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
        return jdbcTemplate.query(sql, new WebsiteRowMapper());
    }

    public List<Websites> findDown(){
        String sql = "SELECT * FROM  websites where active = False";
        return jdbcTemplate.query(sql, new WebsiteRowMapper());
    }

    public void healthCheckLog(int website_id, int website_status, int response_time_ms){
        String sql = "INSERT INTO HealthCheck (website_id, website_status, response_time_ms) VALUES(?,?,?)";
        jdbcTemplate.update(sql, website_id, website_status, response_time_ms);
    }
}
