package com.technocrats.workboxutility.controllers;

import org.json.JSONObject;

import java.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.technocrats.workboxutility.entity.Currency;

@CrossOrigin(origins="*")
@RequestMapping("/other-utilities")
@RestController
public class Aniket {

    static String apiKey = "0126747d97-9f56fde0e0-r9jo5u";

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

}
