package com.technocrats.workboxutility.controllers;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/conversions")
@RestController
public class Akshay {


    /**
     * @name JSON TO XML
     * @param body Request Body
     * @return XML String
     * @author Akshay Priyadarshi
     */
    @PostMapping("/json2xml")
    String json2xml(@RequestBody() String body){
        try {
            JSONObject jsonReqObject = new JSONObject(body);
            String xmlRoot = jsonReqObject.getString("root");
            String xmlSchemaDetails = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
            String xmlData = XML.toString(jsonReqObject.getJSONObject("data"));
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
    String xml2json(@RequestBody() String body){
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
    String json2pojo(@RequestBody() String body){
        try {
            //
            return body;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return e.toString();
        }
    }

    @PostMapping("/pojo2json")
    String pojo2json(@RequestBody() String body){
        try {
            //
            return body;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return e.toString();
        }
    }


}
