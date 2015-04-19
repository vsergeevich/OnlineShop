/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.dao;

import com.tyrin.beans.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tyrin V. S.
 */
@Repository
public class ProductDao {

    @Autowired
    private JdbcTemplate template;
    private static final Log log = LogFactory.getLog(ProductDao.class);

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    class ProductMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs, int i) throws SQLException {
            Product prod = new Product();
            prod.setId(rs.getInt("prod_id"));
            prod.setName(rs.getString("prod_name"));
            prod.setCatId(rs.getInt("prod_cat_id"));
            prod.setManId(rs.getInt("prod_man_id"));
            prod.setAvailable(rs.getByte("prod_avail"));
            prod.setDesc(rs.getString("prod_desc"));
            prod.setPrice(rs.getDouble("prod_price"));
            prod.setImage(rs.getString("prod_image"));
            return prod;
        }
    }

    public List<Product> getAllProduct(int page, int pageSize) {
        String sql = "SELECT prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image "
                + "FROM Product ORDER BY prod_id LIMIT ? OFFSET ?";
        return template.query(sql, new ProductMapper(), pageSize, (page - 1) * pageSize);
    }

    public List<Product> getAllProduct() {
        String sql = "SELECT prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image "
                + "FROM Product ORDER BY prod_id";
        return template.query(sql, new ProductMapper());
    }

    public Product getProduct(int prodId) {
        return template.queryForObject(
                "SELECT prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image "
                + "FROM Product WHERE prod_id = ?",
                new ProductMapper(), prodId);
    }

    public void addProduct(Product prod) {
        String sql = "insert into Product (prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image)\n"
                + "values(4000, :cat, :man, :name, :price, :desc, :avail, :img)";
        namedParameterJdbcTemplate.update(sql,
                getParemetrsForRequest(prod));
    }

    public void updateProduct(Product prod) {
        namedParameterJdbcTemplate.update("UPDATE Product "
                + "SET prod_cat_id = :cat, "
                + "prod_man_id = :man, "
                + "prod_name = :name, "
                + "prod_price = :price, "
                + "prod_desc = :desc, "
                + "prod_avail = :avail, "
                + "prod_image = :img "
                + "WHERE prod_id = :id", getParemetrsForRequest(prod));
    }

    public void deleteProduct(int prodId) {
        template.update("DELETE FROM Orders WHERE order_prodID = ?", prodId);
        template.update("DELETE FROM Product WHERE prod_id = ?", prodId);
    }

    public List<Product> getProductByCategory(int catId) {
        return template.query(
                "SELECT prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image "
                + "FROM Product WHERE prod_cat_id = ?",
                new ProductMapper(), catId);
    }

    public List<Product> getProductByManufacturer(int man_id) {
        return template.query(
                "SELECT prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image "
                + "FROM Product WHERE prod_man_id = ?",
                new ProductMapper(), man_id);
    }

    public List<Product> getProductByManufacturer(int page, int pageSize, int man_id) {
        return template.query(
                "SELECT prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image "
                + "FROM Product WHERE prod_man_id = ?  LIMIT ? OFFSET ?",
                new ProductMapper(), man_id, pageSize, (page - 1) * pageSize);
    }

    public List<Product> searchProductOnPrice(int low, int high) {
        String sql = "SELECT prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, "
                + "prod_desc, prod_avail, prod_image FROM Product WHERE prod_price BETWEEN ? AND ?";
        return template.query(sql, new ProductMapper(), low, high);

    }

    public List<Product> searchProductOnPrice(int page, int pageSize, int low, int high) {
        String sql = "SELECT prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, "
                + "prod_desc, prod_avail, prod_image FROM Product WHERE prod_price BETWEEN ? AND ? LIMIT ? OFFSET ?";
        return template.query(sql, new ProductMapper(), low, high, pageSize, (page - 1) * pageSize);

    }

    public List<Product> searchProduct(String mask) {
        String sql = "SELECT prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image "
                + "FROM Product WHERE prod_name LIKE '%" + mask + "%' OR prod_desc like '%" + mask + "%'";
        return template.query(sql, new ProductMapper());
    }

    public List<Product> getProductByCategoryWithChildren(int catId) {
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

        List<Product> listProd = new LinkedList<>();
        for (int i : listCategoriesWithChildren) {
            List<Product> listProdByCat = template.query(
                    "SELECT prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image "
                    + "FROM Product WHERE prod_cat_id = ?",
                    new ProductMapper(), i);

            for (Product p : listProdByCat) {
                listProd.add(p);
            }
        }
        return listProd;
    }

    public List<Product> getProductByCategoryAndManufacturer(int catId, int manId) {
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

        List<Product> listProd = new LinkedList<>();
        for (int i : listCategoriesWithChildren) {
            List<Product> listProdByCat = template.query(
                    "SELECT prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image "
                    + "FROM Product WHERE prod_cat_id = ? AND prod_man_id = ?",
                    new ProductMapper(), i, manId);

            for (Product p : listProdByCat) {
                listProd.add(p);
            }
        }
        return listProd;
    }

    public List<Product> getProductByCategoryAndPrice(int catId, double low, double high) {
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

        List<Product> listProd = new LinkedList<>();
        for (int i : listCategoriesWithChildren) {
            List<Product> listProdByCat = template.query(
                    "SELECT prod_id, prod_cat_id, prod_man_id, prod_name, prod_price, prod_desc, prod_avail, prod_image "
                    + "FROM Product WHERE prod_cat_id = ? AND prod_price BETWEEN ? AND ?",
                    new ProductMapper(), i, low, high);

            for (Product p : listProdByCat) {
                listProd.add(p);
            }
        }
        return listProd;
    }

    /**
     * Метод разбивает бин Product на карту
     * именованных значений его полей, для
     * параметрезированного запроса в БД.
     *
     * @param Product
     * @return Map<String, Object>
     */
    private Map<String, Object> getParemetrsForRequest(Product prod) {
        Map<String, Object> parametrs = new HashMap<>();
        parametrs.put("id", prod.getId());
        parametrs.put("cat", prod.getCatId());
        parametrs.put("man", prod.getManId());
        parametrs.put("name", prod.getName());
        parametrs.put("price", prod.getPrice());
        parametrs.put("desc", prod.getDesc());
        parametrs.put("avail", prod.getAvailable());
        parametrs.put("img", prod.getImage());
        return parametrs;
    }

}
