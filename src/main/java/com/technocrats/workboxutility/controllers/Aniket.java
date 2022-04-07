package com.technocrats.workboxutility.controllers;

import org.json.JSONObject;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.technocrats.workboxutility.entity.Currency;
import com.technocrats.workboxutility.entity.CustomDate;

@CrossOrigin(origins="*")
@RequestMapping("/other-utilities")
@RestController
public class Aniket {

    static String apiKey = "09513a4bfb-611122e30b-r9yt3j";

    @PostMapping("/currency-conversion")
    public String currencyConversion(@RequestBody() Currency cur) {
        try {
            String from = cur.getFrom();
            String to = cur.getTo();
            String url = String.format("https://api.fastforex.io/fetch-one?from=%s&to=%s&api_key=%s", from, to, apiKey);
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(url, String.class);
            JSONObject obj = new JSONObject(result);
            obj = obj.getJSONObject("result");
            double rate = Double.parseDouble(obj.get(to).toString());
            double convertedAmount = rate * cur.getAmount();
            if (cur.getFormat().equals("in")) {
                String str = String.format("%.5f", convertedAmount);
                if (str.length() > 3) {
                    String rev = "";
                    for (int i = str.length() - 1; i >= 0; i--) {
                        rev += str.charAt(i);
                    }
                    int i = 0, j;
                    String ans = "";
                    while (rev.charAt(i) != '.') {
                        ans += rev.charAt(i);
                        i++;
                    }
                    ans += rev.charAt(i);
                    i++;
                    if (i + 3 >= str.length()) {
                        return str;
                    }
                    for (j = i; j < i + 3; j++) {
                        ans += rev.charAt(j);
                    }
                    i = j;
                    ans += ",";
                    int ctr = 0;
                    for (j = i; j < rev.length(); j++) {
                        ans += rev.charAt(j);
                        ctr++;
                        if (ctr == 2 && j < rev.length() - 1) {
                            ans += ",";
                            ctr = 0;
                        }
                    }
                    String res = "";
                    for (i = ans.length() - 1; i >= 0; i--) {
                        res += ans.charAt(i);
                    }
                    return res;
                }
            } else if (cur.getFormat().equals("intl")) {
                return String.format("%,.5f", convertedAmount);
            }

            return String.format("%.5f", convertedAmount);
        }catch(Exception e){
            System.out.println(e);
        }
        return "Cannot convert";
    }

    @GetMapping("/available-conversion")
    public HashMap<String, String> availableConversion() {
        String url = String.format("https://api.fastforex.io/currencies?api_key=%s", apiKey);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        JSONObject obj = new JSONObject(result);
        obj = obj.getJSONObject("currencies");
        Iterator<String> keys = obj.keys();
        HashMap<String, String> con = new HashMap<>();
        while(keys.hasNext())
        {
            String key = (String)keys.next();
            String value = (String)obj.get(key);
            con.put(key, value);
        }
        List<Map.Entry<String, String>> lst = new LinkedList<Map.Entry<String, String>>(con.entrySet());
        Collections.sort(lst, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2)
            {
                return (o1.getValue().compareTo(o2.getValue()));
            }
        });
        HashMap<String, String> res = new LinkedHashMap<String, String>();
        for(Map.Entry<String, String> mp : lst)
        {
            res.put(mp.getKey(), mp.getValue());
        }
        return res;
    }
    
    @PostMapping("/date/dayFinder")
    public String findDay(@RequestBody CustomDate customDate) throws ParseException {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
    	Date date = sdf.parse(customDate.getDate());
    	Format f = new SimpleDateFormat("EEEE");  
    	String str = f.format(date); 
    	return str;
    }
    
    @GetMapping("/date/available-conversions")
    public List<String> getConversions() {
    	List<String> conversions = new ArrayList<>();
    	conversions.add("MM-DD-YY");
    	conversions.add("YY-MM-DD");
    	conversions.add("Month D, YYYY");
    	conversions.add("DD Mon, YYYY");
    	conversions.add("DDDD, MMMM DD, YYYY");
    	conversions.add("D'th MMMM, YYYY");
    	return conversions;
    }
    
    @PostMapping("/date/convert")
    public String convertDate(@RequestBody CustomDate customDate) throws ParseException {
    	
    	String date = customDate.getDate();
    	String format = customDate.getFormat();
    	
    	String[] values = date.split("-");
    	
    	int day = Integer.parseInt(values[0]);
    	int month = Integer.parseInt(values[1]);
    	int year = Integer.parseInt(values[2]);
    	
    	if(!customDate.check(day, month, year))
    	{
    		return "Invalid Date";
    	}
    	
    	String monthName[] = {"January", "February", "March", "April", "May", "June", "July",
    							"August", "September", "October", "November", "December"};
    	
    	String monName[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec"};
    	
    	if (format.equals("MM-DD-YY"))
    	{
    		return values[1] + "-" + values[0] + "-" + values[2];
    	}
    	else if (format.equals("YY-MM-DD"))
    	{
    		return values[2] + "-" + values[1] + "-" + values[0];
    	}
    	else if (format.equals("Month D, YYYY"))
    	{
    		return monthName[month-1] + " " + day + ", " + values[2];
    	}
    	else if (format.equals("DD Mon, YYYY"))
    	{
    		return values[0] + " " + monName[month-1] + ", " + values[2];
    	}
    	else if (format.equals("DDDD, MMMM DD, YYYY"))
    	{
    		return findDay(customDate) + ", " + monthName[month-1] + " " + values[0] + ", " + values[2];
    	}
    	else if(format.equals("D'th MMMM, YYYY"))
    	{
    		return customDate.ordinal(day) + " " + monthName[month-1] + ", " + values[2];
    	}
    	return "";
    }
}
