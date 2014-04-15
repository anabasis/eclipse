package com.tg.slug.proc; 


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tg.slug.exception.PException;
import com.tg.slug.proc.msg.ProcessMsg;


public interface XAsyncProcessor 
{ 
    //public ProcessMsg procXAsyncRequest(HttpServletRequest req, HttpServletResponse res) throws PException;
    public void  procXAsyncRequest(HttpServletRequest req, HttpServletResponse res) throws PException;

} 
