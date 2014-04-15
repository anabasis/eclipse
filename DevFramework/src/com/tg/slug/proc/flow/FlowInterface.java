package com.tg.slug.proc.flow;

import com.tg.slug.proc.msg.ProcessMsg;
import com.tg.slug.vo.box.Box;

import net.sf.json.JSONObject;


public interface FlowInterface{
    public ProcessMsg executeFlow(Box msg) throws Exception;
}
