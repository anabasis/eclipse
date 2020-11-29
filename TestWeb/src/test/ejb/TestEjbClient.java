package test.ejb;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

// EJB »ı·«
//import example.ejb.GuGuDanHome;
//import example.ejb.GuGuDanRemote;

public class TestEjbClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// TODO Auto-generated method stub

		TestEjbClient client = new TestEjbClient();
		int servercnt = Integer.parseInt(args[0]);
		int callcnt = Integer.parseInt(args[1]);
		// System.out.println("EJB Cache Start >>>>>>>>>");
		// for(int i=1;i<=servercnt;i++) client.callEJBCache(i, callcnt);
		System.out.println("EJB NoCache Start >>>>>>>>>");
		for (int i = 1; i <= servercnt; i++)
			client.callEJB(i, callcnt);

		System.out.println("TEST End >>>>>>>>>");
	}

	public void callEJB(int servercnt, int callcnt) {
		long starttime = 0;
		long endtime = 0;
		try {
			starttime = System.currentTimeMillis();

			String[] t_url = new String[40];

			t_url[0] = "192.168.25.200:8011";
			t_url[1] = "192.168.25.200:8012";
			t_url[2] = "192.168.25.200:8013";
			t_url[3] = "192.168.25.200:8014";
			t_url[4] = "192.168.25.200:8015";
			t_url[5] = "192.168.25.200:8021";
			t_url[6] = "192.168.25.200:8022";
			t_url[7] = "192.168.25.200:8023";
			t_url[8] = "192.168.25.200:8024";
			t_url[9] = "192.168.25.200:8025";

			t_url[10] = "192.168.25.200:8011";
			t_url[11] = "192.168.25.200:8012";
			t_url[12] = "192.168.25.200:8013";
			t_url[13] = "192.168.25.200:8014";
			t_url[14] = "192.168.25.200:8015";
			t_url[15] = "192.168.25.200:8021";
			t_url[16] = "192.168.25.200:8022";
			t_url[17] = "192.168.25.200:8023";
			t_url[18] = "192.168.25.200:8024";
			t_url[19] = "192.168.25.200:8025";

			t_url[20] = "192.168.25.200:8011";
			t_url[21] = "192.168.25.200:8012";
			t_url[22] = "192.168.25.200:8013";
			t_url[23] = "192.168.25.200:8014";
			t_url[24] = "192.168.25.200:8015";
			t_url[25] = "192.168.25.200:8021";
			t_url[26] = "192.168.25.200:8022";
			t_url[27] = "192.168.25.200:8023";
			t_url[28] = "192.168.25.200:8024";
			t_url[29] = "192.168.25.200:8025";

			t_url[30] = "192.168.25.200:8011";
			t_url[31] = "192.168.25.200:8012";
			t_url[32] = "192.168.25.200:8013";
			t_url[33] = "192.168.25.200:8014";
			t_url[34] = "192.168.25.200:8015";
			t_url[35] = "192.168.25.200:8021";
			t_url[36] = "192.168.25.200:8022";
			t_url[37] = "192.168.25.200:8023";
			t_url[38] = "192.168.25.200:8024";
			t_url[39] = "192.168.25.200:8025";

			String provider_url = "t3://";

			for (int i = 0; i < servercnt; i++) {
				provider_url = provider_url + t_url[i] + ",";

			}
			// System.out.println("servercnt : "+servercnt);
			// System.out.println("provider_url : "+provider_url);

			Properties p = new Properties();
			p.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			p.put(Context.PROVIDER_URL, provider_url);

			System.out.print("call Server Name :");
			for (int i = 0; i < callcnt; i++) {
				long initstime = System.currentTimeMillis();

				InitialContext ctx = new InitialContext(p);
				long ctxtime = System.currentTimeMillis();

				Object obj = (Object) ctx.lookup("GuGuDan");
				// Be good and use RMI remote object narrowing as required by
				// the
				// EJB specification.
				long lookuptime = System.currentTimeMillis();

				//GuGuDanHome ejbHome = (GuGuDanHome) PortableRemoteObject.narrow(obj, GuGuDanHome.class);
				long hometime = System.currentTimeMillis();

				// Use the HelloHome to create a HelloObject
				//GuGuDanRemote ejbRemote = ejbHome.create();
				long initetime = System.currentTimeMillis();
				/*
				System.out.print(ejbRemote.getServerName() + "("
						+ (ctxtime - initstime) + "+" + (lookuptime - ctxtime)
						+ "+" + (hometime - lookuptime) + "+"
						+ (initetime - hometime) + "="
						+ (initetime - initstime) + "),");
					*/

				// ctx = null;ejbHome=null;ejbRemote=null;obj = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			endtime = System.currentTimeMillis();
			System.out.println("EJB NoCache : server[" + servercnt + "],call["
					+ callcnt + "],execute[" + (endtime - starttime) + "ms]");
		}
	}

	public void callEJBCache(int servercnt, int callcnt) {
		long starttime = 0;
		long endtime = 0;
		try {
			starttime = System.currentTimeMillis();

			String[] t_url = new String[40];

			t_url[0] = "192.168.25.200:8011";
			t_url[1] = "192.168.25.200:8012";
			t_url[2] = "192.168.25.200:8013";
			t_url[3] = "192.168.25.200:8014";
			t_url[4] = "192.168.25.200:8015";
			t_url[5] = "192.168.25.200:8021";
			t_url[6] = "192.168.25.200:8022";
			t_url[7] = "192.168.25.200:8023";
			t_url[8] = "192.168.25.200:8024";
			t_url[9] = "192.168.25.200:8025";

			t_url[10] = "192.168.25.200:8011";
			t_url[11] = "192.168.25.200:8012";
			t_url[12] = "192.168.25.200:8013";
			t_url[13] = "192.168.25.200:8014";
			t_url[14] = "192.168.25.200:8015";
			t_url[15] = "192.168.25.200:8021";
			t_url[16] = "192.168.25.200:8022";
			t_url[17] = "192.168.25.200:8023";
			t_url[18] = "192.168.25.200:8024";
			t_url[19] = "192.168.25.200:8025";

			t_url[20] = "192.168.25.200:8011";
			t_url[21] = "192.168.25.200:8012";
			t_url[22] = "192.168.25.200:8013";
			t_url[23] = "192.168.25.200:8014";
			t_url[24] = "192.168.25.200:8015";
			t_url[25] = "192.168.25.200:8021";
			t_url[26] = "192.168.25.200:8022";
			t_url[27] = "192.168.25.200:8023";
			t_url[28] = "192.168.25.200:8024";
			t_url[29] = "192.168.25.200:8025";

			t_url[30] = "192.168.25.200:8011";
			t_url[31] = "192.168.25.200:8012";
			t_url[32] = "192.168.25.200:8013";
			t_url[33] = "192.168.25.200:8014";
			t_url[34] = "192.168.25.200:8015";
			t_url[35] = "192.168.25.200:8021";
			t_url[36] = "192.168.25.200:8022";
			t_url[37] = "192.168.25.200:8023";
			t_url[38] = "192.168.25.200:8024";
			t_url[39] = "192.168.25.200:8025";

			String provider_url = "t3://";

			for (int i = 0; i < servercnt; i++) {
				provider_url = provider_url + t_url[i] + ",";

			}
			// System.out.println("servercnt : "+servercnt);
			// System.out.println("provider_url : "+provider_url);
			Properties p = new Properties();
			p.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			p.put(Context.PROVIDER_URL, provider_url);

			InitialContext ctx = new InitialContext(p);

			Object obj = (Object) ctx.lookup("GuGuDan");
			// Be good and use RMI remote object narrowing as required by the
			// EJB specification.
			//GuGuDanHome ejbHome = (GuGuDanHome) PortableRemoteObject.narrow(obj, GuGuDanHome.class);

			// Use the HelloHome to create a HelloObject
			//GuGuDanRemote ejbRemote = ejbHome.create();
			System.out.print("call Server Name :");
			for (int i = 0; i < callcnt; i++) {

				//System.out.print(ejbRemote.getServerName() + ",");

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			endtime = System.currentTimeMillis();
			System.out.println("EJB Cache : server[" + servercnt + "],call["
					+ callcnt + "],execute[" + (endtime - starttime) + "ms]");
		}
	}
}
