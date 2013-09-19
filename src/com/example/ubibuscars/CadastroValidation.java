package com.example.ubibuscars;

import java.util.GregorianCalendar;
import java.util.Calendar;

public class CadastroValidation{
	public static boolean nascimento(String data) {  
		GregorianCalendar calendar =  new GregorianCalendar();
	    int day = 0,month = 0,year = 0;
	    String dayStr = data.substring(0,2);  
	    String monthStr = data.substring(3,5);  
	    String yearStr = data.substring(6,10);  
	    try {  
	    	day = Integer.parseInt(dayStr);  
	        month = Integer.parseInt(monthStr);  
	        year = Integer.parseInt(yearStr);  
	    } catch (Exception e) {  
	    	return false;  
	    }  
	    if (year>(calendar.get(Calendar.YEAR)-1)){
	    	return false;
	    }
	    if (day < 1 || month < 1 || year < 1)  
	    	return false;  
	    else  
	    	if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)  
	    		if (day <= 31)  
	    			return true;  
	            else  
	            	return false;  
	        else  
	        	if (month == 4 || month == 6 || month == 9 || month == 11)  
	        		if (day <= 30)  
	        			return true;  
	                else  
	                	return false;  
	            else  
	            	if (month == 2)  
	            		if (calendar.isLeapYear(year))  
	            			if (day <= 29)  
	            				return true;  
	                        else  
	                        	return false;  
	                    else  
	                    	if (day <= 28)  
	                    		return true;  
	                        else  
	                        	return false;  
	                else   
	                	if (month > 12)  
	                		return false;  
	    return true; 
	}
}