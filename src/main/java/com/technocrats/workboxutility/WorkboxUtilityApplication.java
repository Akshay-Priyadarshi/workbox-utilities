package com.technocrats.workboxutility;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.dsig.XMLObject;

@SpringBootApplication
@RestController
public class WorkboxUtilityApplication {


	@GetMapping("/hello/{name}")
	public String sayHello(@PathVariable("name") String name) {
		return String.format("Hello %s!", name);
	}

	/**
	 * @name JSON TO XML
	 * @param body Request Body
	 * @return XML String
	 * @author Akshay Priyadarshi
	 */
	@PostMapping("/json2xml")
	public String jsonToXML(@RequestBody() String body){
		try {
			JSONObject jsonObject = new JSONObject(body);
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + XML.toString(jsonObject);
			return xml;
		}catch(Exception e){
			System.out.println(e.toString());
			return e.toString();
		}
	}

	/**
	 * @name XML TO JSON
	 * @param body Request Body
	 * @return JSON String
	 * @author Akshay Priyadarshi
	 */
	@PostMapping("/xml2json")
	public String xmlToJson(@RequestBody() String body){
		try {
			JSONObject json = XML.toJSONObject(body);
			String jsonString = json.toString(4);
			return jsonString;
		}catch(Exception e){
			System.out.println(e.toString());
			return e.toString();
		}
	}


	public static void main(String[] args) {
		SpringApplication.run(WorkboxUtilityApplication.class, args);
	}
}
