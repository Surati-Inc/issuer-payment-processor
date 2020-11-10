package com.issuer.payment.processor.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matchers;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.RsJson;
import org.takes.rs.RsWithStatus;

import com.jcabi.http.request.JdkRequest;
import com.jcabi.http.response.JsonResponse;
import com.jcabi.http.response.RestResponse;

public final class TkRouteTransactionToSuratiApi implements Take {

	final String suratiUrl;
	final Random r = new Random();
	
	public TkRouteTransactionToSuratiApi(final String suratiUrl) {
		this.suratiUrl = suratiUrl;
	}
	
	@Override
	public Response act(Request req) throws Exception {
			
		try {
			
			final RqFormSmart rqForm = new RqFormSmart(new RqGreedy(req));
			
			final String designation = rqForm.single("designation");
			final Integer amount = Integer.parseInt(rqForm.single("amount"));
			final String currencyCode = rqForm.single("currency_code");
			final String pan = rqForm.single("pan");
					
			String rrn;
			do {
				rrn = newRRN();
			} while(isRRNExists(pan, rrn));
				
			final LocalDateTime now = LocalDateTime.now();
			
			final Map<String, String> fields = new HashMap<>();
			fields.put("2", pan);
			fields.put("3", "003040"); // achat en ligne
			fields.put("4", StringUtils.rightPad(StringUtils.leftPad(amount.toString(), 10, "0"), 12, "0"));
			fields.put("7", now.format(DateTimeFormatter.ofPattern("MMddHHmmss")));
			fields.put("11", "000001");
			fields.put("12", now.format(DateTimeFormatter.ofPattern("HHmmss")));
			fields.put("13", now.format(DateTimeFormatter.ofPattern("MMdd")));
			fields.put("22", "010");
			fields.put("37", rrn);
			fields.put("41", "1@surati");
			fields.put("42", "surati@market07");
			fields.put("43", "01BP14895YAMOUSSOUKRO01YAKROMOROFE02RLCI");
			fields.put("49", currencyCode);
			fields.put("73", now.format(DateTimeFormatter.ofPattern("yyMMdd")));
			fields.put("104", designation.substring(0, Math.min(100, designation.length()))); 
			
			final StringBuilder body = new StringBuilder("");
			for (String key : fields.keySet()) {
				if(!body.toString().equals("")) {
					body.append("&");
				}
				
				body.append(String.format("%s=%s", key, fields.get(key))); 
			}
			
			JsonObject json = TkRouteTransactionToSurati.routeTransaction(
				rrn, 
				pan, 
				suratiUrl, 
				this, 
				body.toString()
			);
			
			return new RsWithStatus(
					new RsJson(
						Json.createObjectBuilder()
						    .add("message", String.format("Transaction successfully routed to Surati with response %s (%s) !", json.getString("response_message"), json.getString("response_code")))
						    .build()
					), 
					HttpURLConnection.HTTP_CREATED
			);
		} catch (IllegalArgumentException e) {
			return new RsWithStatus(
					new RsJson(
						Json.createObjectBuilder()
						.add("message", e.getLocalizedMessage())
						.build()
					),
					HttpURLConnection.HTTP_BAD_REQUEST
				);
			} catch (Exception e) {
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
	
	private boolean isRRNExists(String pan, String rrn) throws IOException {
		final String body = String.format("pan=%s&rrn=%s", pan, rrn);
		final RestResponse response = new JdkRequest(suratiUrl)
				.uri().path("/api/transaction/check/rrn").back()
				.method(com.jcabi.http.Request.POST)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED)
				.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
				.fetch(new ByteArrayInputStream(body.getBytes()))
				.as(RestResponse.class)
				.assertStatus(Matchers.isOneOf(HttpURLConnection.HTTP_OK, HttpURLConnection.HTTP_NOT_FOUND, HttpURLConnection.HTTP_BAD_REQUEST));
		
		if(response.status() == HttpURLConnection.HTTP_BAD_REQUEST) {
			
			final JsonObject rsJson = response.as(JsonResponse.class)
					  .json()
					  .read()
					  .asJsonObject();
			
			throw new IllegalArgumentException(rsJson.getString("message"));
		}
			
		return response.status() == HttpURLConnection.HTTP_OK;
	}
	
	private String newRRN() {
		
		String result;
		
		do {
			long low = 1;
			long high = 100000000000L;
			Long number = (long) (Math.random() * (high - low)) + low;
			result = StringUtils.leftPad(number.toString(), 12, '0');
		} while(result.length() != 12);
		
		return result;
	}

}
