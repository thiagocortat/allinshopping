package com.projetandoo.allinshopping.utilities;

import android.location.Location;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;


/*************************************************************
 * Classe de constantes do Aplicatico
 * 
 * @author Thiago Cortat (thiagocortat@gmail.com)
 */
public class StringUtils
{
	public static boolean isEmpty(String s)
	{
		return (s == null || s.trim().equals(""));
	}
	
	public static String emptyOr(String s)
	{
		return ((!StringUtils.isEmpty(s)) ? s : "");
	}
	
	public static String getExtension(String s) 
	{
	    int dot = s.lastIndexOf(".");
	    return s.substring(dot + 1);
	}

	public static String getBasename(String s) 
	{
		int sep = s.lastIndexOf("/");
		return s.substring(sep + 1, s.length());
	}
	
	public static String getBasename(String s, String ext) 
	{
		int sep = s.lastIndexOf("/");
		String str = s.substring(sep + 1, s.length());
		return str.replaceAll('.' + ext, "");
	}
	
	public static String encodeSpace(String s)
	{
		return s.replaceAll(" ", "%20");
	}

	
	@SuppressWarnings("unused")
	public static boolean isNumber(String str) {
		
		try  
		{  
		    double d = Double.parseDouble(str);  
		}  
		catch(NumberFormatException nfe)  
		{  
			return false;//str.matches("[0-9]{5}-?[0-9]{3}?");
		}  
		return true;  
		

	}
	
	
	/**
	 * returns the string, the first char lowercase
	 *
	 * @param target
	 * @return
	 */
	public final static String asLowerCaseFirstChar(final String target) {

	    if ((target == null) || (target.length() == 0)) {
	        return target; // You could omit this check and simply live with an
	                       // exception if you like
	    }
	    return Character.toLowerCase(target.charAt(0))
	            + (target.length() > 1 ? target.substring(1) : "");
	}

	/**
	 * returns the string, the first char uppercase
	 *
	 * @param target
	 * @return
	 */
	public final static String asUpperCaseFirstChar(final String target) {

	    if ((target == null) || (target.length() == 0)) {
	        return target; // You could omit this check and simply live with an
	                       // exception if you like
	    }
	    return Character.toUpperCase(target.charAt(0))
	            + (target.length() > 1 ? target.substring(1) : "");
	}
	
	public static String currencyFormat(BigDecimal n) {
	    return NumberFormat.getCurrencyInstance().format(n);
	}
	
	public static String stringForImageUrlDelivery(String url){
		
		try {
			return url.split("img=")[1].replace("&w=100&h=100", "");
		} catch (Exception e) {
			return url;
		}
		
	}
	
	public static String calcMD5(String s) {
	    try {
	        MessageDigest digest = MessageDigest.getInstance("MD5");
	        digest.update(s.getBytes());
	        byte messageDigest[] = digest.digest();
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < messageDigest.length; i++) {
	            byte b = messageDigest[i];
	            String hex = Integer.toHexString((int) 0x00FF & b);
	            if (hex.length() == 1) {
	                sb.append("0");
	            }
	            sb.append(hex);
	        }
	        return sb.toString();
	    }
	    catch (NoSuchAlgorithmException e) {
	        // exception
	    }
	    return "";

	}
	
	public static String getDistanceTo(Location start, Location end) {
		
		if(start == null || end == null)
			return "";
		double distance = start.distanceTo(end);
		return getDistanceTo(distance);

	}
	
	public static String getDistanceTo(double distance) {
		
		String result = "";
		DecimalFormat dF = new DecimalFormat("00");
		
		dF.applyPattern("0");
		
		if (distance < 1000)
			result = dF.format(distance) + "m";
		else {
			distance = distance / 1000.0;
			result   = dF.format(distance) + "km";
		}
		
		return result;
	}


}
