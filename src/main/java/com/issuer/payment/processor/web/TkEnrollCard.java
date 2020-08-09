package com.issuer.payment.processor.web;

import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;
import javax.json.JsonObject;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.cactoos.collection.Sticky;
import org.hamcrest.Matchers;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeAppend;
import com.jcabi.http.request.JdkRequest;
import com.jcabi.http.response.JsonResponse;
import com.jcabi.http.response.RestResponse;

/**
 * Take that enrolls a card.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @author Olivier B. OURA (baudolivier.oura@gmail.com)
 */
public final class TkEnrollCard implements Take {
	
	private final String suratiUrl;
	
	public TkEnrollCard(final String suratiUrl) {
		this.suratiUrl = suratiUrl;
	}
	
	@Override
	public Response act(Request req) throws Exception {	
		
		final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
		
		final String pan = form.single("pan");
		final String cardHolder = form.single("card_holder");
		final String login = form.single("login");
		final String phone = form.single("phone");
		final String password = form.single("password");
		final CardNetwork network = CardNetwork.valueOf(form.single("network"));
		
		try {
						
			final String formBody = String.format(
					"pan=%s&card_holder=%s&login=%s&phone=%s&password=%s&network=%s&validity_date=%s", 
					pan, 
					cardHolder, 
					login, 
					phone, 
					password, 
					network.name(), 
					form.single("validity_date")
			);
			
			final RestResponse response = new JdkRequest(suratiUrl)
											.uri().path("/api/card").back()
											.method(com.jcabi.http.Request.POST)
											.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_FORM_URLENCODED)
											.fetch(new ByteArrayInputStream(formBody.getBytes()))
											.as(RestResponse.class)
											.assertStatus(Matchers.isOneOf(HttpURLConnection.HTTP_CREATED, HttpURLConnection.HTTP_BAD_REQUEST));
			
			if(response.status() == HttpURLConnection.HTTP_BAD_REQUEST) {
				
				final JsonObject rpJson = response.as(JsonResponse.class)
												  .json()
												  .read()
												  .asJsonObject();
				
				throw new IllegalArgumentException(rpJson.getString("message"));
			}
			
			return new RsPage(
				"/xsl/enroll_card_output.xsl", 
				req, 
				() -> new Sticky<>(
					new XeAppend("menu", "cards-enrolled"),
					new XeAppend("pan", pan),
					new XeAppend("message", String.format("Card N° %s has been enrolled with success !", pan))
				)
			);
			
		} catch (IllegalArgumentException e) {
			
			return new RsPage(
				"/xsl/enroll_card_output.xsl", 
				req, 
				() -> new Sticky<>(
					new XeAppend("menu", "cards-enrolled"),
					new XeAppend("pan", pan),
					new XeAppend("message", e.getLocalizedMessage())
				)
			);			
		}
	}

}
