package com.tg.slug.proc; 


import javax.servlet.http.HttpServletRequest;

import com.tg.slug.exception.PException;
import com.tg.slug.proc.msg.ProcessMsg;
import com.tg.slug.vo.box.Box;
import com.tg.slug.web.MultipartRequest;


public interface JpfProcessor 
{ 
    // General Web process
	public ProcessMsg procJpfRequest(Box ibox) throws PException;

} 
