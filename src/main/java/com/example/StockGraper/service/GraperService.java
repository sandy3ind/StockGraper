package com.example.StockGraper.service;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/graper")
public class GraperService {
	
	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> get() {
		
		ResponseEntity<JsonNode> resp = restTemplate.getForEntity("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=MSFT&apikey=demo", 
				JsonNode.class);
		
		if (resp.getStatusCode() == HttpStatus.OK) {
			JsonNode node = resp.getBody();
			JsonNode metaDataNode = node.get("Meta Data");
			System.out.println(metaDataNode);
			ObjectMapper mapper = new ObjectMapper();
				Map<String, Object> mapRootNode = mapper.convertValue(resp.getBody(), Map.class);
				Map<String, Object> metadataRoot = (Map<String, Object>) mapRootNode.get("Meta Data");
				System.out.println(metadataRoot);
			
		}
        
		
//		if (resp.getStatusCode() == HttpStatus.OK) {
//			try {
//				JSONObject json = new JSONObject(resp.getBody());
//				
//				JSONObject metaData = json.getJSONObject("Meta Data");
//				JSONObject series = json.getJSONObject("Time Series (Daily)");
//				
//				
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			
//		}
		
		return ResponseEntity.ok().build();
		
	}
	
}
