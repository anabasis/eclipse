package com.tg.slug.proc.flow;

import java.lang.reflect.InvocationTargetException;

import com.tg.slug.exception.PException;
import com.tg.slug.logging.Logging;
import com.tg.slug.proc.component.FlowComponent;
import com.tg.slug.proc.component.FlowComponentImpl;
import com.tg.slug.proc.msg.ProcessMsg;
import com.tg.slug.vo.box.Box;





public class FlowAccessController
{ 

        
    static final long serialVersionUID = 1L;
    private static FlowAccessController fac;
    
    public static FlowAccessController getInstance(){
    	
    	if(fac == null){
    		fac = new FlowAccessController();
    	}
    	return fac;
    }


    public ProcessMsg executeFlowComponent(Box ibox) throws PException
    {
    	ProcessMsg processMsg = new ProcessMsg();

    	
    	try {
      		
    		Logging.debug.println("< FlowAccessController >< executeFlowComponent > execute ");
    		//FlowComponentImpl flow = FlowComponent.getInstance();//new FlowComponent();
    		FlowComponentImpl flow = new FlowComponent();
	    	processMsg =  flow.executeFlow(ibox);
    	
    	
    	}catch(ClassNotFoundException cn) {
        	Logging.err.println(cn);
        	throw new PException("< FlowAccessController > executeFlowComponent ClassNotFoundException [com.tg.jfound.proc.flow.FlowComponent]"+cn.getCause(),cn);
        }catch(NoSuchMethodException nm) {
        	Logging.err.println(nm);
        	throw new PException("< FlowAccessController > executeFlowComponent NoSuchMethodException [com.tg.jfound.proc.flow.FlowComponent]"+nm.getCause(),nm);
        }catch(IllegalAccessException iae) {
        	Logging.err.println(iae);
        	throw new PException("< FlowAccessController > executeFlowComponent IllegalAccessException [com.tg.jfound.proc.flow.FlowComponent]"+iae.getCause(),iae);
        }catch(InstantiationException ie) {
        	Logging.err.println(ie);
        	throw new PException("< FlowAccessController > executeFlowComponent InstantiationException [com.tg.jfound.proc.flow.FlowComponent]"+ie.getCause(),ie);
        }catch(InvocationTargetException ivo) {
        	Logging.err.println(ivo);
        	throw new PException("< FlowAccessController > executeFlowComponent InvocationTargetException [com.tg.jfound.proc.flow.FlowComponent]"+ivo.getCause(),ivo);
	    }catch(Exception e) {
            e.printStackTrace();
	    	//Logging.err.println(e);
	    	//throw new PException("< FlowAccessController > executeFlowComponent [com.tg.jfound.proc.flow.FlowComponent]"+e.getCause(),e);
	    }
			return processMsg;	    	
	    
    }
}