package com.slug.proc; 


import javax.servlet.http.HttpServletRequest;

import com.slug.exception.PException;
import com.slug.web.MultipartRequest;


public interface Processor 
{ 
    // General Web process
	public String processWebRequest(HttpServletRequest req) throws PException;
	// Multipart Process
	public String processMultipartRequest(MultipartRequest mReq,HttpServletRequest req) throws PException;

} 
