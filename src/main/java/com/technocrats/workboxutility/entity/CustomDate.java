package com.technocrats.workboxutility.entity;

public class CustomDate {
	private String date;
	private String format;
	
	public CustomDate() {
		
	}
	
	public CustomDate(String date, String format) {
		this.date = date;
		this.format = format;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
	public boolean check(int day, int month, int year) {
		if(day == 0 || month == 0)
		{
			return false;
		}
		if(month == 1 && day <= 31)
		{
			return true;
		}
		else if(month == 2 && day <= 29)
		{
			if(day == 29)
			{
				if ((year%4 == 0 && year%100 != 0) || (year%400 == 0))
		    	{
		    		return true;
		    	}
				else
				{
					return false;
				}
			}
			else
			{
				return true;
			}
		}
		else if(month == 3 && day <= 31)
		{
			return true;
		}
		else if(month == 4 && day <= 30)
		{
			return true;
		}
		else if(month == 5 && day <= 31)
		{
			return true;
		}
		else if(month == 6 && day <= 30)
		{
			return true;
		}
		else if(month == 7 && day <= 31)
		{
			return true;
		}
		else if(month == 8 && day <= 31)
		{
			return true;
		}
		else if(month == 9 && day <= 30)
		{
			return true;
		}
		else if(month == 10 && day <= 31)
		{
			return true;
		}
		else if(month == 11 && day <= 30)
		{
			return true;
		}
		else if(month == 12 && day <= 31)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String ordinal(int day) {
		String suffix[] = {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
		switch (day%100) {
		case 11:
		case 12:
		case 13:
			return day + "th";
		default:
			return day + suffix[day%10];
		}
	}
	
	
}
