package com.example.StockGraper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Util {

	public static Date convertToDate(String dateStr, String format) {
		DateFormat formatter = new SimpleDateFormat(format);
		//formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
		try {
			Date date = formatter.parse(dateStr);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
