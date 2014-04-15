package com.slug.proc.component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Loader;
import javassist.NotFoundException;
import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

import com.slug.exception.PException;
import com.slug.logging.Logging;
import com.slug.proc.msg.ProcessMsg;
import com.slug.vo.box.Box;


public class FlowComponent implements FlowComponentImpl{
	
	private static FlowComponent fc;
	//private static ClassPool root_pool ;
	//private static ClassPool child_pool;
	//private static Loader cl;
 
	public static FlowComponent getInstance(){
		
		if(fc == null){
			fc = new FlowComponent();
			Logging.dev.println("<FlowComponent> <getInstance> Create FlowComponent Class..........................");
		}else{
			Logging.dev.println("<FlowComponent> <getInstance> Delegate FlowComponent Class..........................");
		}
		
/*		
		if(root_pool == null){
			root_pool = new ClassPool(true);
			child_pool = new ClassPool(root_pool);
			child_pool.appendSystemPath();
			child_pool.childFirstLookup = true;
			child_pool.insertClassPath(new ClassClassPath(fc.getClass()));
			cl = new Loader(child_pool);
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
			Logging.dev.println("<FlowComponent> <getInstance> Create ClassPool Class..........................");
		}else{
			Logging.dev.println("<FlowComponent> <getInstance> Delegate ClassPool Class..........................");
		}
*/		
		return fc;
	}
	
	public ProcessMsg executeFlow(Box ibox)throws PException{
    	
 		String className  = ibox.getString("tflow");
    	String methodName = ibox.getString("taction");
    	Object obj = null;
    	//ProxyFactory fac = new ProxyFactory();

 		try
        {
 			
 	    	/*
 			
			Class[] param_type = {Class.forName("com.tg.jfound.vo.box.Box")};
			CtClass[] pt = {child_pool.get("com.tg.jfound.vo.box.Box")};
 			CtClass cc = child_pool.get(className);
 			Logging.dev.println(child_pool.toString());
 			CtMethod cm = cc.getDeclaredMethod(methodName);
 			
 			 //fac.setSuperclass(Foo.class);
 			 MethodHandler mi = new MethodHandler() {
 			     public Object invoke(Object self, Method m, Method proceed,
 			                          Object[] args) throws Throwable {
 			         System.out.println("Name: " + m.getName());
 			         return proceed.invoke(self, args);  // execute the original method.
 			     }
 			 };
 			 fac.setFilter(new MethodFilter() {
 			     public boolean isHandled(Method m) {
 			         // ignore finalize()
 			         return !m.getName().equals("finalize");
 			     }
 			 });
 			 //Class c = f.createClass();
 			// Foo foo = (Foo)c.newInstance();
 			 ((ProxyObject)foo).setHandler(mi);


 			*/
 			/**
 			 * Case001: Class�� pool�� ����ϰ� Method�� ������ ��� ��� Invoking��Ų��.
 			 */

 			/*
 			Class tc = cc.toClass();
 			Logging.dev.println("01----------------"+tc.getName());
 			Method method = tc.getDeclaredMethod(methodName,param_type);
 			Logging.dev.println("02----------------"+method.getName());
			obj  = method.invoke(tc.newInstance(), new Object[] {ibox});
			Logging.dev.println("< FlowComponent >< executeFlow >  ["+className+"] Execute_Action [" + methodName+"]");
 			*/
			/**
			 * Case000: ������ reflet �� �̿��� ���
			 */
			Class[] param_type = {Class.forName("com.tg.jfound.vo.box.Box")};	
			Class c = Class.forName(className);
			Method method = c.getDeclaredMethod(methodName,param_type);
			obj  = method.invoke(c.newInstance(), new Object[] {ibox});
			    
			Logging.dev.println("< FlowComponent >< executeFlow > End ");
		
		}catch(ClassNotFoundException cn) {
        	Logging.err.println(cn);
        	throw new PException("< FlowComponent > ClassNotFoundException executeFlow ["+className+"."+methodName+"()]"+cn.getCause(),cn);
///*
        }catch(NoSuchMethodException nm) {
        	Logging.err.println(nm);
        	throw new PException("< FlowComponent > NoSuchMethodException executeFlow ["+className+"."+methodName+"()]"+nm.getCause(),nm);

        }catch(IllegalAccessException iae) {
        	Logging.err.println(iae);
        	throw new PException("< FlowComponent > IllegalAccessException executeFlow ["+className+"."+methodName+"()]"+iae.getCause(),iae);

        }catch(InstantiationException ie) {
        	Logging.err.println(ie);
        	throw new PException("< FlowComponent > executeFlow InstantiationException ["+className+"."+methodName+"()]"+ie.getCause(),ie);

        }catch(InvocationTargetException ivo) {
            ivo.printStackTrace();
        	//Logging.err.println(ivo);
        	//throw new PException("< FlowComponent > executeFlow InvocationTargetException ["+className+"."+methodName+"()]"+ivo.getCause(),ivo);
        }
//*/
        
        return (ProcessMsg)obj;

        //return obj;
	}
}
