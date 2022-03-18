package com.technocrats.workboxutility.controllers;


import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

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

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> type = new TypeReference<HashMap<String, Object>>() {
        };

        String result = "";

        Map<String, Object> leftMap = null;
        try {
            leftMap = mapper.readValue(j1, type);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Map<String, Object> rightMap = null;
        try {
            rightMap = mapper.readValue(j2, type);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        MapDifference<String, Object> diff = Maps.difference(leftMap, rightMap);
        result = diff.toString();

        return result;
    }
}
