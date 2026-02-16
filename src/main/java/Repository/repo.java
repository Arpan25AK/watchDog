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

    public void addWebsite(){
        String sql = "INSERT INTO websites (url,site_name) VALUES (?,?)";
        jdbcTemplate.update(sql, url, name);
    }

}
