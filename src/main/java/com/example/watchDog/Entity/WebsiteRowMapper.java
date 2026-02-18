package com.example.watchDog.Entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WebsiteRowMapper implements RowMapper<Websites> {
    @Override
    public Websites mapRow(ResultSet rs, int rowNum) throws SQLException {
        Websites website = new Websites();

        website.setId(rs.getInt("id"));
        website.setUrl(rs.getString("url"));
        website.setSiteName(rs.getString("site_Name"));
        website.setActive(rs.getBoolean("active"));
        int code = rs.getInt("website_status");
        website.setStatusText(code == 200 ? "up" : "down");
        if (rs.getTimestamp("created_at") != null) {
            website.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        return website;
    }
}
