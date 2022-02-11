package com.technocrats.workboxutility;


import org.json.JSONObject;
import org.json.XML;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;



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
			JSONObject jsonReqObject = new JSONObject(body);
			String xmlRoot = jsonReqObject.getString("root");
			String xmlSchemaDetails = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
			String xmlData = XML.toString(jsonReqObject.getJSONObject("data"),"");
			String rootWrappedXML = "<" + xmlRoot + ">\n" + xmlData + "\n</" + xmlRoot + ">";
			String xml = xmlSchemaDetails + rootWrappedXML;
			return xml;
		}catch(Exception e){
			System.out.println(e.getMessage());
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
			System.out.println(e.getMessage());
			return e.toString();
		}
	}

    @PostMapping("/json2pojo")
    public String jsonToPojo(@RequestBody() String body){
        try {
			//
			return body;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return e.toString();
        }
    }

	@PostMapping("/pojo2json")
    public String pojoToJson(@RequestBody() String body){
        try {
			//
			return body;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return e.toString();
        }
    }


	public static void main(String[] args) {
		SpringApplication.run(WorkboxUtilityApplication.class, args);
	}
}
