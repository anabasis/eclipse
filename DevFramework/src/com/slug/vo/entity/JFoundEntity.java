package com.slug.vo.entity;

/**
 *
 * @(#) EntityData.java
 * Copyright 1999-2000 by  LG-EDS Systems, Inc.,
 * Information Technology Group, Application Architecture Team,
 * Application Intrastructure Part.
 * 236-1, Hyosung-2dong, Kyeyang-gu, Inchun, 407-042, KOREA.
 * All rights reserved.
 *
 * NOTICE !      You can copy or redistribute this code freely,
 * but you should not remove the information about the copyright notice
 * and the author.
 *
 * @author  WonYoung Lee, wyounglee@lgeds.lg.co.kr.
 */
import java.lang.reflect.Field;
import java.lang.reflect.Array;

public abstract class JFoundEntity implements java.io.Serializable {

	/**
	 *
	 */
	public JFoundEntity() {
		super();
	}

	/**
	 * Returns a String that represents the member variables of the sub class.
	 * @return a string representation of the receiver
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer();

		Class c = this.getClass();
		
		String fullname = c.getName();
		String name = null;
		
		int index = fullname.lastIndexOf('.');
		
		if ( index == -1 ) 
			name = fullname;
		else 
			name = fullname.substring(index+1);
		
		buf.append(name + ":{");

		Field[] fields = c.getFields();

		for (int i=0 ; i<fields.length; i++) {
			try {
				if ( i != 0 )
					buf.append(',');
				
				buf.append(fields[i].getName() + '=');
				
				Object f = fields[i].get(this);
				Class fc = fields[i].getType();
				
				if ( fc.isArray() ) {
				
					buf.append('[');
					
					int length = Array.getLength(f);
					
					for(int j=0; j<length ;j++){
						if ( j != 0 ) buf.append(',');
						Object element = Array.get(f, j);
						buf.append(element.toString());
					}
					
					buf.append(']');
				}
				
				else
					
					buf.append(f.toString());
			}
			catch(Exception e) {}
		}
		buf.append('}');
		return buf.toString();
	}
}