/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.dao;

import com.tyrin.beans.Category;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tyrin V. S.
 */
@Repository
public class CategoryDao {

    @Autowired
    private JdbcTemplate template;
    private static final Log log = LogFactory.getLog(CategoryDao.class);

    class CategoryMapper implements RowMapper<Category> {

        @Override
        public Category mapRow(ResultSet rs, int i) throws SQLException {
            Category cat = new Category();
            cat.setId(rs.getInt("id"));
            cat.setParentId(rs.getInt("parent_id"));
            cat.setName(rs.getString("name"));
            return cat;
        }
    }

    public Category getCategory(int catId) {
        return template.queryForObject(
                "SELECT id, parent_id, name "
                + "FROM Category WHERE id = ?",
                new CategoryMapper(), catId);
    }

    public void deleteCategory(int catId) {
        String sql = "SELECT DISTINCT t1.id as cat\n"
                + "FROM Category t1\n"
                + "LEFT JOIN Category t2 ON t1.id = t2.parent_id\n"
                + "LEFT JOIN Category t3 ON t2.id = t3.parent_id\n"
                + "WHERE t1.id = ? "
                + "UNION\n"
                + "SELECT DISTINCT t2.id as cat\n"
                + "FROM Category t1\n"
                + "LEFT JOIN Category t2 ON t1.id = t2.parent_id\n"
                + "LEFT JOIN Category t3 ON t2.id = t3.parent_id\n"
                + "WHERE t1.id = ? "
                + "UNION\n"
                + "SELECT DISTINCT t3.id as cat\n"
                + "FROM Category t1\n"
                + "JOIN Category t2 ON t1.id = t2.parent_id\n"
                + "JOIN Category t3 ON t2.id = t3.parent_id\n"
                + "WHERE t1.id = ? ";
        ArrayList<Integer> listCategoriesWithChildren = template.queryForObject(sql,
                new RowMapper<ArrayList<Integer>>() {
                    @Override
                    public ArrayList<Integer> mapRow(ResultSet rs, int i) throws SQLException {
                        ArrayList<Integer> listId = new ArrayList<>();
                        do {
                            listId.add(rs.getInt("cat"));
                        } while (rs.next());

                        return listId;
                    }
                }, catId, catId, catId);

        for (Integer id : listCategoriesWithChildren) {
            template.update("DELETE FROM Product WHERE prod_cat_id = ?", id);
            template.update("DELETE FROM Category WHERE id = ?", id);
        }
    }

    public void updateCategory(Category cat) {
        template.update("UPDATE Category "
                + "SET parent_id = ?, name = ? "
                + "WHERE id = ?",
                cat.getParentId(), cat.getName(), cat.getId());
    }

    public List<Category> getAllCategory() {
        return template.query(
                "SELECT id, parent_id, name "
                + "FROM Category",
                new CategoryMapper());
    }

    public Map<String, String> getCategoryTree() {
        return template.queryForObject(
                "SELECT t1.name as k, t2.name as v\n"
                + "FROM Category t1, Category t2\n"
                + "WHERE t2.id = t1.parent_id\n"
                + "UNION\n"
                + "SELECT name, 'Verhnij'\n"
                + "FROM Category\n"
                + "WHERE parent_id = 0 "
                + "ORDER BY v",
                new RowMapper<Map<String, String>>() {
                    @Override
                    public Map<String, String> mapRow(ResultSet rs, int i) throws SQLException {
                        Map<String, String> map = new HashMap<>();
                        do {
                            map.put(rs.getString("k"), rs.getString("v"));
                        } while (rs.next());
                        return map;
                    }
                });
    }

    public void addCategory(Category cat) {
        String sql = "INSERT INTO Category (id, parent_id, name)\n"
                + "             VALUES (?, ?, ?)";
//        String sql = "SELECT @id = (SELECT generic_id FROM GenID WHERE tab = 'Category')\n"
//                + "		update GenID set generic_id = generic_id + 1 WHERE tab = 'Category'\n"
//                + "INSERT INTO Category (cat_id, parent_id, subcat)\n"
//                + "             VALUES (@id, @parent_id, @subcat)";
        template.update(sql, 2000,
                cat.getName(), cat.getParentId());
    }

    public List<Category> getChidrenFor(int parentCategory) {
        return template.query(
                "SELECT id, parent_id, name "
                + "FROM Category "
                + "WHERE parent_id = ?",
                new CategoryMapper(), parentCategory);
    }

    public Map<Category, List<Category>> buildTreeForWeb(int parent) {
        Map<Category, List<Category>> map = new LinkedHashMap<>();
        List<Category> listOfChildren = getChidrenFor(parent);
        if (listOfChildren.isEmpty() & parent != 0) {
            map.put(getCategory(parent), null);
            return map;
        } else {
            for (Category cat : listOfChildren) {
                map.put(cat, getChidrenFor(cat.getId()));
            }
            return map;
        }
    }
}
