package com.issuer.payment.processor.web;

import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.util.logging.Level;

import javax.json.Json;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.RsJson;
import org.takes.rs.RsWithStatus;

import com.jcabi.log.Logger;

/**
 * Takes that deactivates a bank card
 * 
 * @author Olivier B. OURA (baudolivier.oura@gmail.com)
 *
 */
public final class TkDeactivateCard implements Take {
	
	
	@Override
	public Response act(Request req) throws Exception {
		
		final RqFormSmart rqForm = new RqFormSmart(new RqGreedy(req));
		
		try {
			
			final String rrn = rqForm.single("rrn");
			final String pan = rqForm.single("pan");
			
			Logger.info(this, "Transaction (rrn=%s, pan=%s) : Surati asks %s to deactivate the card.", rrn, pan, Main.APP_NAME);
			
			Thread.sleep(100);
			
			Logger.info(this, "Transaction (rrn=%s, pan=%s) : %s deactivates the card.", rrn, pan, Main.APP_NAME);
			
			Thread.sleep(100);
			
			return new RsWithStatus(
				HttpURLConnection.HTTP_OK
			);
			
		} catch(IllegalArgumentException ex) {
			return new RsWithStatus(
					new RsJson(
						Json.createObjectBuilder()
						.add("message", ex.getLocalizedMessage())
						.build()
					),
					HttpURLConnection.HTTP_BAD_REQUEST
				);
		} catch (Exception e) {
			e.printStackTrace(new PrintStream(Logger.stream(Level.WARNING, e)));
			return new RsWithStatus(
				new RsJson(
						Json.createObjectBuilder()
						.add("message", e.getLocalizedMessage())
						.build()
					),
				HttpURLConnection.HTTP_INTERNAL_ERROR
			);
		}
	}

}
