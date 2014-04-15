package com.tg.slug.proc; 


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tg.slug.exception.PException;
import com.tg.slug.vo.box.Box;


public interface XAsyncStrProcessor 
{ 
    //public ProcessMsg procXAsyncRequest(HttpServletRequest req, HttpServletResponse res) throws PException;
    public String  procXAsyncRequest(HttpServletRequest req, HttpServletResponse res) throws PException;
    public String  procXAsyncMultipartRequest(Box sbox) throws PException;

} 
