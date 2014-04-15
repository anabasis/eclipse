package com.tg.slug.proc; 


import javax.servlet.http.HttpServletRequest;

import com.tg.slug.exception.PException;
import com.tg.slug.web.MultipartRequest;


public interface Processor 
{ 
    // General Web process
	public String processWebRequest(HttpServletRequest req) throws PException;
	// Multipart Process
	public String processMultipartRequest(MultipartRequest mReq,HttpServletRequest req) throws PException;

} 
