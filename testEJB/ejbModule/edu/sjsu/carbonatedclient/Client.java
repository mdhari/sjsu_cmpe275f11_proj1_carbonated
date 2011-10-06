package edu.sjsu.carbonatedclient;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import edu.sjsu.carbonated.FirstEJBRemote;

public class Client {
	private static Logger log = Logger.getLogger(Client.class);
	String endpoint; // localhost:1099
	Context ctx;

	public Client(String endpoint) {
		this.endpoint = endpoint;
	}

	/**
	 * NOTE: This method is needed when connecting to an EJB from _outside_ of
	 * the application server (e.g., jboss). If we are running from within
	 * jboss; jboss has a context that you do not need to do this (use @EJB
	 * instead)
	 * 
	 * @return
	 * @throws Exception
	 */
	private Context getContext() throws Exception {
		if (ctx != null)
			return ctx;

		Properties props = new Properties();
		props.put(Context.PROVIDER_URL, "jnp://" + endpoint);
		props.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jnp.interfaces.NamingContextFactory");
		props.put(Context.URL_PKG_PREFIXES,
				"org.jboss.naming:org.jnp.interfaces");
		ctx = new InitialContext(props);

		return ctx;
	}

	public void saySomething(String msg) {
		try {
			long st = System.currentTimeMillis();
			Context ctx = getContext();
			Object ref = ctx.lookup("FirstEJB/remote");
			FirstEJBRemote svr = (FirstEJBRemote) PortableRemoteObject.narrow(ref,
					FirstEJBRemote.class);
			long mt = System.currentTimeMillis();
			System.out.println("From the server: " + svr.echo(msg));
			long et = System.currentTimeMillis();

			System.out.println("Connect: " + (mt - st));
			System.out.println("Message: " + (et - mt));
			System.out.println("Total:   " + (et - st));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BasicConfigurator.configure();
		Client clt = new Client("localhost:1099");
		clt.saySomething("AA.dat");

	}

}