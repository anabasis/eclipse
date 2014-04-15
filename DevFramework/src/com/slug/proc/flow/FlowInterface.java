package com.slug.proc.flow;

import com.slug.proc.msg.ProcessMsg;
import com.slug.vo.box.Box;

import net.sf.json.JSONObject;


public interface FlowInterface{
    public ProcessMsg executeFlow(Box msg) throws Exception;
}
