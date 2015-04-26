/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.restclient;

import com.tyrin.beans.Order;
import com.tyrin.exceptions.DBException;
import com.tyrin.services.IOrderService;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author user
 */
public class OrderRestClient implements IOrderService {

    private final RestTemplate restTemplate;
    private final HttpHeaders headers;
    private final String siteUrl;

    public OrderRestClient(String siteUrl, final String login, final String password) {
        this.restTemplate = new RestTemplate();
        this.siteUrl = siteUrl;
        this.headers = new HttpHeaders() {
            {
                String auth = login + ":" + password;
                byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encodedAuth);
                set("Authorization", authHeader);
            }
        };
    }

    @Override
    public void addOrder(Order order) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order getOrder(int orderID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Order> getAllOrders() {
        String url = siteUrl + "/admin/order";
        ResponseEntity<Order[]> response;
        HttpEntity<String> request = new HttpEntity<>(headers);
        response = restTemplate.exchange(url, HttpMethod.GET, request, Order[].class);
        int respCode = response.getStatusCode().value();
        try {
            if (respCode >= 400) {
                throw new DBException("Server error: " + respCode);
            }
        } catch (RestClientException | DBException e) {
            throw new DBException(e.getMessage());
        }

        return Arrays.asList(response.getBody());

    }

    @Override
    public void delOrder(int orderID) {
        String url = siteUrl + "/admin/order/{id}";
        ResponseEntity<String> response;
        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            response = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class, orderID);
            int respCode = response.getStatusCode().value();
            if (respCode >= 400) {
                throw new DBException("Server error: " + respCode);
            }
        } catch (RestClientException | DBException e) {
            throw new DBException(e.getMessage());
        }
    }

}
