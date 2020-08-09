package com.issuer.payment.processor.web;

import java.util.Collection;
import java.util.LinkedList;

import org.cactoos.collection.Sticky;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeDirectives;
import org.takes.rs.xe.XeSource;
import org.xembly.Directives;

/**
 * Take that allows to enter details for card to enroll.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @author Olivier B. OURA (baudolivier.oura@gmail.com)
 */
public final class TkNewCardToEnroll implements Take {
	
	@Override
	public Response act(Request req) throws Exception {
		
		final Collection<XeSource> networkSources = new LinkedList<>();
		for (CardNetwork network : CardNetwork.values()) {
			if(network == CardNetwork.NONE)
				continue;
			networkSources.add(
				new XeDirectives(
					new Directives().add("network")
									.add("id").set(network.name()).up()
									.add("name").set(network.toString()).up()
									.up()
				)
			);
		}
		
		return new RsPage(
				"/xsl/new-card-to-enroll.xsl", 
				req, 
				() -> new Sticky<>(
					new XeAppend("menu", "cards-enrolled"),
					new XeAppend("networks", new XeChain(networkSources))
				)
			);
	}

}
