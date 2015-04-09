/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.dao;

import com.tyrin.beans.Manufacturer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author Tyrin V. S.
 */
@Component
public class ManufacturerDao {

    @Autowired
    private JdbcTemplate template;
    private static final Log log = LogFactory.getLog(ManufacturerDao.class);

    class ManufacturerMapper implements RowMapper<Manufacturer> {

        @Override
        public Manufacturer mapRow(ResultSet rs, int i) throws SQLException {
            Manufacturer man = new Manufacturer();
            man.setId(rs.getInt("id"));
            man.setName(rs.getString("name"));
            man.setCountry(rs.getString("country"));
            return man;
        }
    }

    public void addManufacturer(Manufacturer man) {
        String sql = "INSERT INTO Manufacturer(id, name, country)\n" +
"             VALUES (?, ?, ?)";
        template.update(sql, 2000, man.getName(), man.getCountry());
    }

    public void deleteManufacturer(int manId) {
        template.update("DELETE FROM Product WHERE prod_man_id  = ?", manId);
        template.update("DELETE FROM Manufacturer WHERE id = ?", manId);
    }

    public Manufacturer getManufacturer(int manId) {
        return template.queryForObject(
                "SELECT id, name, country "
                + "FROM Manufacturer WHERE id = ?",
                new ManufacturerMapper(), manId);
    }

    public void updateManufactutrer(Manufacturer man) {
        template.update("UPDATE Manufacturer "
                + "SET name = ?, "
                + "country = ? "
                + "WHERE id = ?",
                man.getName(), man.getCountry(), man.getId());
    }

    public List<Manufacturer> getAllManufacturer() {
        return template.query(
                "SELECT id, name, country "
                + "FROM Manufacturer",
                new ManufacturerMapper());
    }

}
