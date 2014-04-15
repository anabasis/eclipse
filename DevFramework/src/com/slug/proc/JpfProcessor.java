package com.slug.proc; 


import javax.servlet.http.HttpServletRequest;

import com.slug.exception.PException;
import com.slug.proc.msg.ProcessMsg;
import com.slug.vo.box.Box;
import com.slug.web.MultipartRequest;


public interface JpfProcessor 
{ 
    // General Web process
	public ProcessMsg procJpfRequest(Box ibox) throws PException;

} 
