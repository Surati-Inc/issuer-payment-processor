package com.issuer.payment.processor.web;

import org.takes.Take;
import org.takes.facets.flash.TkFlash;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.facets.forward.TkForward;
import org.takes.tk.TkClasspath;
import org.takes.tk.TkSlf4j;
import org.takes.tk.TkWithType;
import org.takes.tk.TkWrap;

/**
 * App.
 * 
 * @author Olivier B. OURA (baudoliver7@gmail.com)
 *
 */
public final class TkApp extends TkWrap {

	/**
	 * Ctor.
	 */
	public TkApp(final String suratiUrl) {
		super(make(suratiUrl));
	}

	/**
	 * Ctor.
	 * @return Takes
	 */
	private static Take make(final String suratiUrl) {
		return new TkSlf4j(
				new TkForward(
						new TkFlash(
							new TkFork(
								new FkRegex(
									"/css/.+", 
									new TkWithType(new TkClasspath(), "text/css")
								),
								new FkRegex(
									"/img/[a-z]+\\.svg", 
									new TkWithType(new TkClasspath(), "image/svg+xml")
								),
								new FkRegex(
									"/img/[a-z]+\\.png", 
									new TkWithType(new TkClasspath(), "image/png")
								),
								new FkRegex("/robots\\.txt", ""),
								new FkRegex("/", new TkIndex()),
								new FkRegex("/transaction/new", new TkNewTransaction()),
								new FkRegex("/transaction/route", new TkRouteTransactionToSurati(suratiUrl)),
								new FkRegex("/transaction/routing/output", new TkOutputTransactionRouting()),
								new FkRegex("/card/activate", new TkActivateCard()),
								new FkRegex("/card/deactivate", new TkDeactivateCard())
							)
						)
					)
				);
	}
}
