package Entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WebsiteRowMapper implements RowMapper<Website> {
    @Override
    public Website mapRow(ResultSet rs, int rowNum) throws SQLException {
        Website website = new Website();

        website.setId(rs.getInt("id"));
        website.setUrl(rs.getString("url"));
        website.setSiteName(rs.getString("site_Name"));
        website.setActive(rs.getBoolean("active"));
        website.setCreatedAt(rs.getTimestamp("created_At").toLocalDateTime());

        return website;
    }
}
