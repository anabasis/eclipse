package com.tg.slug.proc.component;

import com.tg.slug.proc.msg.ProcessMsg;
import com.tg.slug.vo.box.Box;

import org.jdom.Document;

public interface FlowComponentImpl{

    public ProcessMsg executeFlow(Box msg) throws Exception;

}
