package com.issuer.payment.processor.web;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.cactoos.collection.Sticky;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rs.xe.XeAppend;

public final class TkOutputTransactionRouting implements Take {

	@Override
	public Response act(Request req) throws Exception {
		
		final RqHref.Smart rqHref = new RqHref.Smart(new RqGreedy(req));
		
		final String rrn = rqHref.single("rrn");
		final String pan = rqHref.single("pan");
		
		final String marker = String.format("Transaction (rrn=%s, pan=%s)", rrn, pan);
		
		final StringBuilder builder = new StringBuilder();
		
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("logs/app.log")))) {
			String line;
			while((line = br.readLine()) != null) {
				if(line.contains(marker) && !(line.contains("Started --------------------------------") || line.contains("Finished --------------------------------"))) {
					final String message = line.replace(String.format(" INFO %s", marker), "").trim();
					builder.append(message).append("\n");
				}
			}
		}
		
		return new RsPage(
			"/xsl/transaction_routing_output.xsl", 
			req, 
			() -> new Sticky<>(
				new XeAppend("menu", "route-transaction"),
				new XeAppend("rrn", rrn),
				new XeAppend("pan", pan),
				new XeAppend("logs", builder.toString())  
			)
		);
	}

}
