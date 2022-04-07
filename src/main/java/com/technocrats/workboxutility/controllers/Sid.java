package com.technocrats.workboxutility.controllers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.technocrats.workboxutility.entity.FlatMapUtil;

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
    	Map<String, Object> leftFlatMap = FlatMapUtil.flatten(leftMap);
    	Map<String, Object> rightFlatMap = FlatMapUtil.flatten(rightMap);

    	MapDifference<String, Object> difference = Maps.difference(leftFlatMap, rightFlatMap);
    	
    	List<String> output = new ArrayList<>();
    	
    	output.add("Entries only on the left");
    	output.add("--------------------------");
    	difference.entriesOnlyOnLeft()
        .forEach((key, value) -> output.add(key + ": " + value));
    	output.add("");
    	
    	output.add("Entries only on the right");
    	output.add("--------------------------");
    	difference.entriesOnlyOnRight()
        .forEach((key, value) -> output.add(key + ": " + value));
    	output.add("");
    	
    	output.add("Entries differing");
    	output.add("--------------------------");
    	difference.entriesDiffering()
    	          .forEach((key, value) -> output.add(key + ": " + value));
    	
    	int len = output.size();
    	for(int i=0;i<len;i++)
    	{
    		String s = output.get(i).replace(',', '^');
    		output.remove(i);
    		output.add(i, s);
    	}
    	
    	String ans = output.toString();
    	
    	StringBuilder sb = new StringBuilder(ans);
    	sb.deleteCharAt(ans.length()-1);
    	sb.deleteCharAt(0);
    	
    	String res = sb.toString();
    	
    	String out = res.replace(',', '|');
    	String result = out.replace('^', ',');
    	
    	return result;
    }
    
}
