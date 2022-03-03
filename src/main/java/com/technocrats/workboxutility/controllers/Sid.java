package com.technocrats.workboxutility.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "*")
@RequestMapping("/json")
@RestController
public class Sid {

    /***
     * @name Json Difference
     * @param body Json with to jsons
     * @return True or false based on whether jsons match
     * */
    @PostMapping("/difference")
    public String jsonDifference(@RequestBody() String body) {
        JSONObject json = new JSONObject(body);
        String j1 = json.getJSONObject("json1").toString();
        String j2 = json.getJSONObject("json2").toString();
        System.out.println(j1 + j2);

        InputStream s1 = new ByteArrayInputStream(j1.getBytes(Charset.forName("UTF-8")));
        InputStream s2 = new ByteArrayInputStream(j2.getBytes(Charset.forName("UTF-8")));

        ObjectMapper mapper = new ObjectMapper();

        boolean result = true;
        try {
            result = mapper.readTree(s1).equals(mapper.readTree(s2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Boolean.toString(result);
    }
}
