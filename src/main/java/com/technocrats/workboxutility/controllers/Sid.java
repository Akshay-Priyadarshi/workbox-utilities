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
import com.google.gson.Gson;

@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/json")
@RestController
public class Sid {

	// Difference between two JSON object

	@PostMapping("/difference")
	public String jsonDifference(@RequestBody() String body) {

		JSONObject json = new JSONObject(body);
		String j1 = json.getJSONObject("json1").toString();
		String j2 = json.getJSONObject("json2").toString();
		System.out.println(j1 + j2);

		InputStream s1 = new ByteArrayInputStream(j1.getBytes(Charset.forName("UTF-8")));
		InputStream s2 = new ByteArrayInputStream(j2.getBytes(Charset.forName("UTF-8")));

		ObjectMapper mapper = new ObjectMapper();
		// assertEquals(mapper.readTree(s1), mapper.readTree(s2));

		boolean result = true;
		try {
			result = mapper.readTree(s1).equals(mapper.readTree(s2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Boolean.toString(result);
	}

	// Checking the validity of a JSON

	@PostMapping("/isvalid")
	public String jsonIsValid(@RequestBody() String text) {
		final Gson gson = new Gson();

		try {
			gson.fromJson(text, Object.class);
			return "It is a JSON";
		} catch (com.google.gson.JsonSyntaxException ex) {
			return "Not a JSON";
		}
	}

}
