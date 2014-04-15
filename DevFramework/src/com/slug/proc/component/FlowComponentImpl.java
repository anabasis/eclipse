package com.slug.proc.component;

import com.slug.proc.msg.ProcessMsg;
import com.slug.vo.box.Box;

import org.jdom.Document;

public interface FlowComponentImpl{

    public ProcessMsg executeFlow(Box msg) throws Exception;

}
