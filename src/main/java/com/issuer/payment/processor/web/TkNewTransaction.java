package com.issuer.payment.processor.web;

import org.cactoos.collection.Sticky;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.xe.XeAppend;

public final class TkNewTransaction implements Take {

	@Override
	public Response act(Request req) throws Exception {
		
		return new RsPage(
				"/xsl/new_transaction.xsl", 
				req, 
				() -> new Sticky<>(
					new XeAppend("menu", "route-transaction")
				)
			);
	}

}
