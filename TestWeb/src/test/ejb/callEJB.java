package test.ejb;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import example.ejb.GuGuDanHome;
import example.ejb.GuGuDanRemote;

public class callEJB {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// TODO Auto-generated method stub

		
		int servercnt = Integer.parseInt( args[0]);
		int callcnt   = Integer.parseInt( args[1]);
		//System.out.println("EJB Cache Start >>>>>>>>>");	
		//for(int i=1;i<=servercnt;i++)   client.callEJBCache(i, callcnt);		
		System.out.println("EJB NoCache Start >>>>>>>>>");		
		TestEjbClient client =new TestEjbClient();
		for(int i=1;i<=servercnt;i++) 	client.callEJB(i, callcnt);

		
		
        System.out.println("test End >>>>>>>>>");		
	}
	
}
