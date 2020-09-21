package com.slug.proc.flow;

import java.lang.reflect.InvocationTargetException;

import com.slug.exception.PException;
import com.slug.logging.Logging;
import com.slug.proc.component.FlowComponent;
import com.slug.proc.component.FlowComponentImpl;
import com.slug.proc.msg.ProcessMsg;
import com.slug.vo.box.Box;





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
        	throw new PException("< FlowAccessController > executeFlowComponent ClassNotFoundException [com.slug.proc.flow.FlowComponent]"+cn.getCause(),cn);
        }catch(NoSuchMethodException nm) {
        	Logging.err.println(nm);
        	throw new PException("< FlowAccessController > executeFlowComponent NoSuchMethodException [com.slug.proc.flow.FlowComponent]"+nm.getCause(),nm);
        }catch(IllegalAccessException iae) {
        	Logging.err.println(iae);
        	throw new PException("< FlowAccessController > executeFlowComponent IllegalAccessException [com.slug.proc.flow.FlowComponent]"+iae.getCause(),iae);
        }catch(InstantiationException ie) {
        	Logging.err.println(ie);
        	throw new PException("< FlowAccessController > executeFlowComponent InstantiationException [com.slug.proc.flow.FlowComponent]"+ie.getCause(),ie);
        }catch(InvocationTargetException ivo) {
        	Logging.err.println(ivo);
        	throw new PException("< FlowAccessController > executeFlowComponent InvocationTargetException [com.slug.proc.flow.FlowComponent]"+ivo.getCause(),ivo);
	    }catch(Exception e) {
            e.printStackTrace();
	    	//Logging.err.println(e);
	    	//throw new PException("< FlowAccessController > executeFlowComponent [com.slug.proc.flow.FlowComponent]"+e.getCause(),e);
	    }
			return processMsg;	    	
	    
    }
}