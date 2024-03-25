package com.cgi.accountservice.rest;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


@Component
@Slf4j
public class RestConnector {


    private static final String TIME_OUT = "80000";

    public ResponseEntity<String> sendPostRequest(String url, String request, MediaType contentType,MediaType accept) {
        return sendPostRequest(url, request,"", contentType, accept, TIME_OUT, TIME_OUT);
    }


    public ResponseEntity<String> sendPostRequest(String url, String request,String authorization, MediaType contentType,MediaType accept, String readTimeout, String socketTimeout) {
        String connectionUrl = url;
        log.info("### URL USED FOR POST IS ### " + connectionUrl);

        //Build header request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(contentType);
        if(!authorization.isEmpty()) {
            headers.set("Authorization",authorization);
        }
        headers.setAccept(Collections.singletonList(accept));
        HttpEntity<String> entity = new HttpEntity<>(request, headers);

        RestTemplate restTemplate = new RestTemplate();
        return restConnectorProcessor(restTemplate,connectionUrl, HttpMethod.POST,entity);

    }

    public ResponseEntity<String> sendGetRequest(String url, MediaType contentType,MediaType accept,HttpHeaders headers ) {

        String connectionUrl = url;
        headers.setContentType(contentType);
        headers.setAccept(Collections.singletonList(accept));
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        RestTemplate restTemplate = new RestTemplate();
        log.info("### URL USED FOR GET IS ### " + connectionUrl);
        return restConnectorProcessor(restTemplate,connectionUrl, HttpMethod.GET,entity);
    }

    private  ResponseEntity<String> restConnectorProcessor(RestTemplate restTemplate, String url, HttpMethod method, HttpEntity<String> entity){
        ResponseEntity<String> responseFromServer = null;
        String result="";
        try{
            responseFromServer = restTemplate.exchange(url, method, entity, String.class);
            result = responseFromServer.getBody();
            log.info("### RESPONSE FROM TRANSACTION SERVICE ### " + result);
            return ResponseEntity.status(responseFromServer.getStatusCode()).body(result);
        }catch(ResourceAccessException e){
            log.error("ResourceAccessException"+e.getMessage());
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("");
        } catch(HttpStatusCodeException exception){
            log.error("HttpStatusCodeException::" + exception.getMessage());
            int statusCode = exception.getStatusCode().value();
            log.info("### EXCEPTION FROM TRANSACTION SERVICE ###" + exception.getResponseBodyAsString());
            return ResponseEntity.status(statusCode).body(exception.getResponseBodyAsString());
        } catch(Exception e){
            log.error("### EXCEPTION FROM TRANSACTION SERVICE RETURNING 500 ### " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
        }

    }

}