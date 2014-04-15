package com.slug.proc; 


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.slug.exception.PException;
import com.slug.proc.msg.ProcessMsg;


public interface XAsyncProcessor 
{ 
    //public ProcessMsg procXAsyncRequest(HttpServletRequest req, HttpServletResponse res) throws PException;
    public void  procXAsyncRequest(HttpServletRequest req, HttpServletResponse res) throws PException;

} 
