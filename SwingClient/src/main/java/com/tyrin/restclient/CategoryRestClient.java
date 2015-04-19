/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.restclient;

import com.tyrin.beans.Category;
import com.tyrin.exceptions.DBException;
import com.tyrin.services.ICategoryService;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Tyrin V. S.
 */
public class CategoryRestClient implements ICategoryService {

    private final RestTemplate restTemplate;
    private final HttpHeaders headers;
    private final String siteUrl;

    public CategoryRestClient(String siteUrl, final String login, final String password) {
        this.siteUrl = siteUrl;
        restTemplate = new RestTemplate();
        headers = new HttpHeaders() {
            {
                String auth = login + ":" + password;
                byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encodedAuth);
                set("Authorization", authHeader);
            }
        };
    }

    @Override
    public synchronized void addCategory(Category cat) {
        String url = siteUrl + "/admin/category";
        HttpEntity<Category> request = new HttpEntity<>(cat, headers);
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            int respCode = response.getStatusCode().value();
            if (respCode >= 400) {
                throw new DBException("Server error: " + respCode);
            }
        } 
        catch (RestClientException | DBException e) {
            throw new DBException(e.getMessage());
        } 
    }

    @Override
    public synchronized Category getCategory(int id) {
        String url = "/admin/category/{id}";
        ResponseEntity<Category> response;
        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, Category.class, id);
            int respCode = response.getStatusCode().value();
            if (respCode >= 400) {
                throw new DBException("Server error: " + respCode);
            }
        } catch (RestClientException | DBException e) {
            throw new DBException(e.getMessage());
        }
        return response.getBody();
    }

    @Override
    public synchronized void updateCategory(Category cat) {
        String url = siteUrl + "/admin/category/{id}";
        ResponseEntity<String> response;
        HttpEntity<Category> request = new HttpEntity<>(cat, headers);
        try {
            response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class, cat.getId());
            int respCode = response.getStatusCode().value();
            if (respCode >= 400) {
                throw new DBException("Server error: " + respCode);
            }
        } catch (RestClientException | DBException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public synchronized List<Category> getAllCategory() {
        String url = siteUrl + "/admin/category";
        ResponseEntity<Category[]> response;
        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, Category[].class);
            int respCode = response.getStatusCode().value();
            if (respCode >= 400) {
                throw new DBException("Server error: " + respCode);
            }
        } catch (RestClientException | DBException e) {
            throw new DBException(e.getMessage());
        }
        return Arrays.asList(response.getBody());
    }

    @Override
    public synchronized Map<String, String> getCategoryTree() {
        String url = siteUrl + "/admin/category/get-tree";
        ResponseEntity<HashMap> response;
        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, HashMap.class);
            int respCode = response.getStatusCode().value();
            if (respCode >= 400) {
                throw new DBException("Server error: " + respCode);
            }
        } catch (RestClientException | DBException e) {
            throw new DBException(e.getMessage());
        }
        return response.getBody();
    }

    @Override
    public synchronized void deleteCategory(int id) {
        String url = siteUrl + "/admin/category/{id}";
        ResponseEntity<String> response;
        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            response = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class, id);
            int respCode = response.getStatusCode().value();
            if (respCode >= 400) {
                throw new DBException("Server error: " + respCode);
            }
        } catch (RestClientException | DBException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public Map<Integer, String> mapIndexes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Category, List<Category>> buildTreeForWeb(int parent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

//    @Override
//    public synchronized List<Category> getAllCategory() {
//        URL url;
//        List<Category> catList = null;
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            url = new URL(siteUrl + "/admin/category");
//            catList = mapper.readValue(url, new TypeReference<List<Category>>() {
//            });
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(CategoryRestClient.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(CategoryRestClient.class.getName()).log(Level.SEVERE, null, ex);
//            throw new DBException(ex.getMessage());
//        }
//        return catList;
//    }
//
//    @Override
//    public synchronized Map<String, String> getCategoryTree() {
//        ResponseEntity<Map> response
//                = new RestTemplate().getForEntity(siteUrl + "/admin/category/get-tree", Map.class);
//        return response.getBody();
//    }
//
//    @Override
//    public synchronized void deleteCategory(int id) {
//        URL url;
//        HttpURLConnection con = null;
//        try {
//            url = new URL(siteUrl + "/admin/category/" + id);
//            con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("DELETE");
//            con.connect();
//            if (con.getResponseCode() >= 400) {
//                throw new DBException(con.getResponseMessage());
//            }
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(ManufacturerRestClient.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(ManufacturerRestClient.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            if (con != null) con.disconnect();
//        }
//    }
//}
