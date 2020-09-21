package com.slug.key;

import java.lang.reflect.Field;

import com.slug.exception.PException;

public class JFoundKeyHandler {
	
	private static JFoundKey jkeys;
	private static JFoundKeyHandler jh;
	private static Class key_class ;
	
	
	   public static synchronized JFoundKeyHandler getInstance() {

	        if(jkeys == null){
	            jkeys = new JFoundKey();
	        }
	        
	        if(jh == null)
	        	jh = new JFoundKeyHandler();
	       ///*
	        try{
		       if(key_class == null){
		    	   key_class =  Class.forName("com.slug.key.JFoundKey");
	        	}
	   		}catch(ClassNotFoundException cn){
	   			//throw (new PException(cn));
	   		}
	       // */
	        return jh;
	    }

	
	//public JFoundKeyHandler(){}
	
	public JFoundKey getKeys(){
		return jkeys;
	}
	
	public static String  getContext(){
		return jkeys.ContextName;
	}
	
	public String getKeys(String key) {//throws  IllegalArgumentException, IllegalAccessException, ClassNotFoundException{
		
		String value = "";
		try{
			Field ff = key_class.getField(key);
			value = ff.get(jkeys).toString();

		}catch(NoSuchFieldException nf){

			System.out.println("["+key+"] is not define !");
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return value;
	}
	
	public void setKeys(String key, String value){//throws PException, IllegalArgumentException, IllegalAccessException,ClassNotFoundException{
		
		//Class key_class = Class.forName("com.slug.key.JFoundKey");
		try{
//			if(jkeys == null)System.out.println("jkeys is null");
//			else System.out.println("jkeys is["+jkeys+"]");
			//Field ff = JFoundKey.class.getField(key);
			Field ff = key_class.getDeclaredField(key);

			ff.setAccessible(true);

			ff.set(jkeys,	new String(value));

		}catch(NoSuchFieldException nf){

			System.out.println("["+key+"] is not define !");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	

}
