package com.example.StockGraper.entity;

import java.util.Date;

public class AvStock {

	private Long id;
	private String function;
	private String information;
	private String symbol;
	private Date lastRefreshed;
	private String timeZone;
	private String interval;
	private Date recordedTime;
	private Double open;
	private Double high;
	private Double low;
	private Double close;
	private Double adjustedClose;
	private Long volume;
	private Double dividendAmount;
	private Double splitCoefficient;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Date getLastRefreshed() {
		return lastRefreshed;
	}
	public void setLastRefreshed(Date lastRefreshed) {
		this.lastRefreshed = lastRefreshed;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public Date getRecordedTime() {
		return recordedTime;
	}
	public void setRecordedTime(Date recordedTime) {
		this.recordedTime = recordedTime;
	}
	public Double getOpen() {
		return open;
	}
	public void setOpen(Double open) {
		this.open = open;
	}
	public Double getHigh() {
		return high;
	}
	public void setHigh(Double high) {
		this.high = high;
	}
	public Double getLow() {
		return low;
	}
	public void setLow(Double low) {
		this.low = low;
	}
	public Double getClose() {
		return close;
	}
	public void setClose(Double close) {
		this.close = close;
	}
	public Double getAdjustedClose() {
		return adjustedClose;
	}
	public void setAdjustedClose(Double adjustedClose) {
		this.adjustedClose = adjustedClose;
	}
	public Long getVolume() {
		return volume;
	}
	public void setVolume(Long volume) {
		this.volume = volume;
	}
	public Double getDividendAmount() {
		return dividendAmount;
	}
	public void setDividendAmount(Double dividendAmount) {
		this.dividendAmount = dividendAmount;
	}
	public Double getSplitCoefficient() {
		return splitCoefficient;
	}
	public void setSplitCoefficient(Double splitCoefficient) {
		this.splitCoefficient = splitCoefficient;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
}
