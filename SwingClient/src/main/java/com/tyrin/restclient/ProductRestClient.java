/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.restclient;

import com.tyrin.beans.Product;
import com.tyrin.exceptions.DBException;
import com.tyrin.services.IProductService;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Tyrin V. S.
 */
public class ProductRestClient implements IProductService {

    private final RestTemplate restTemplate;
    private final HttpHeaders headers;
    private final String siteUrl;

    public ProductRestClient(String siteUrl, final String login, final String password) {
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
    public void addProduct(Product prod) {
        String url = siteUrl + "/admin/product";
        HttpEntity<Product> request = new HttpEntity<>(prod, headers);
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            int respCode = response.getStatusCode().value();
            if (respCode >= 400) {
                throw new DBException("Server error: " + respCode);
            }
        } catch (RestClientException | DBException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public void updateProduct(Product prod) {
        String url = siteUrl + "/admin/product/{id}";
        ResponseEntity<String> response;
        HttpEntity<Product> request = new HttpEntity<>(prod, headers);
        try {
            response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class, prod.getId());
            int respCode = response.getStatusCode().value();
            if (respCode >= 400) {
                throw new DBException("Server error: " + respCode);
            }
        } catch (RestClientException | DBException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public Product getProduct(int id) {
        String url = "/admin/product/{id}";
        ResponseEntity<Product> response;
        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, Product.class, id);
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
    public void deleteProduct(int id) {
        String url = siteUrl + "/admin/product/{id}";
        ResponseEntity<String> response;
        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            response = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class, id);
            int respCode = response.getStatusCode().value();
            if (respCode >= 400) {
                throw new DBException("Server error: " + respCode);
            }
        } catch (RestClientException | DBException e) {
            throw new DBException(e);
        }
    }

    @Override
    public List<Product> getProductByCategory(int id) {
        String url = siteUrl + "/admin/product/by-category/{id}";
        ResponseEntity<Product[]> response;
        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, Product[].class, id);
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
    public List<Product> getProductByManufacturer(int id) {
        String url = siteUrl + "/admin/product/by-manufacturer/{id}";
        ResponseEntity<Product[]> response;
        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, Product[].class, id);
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
    public List<Product> searchProduct(String mask) {
        String url = siteUrl + "/admin/product/search/{mask}";
        ResponseEntity<Product[]> response;
        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, Product[].class, mask);
            int respCode = response.getStatusCode().value();
            if (respCode >= 400) {
                throw new DBException("Server error: " + respCode);
            }
        } catch (HttpServerErrorException e) {
            throw new DBException(e.getMessage());
        }
        return Arrays.asList(response.getBody());
    }

    @Override
    public List<Product> getProductsByPrice(int low, int high) {
        String url = siteUrl + "/admin/product/on-price/{low}/{high}";
        ResponseEntity<Product[]> response;
        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, Product[].class, low, high);
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
    public List<Product> getAllProduct() {
        String url = siteUrl + "/admin/product";
        ResponseEntity<Product[]> response;
        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, Product[].class);
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
    public List<Product> getProductByCategoryWithChildren(int catId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
