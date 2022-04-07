package com.technocrats.workboxutility.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.json.JSONObject;
import org.json.XML;
import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;
import org.springframework.web.bind.annotation.*;

import com.sun.codemodel.*;

@CrossOrigin(origins="*")
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
    String json2pojo(@RequestBody() String body) throws IOException {
    	File file = new File("C:\\");
    	convertJsonToJavaClass(body, file, "incture", "MyClass");
    	return "JSON has been successully converted to POJO and saved in " + file.toString() + "incture";
    }

    @PostMapping("/pojo2json")
    String pojo2json(@RequestBody() String body) {
    	return "";
    }
    
    public void convertJsonToJavaClass(String json, File outputJavaClassDirectory, String packageName, String javaClassName) 
    		  throws IOException {
    		    JCodeModel jcodeModel = new JCodeModel();

    		    GenerationConfig config = new DefaultGenerationConfig() {
    		        @Override
    		        public boolean isGenerateBuilders() {
    		            return false;
    		        }
    		        
    		        @Override
    		        public boolean isIncludeAdditionalProperties(){
    		            return false;
    		        }
    		        
    		        @Override
    		        public boolean isIncludeHashcodeAndEquals() {
    		        	return false;
    		        }
    		        
    		        @Override
    		        public boolean isIncludeToString() {
    		        	return false;
    		        }
    		        
    		        @Override
    		        public boolean isIncludeGeneratedAnnotation() {
    		        	return false;
    		        }

    		        @Override
    		        public SourceType getSourceType() {
    		            return SourceType.JSON;
    		        }
    		    };

    		    SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
    		    mapper.generate(jcodeModel, javaClassName, packageName, json);

    		    jcodeModel.build(outputJavaClassDirectory);
    		}
}
