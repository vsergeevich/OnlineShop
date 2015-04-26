/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.restclient;

import com.tyrin.beans.Manufacturer;
import com.tyrin.exceptions.DBException;
import com.tyrin.services.IManufacturerService;
import java.nio.charset.Charset;
import java.util.Arrays;
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
public class ManufacturerRestClient implements IManufacturerService {

    private final RestTemplate restTemplate;
    private final String siteUrl;
    private final HttpHeaders headers;

    public ManufacturerRestClient(String siteUrl, final String login, final String password) {
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
    public Manufacturer getManufacturer(int id) {
        String url = "/admin/manufacturer/{id}";
        ResponseEntity<Manufacturer> response;
        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, Manufacturer.class, id);
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
    public List<Manufacturer> getAllManufacturer() {
        String url = siteUrl + "/admin/manufacturer";
        ResponseEntity<Manufacturer[]> response;
        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, Manufacturer[].class);
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
    public void updateManufactutrer(Manufacturer man) {
        String url = siteUrl + "/admin/manufacturer/{id}";
        ResponseEntity<String> response;
        HttpEntity<Manufacturer> request = new HttpEntity<>(man, headers);
        try {
            response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class, man.getId());
            int respCode = response.getStatusCode().value();
            if (respCode >= 400) {
                throw new DBException("Server error: " + respCode);
            }
        } catch (RestClientException | DBException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public void addManufacturer(Manufacturer man) {
        String url = siteUrl + "/admin/manufacturer";
        HttpEntity<Manufacturer> request = new HttpEntity<>(man, headers);
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
    public void deleteManufacturer(int id) {
        String url = siteUrl + "/admin/manufacturer/{id}";
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
}
