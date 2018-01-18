package com.example.StockGraper.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.StockGraper.Util;
import com.example.StockGraper.entity.AvStock;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/graper")
public class GraperService {
	
	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> get(
			@RequestParam("function") String function,
			@RequestParam("symbol") String symbol,
			@RequestParam("interval") String interval,
			@RequestParam("apikey") String apikey) {
				
		String information = null;		
		Date lastRefreshed = null;
		String timeZone = null;
		JsonNode seriesNode = null;
		
		// Build url dynamically
		StringBuilder urlBuilder = new StringBuilder("http://www.alphavantage.co/query")		
				.append("?function=").append(function)	
				.append("&symbol=").append(symbol);
		if (interval != null && !interval.isEmpty()) {
			urlBuilder.append("&interval=").append(interval);
		}
		urlBuilder.append("&apikey=").append(apikey);
		
		// Get Json data from client server
		ResponseEntity<JsonNode> resp = restTemplate.getForEntity(urlBuilder.toString(), 
				JsonNode.class);
		
		// Process the retrieved data
		if (resp.getStatusCode() == HttpStatus.OK) {
			JsonNode rootNode = resp.getBody();			
			
			// Iterate over Root fields and process data
			for (Iterator<String> rootItr = rootNode.fieldNames(); rootItr.hasNext();) {
				String fieldName = rootItr.next();
				// Extract Meta data details
				if (fieldName.contains("Meta Data")) {
					JsonNode metaNode = rootNode.get(fieldName);
					// Process Meta data
					for (Iterator<String> metaItr = metaNode.fieldNames(); metaItr.hasNext();) {
						String metaFieldName = metaItr.next();						
						
						if (metaFieldName.contains("Information")) information = metaNode.get(metaFieldName).asText();						
						else if (metaFieldName.contains("Time Zone")) timeZone = metaNode.get(metaFieldName).asText();						
						else if (metaFieldName.contains("Last Refreshed")) lastRefreshed = Util.convertToDate(
								metaNode.get(metaFieldName).asText(),
								"YYYY-MM-dd hh:mm:ss");	
						
					}
					
				}
				// Extract Time Series details
				else if (fieldName.contains("Time Series")) {
					seriesNode = rootNode.get(fieldName);
				}				
			}	
			
			// Get field name of Time Series and process data			
			List<AvStock> stocks = new ArrayList<>(100);
			for (Iterator<String> seriesItr = seriesNode.fieldNames(); seriesItr.hasNext();) {
				String fieldName = seriesItr.next();
				JsonNode dataNode = seriesNode.get(fieldName);
				
				// Build object
				AvStock stock = new AvStock();
				
				// Meta data
				stock.setFunction(function);
				stock.setInformation(information);
				stock.setLastRefreshed(lastRefreshed);
				if (interval != null && !interval.isEmpty()) {
					stock.setInterval(interval);
				}
				stock.setTimeZone(timeZone);
				
				// Time Series data processing
				for (Iterator<String> dataItr = dataNode.fieldNames(); dataItr.hasNext();) {
					String dataFieldName = dataItr.next();
					
					if (dataFieldName.contains("open"))	stock.setOpen(dataNode.get(dataFieldName).asDouble());
					else if (dataFieldName.contains("close")) stock.setClose(dataNode.get("4. close").asDouble());
					else if (dataFieldName.contains("high")) stock.setHigh(dataNode.get("2. high").asDouble());
					else if (dataFieldName.contains("low")) stock.setLow(dataNode.get("3. low").asDouble());
					else if (dataFieldName.contains("volume")) stock.setVolume(dataNode.get("6. volume").asLong());					
					else if (dataFieldName.contains("adjusted close")) stock.setAdjustedClose(dataNode.get("5. adjusted close").asDouble());
					else if (dataFieldName.contains("dividend amount")) stock.setDividendAmount(dataNode.get("7. dividend amount").asDouble());
					else if (dataFieldName.contains("split coefficient")) stock.setSplitCoefficient(dataNode.get("8. split coefficient").asDouble());
					
					stocks.add(stock);
				}
				
			}
			
		}
		
		return ResponseEntity.ok().build();
		
	}
	
}
