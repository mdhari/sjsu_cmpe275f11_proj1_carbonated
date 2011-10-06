package edu.sjsu.carbonated;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;



/**
 * Session Bean implementation class FirstEJB
 */
@Stateless
@Remote(FirstEJBRemote.class)
public class FirstEJB implements FirstEJBRemote {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5549425481669464623L;

	public String echo(String filename) {
		CSVtoJSON csvToJson = new CSVtoJSON();
		return csvToJson.convertCSVFile(filename);
	}
	
	@AroundInvoke
	public Object preCheck(InvocationContext ctx) throws Exception {
		System.out.println("Echo's Preflight check!");

		return ctx.proceed();
	}

}
