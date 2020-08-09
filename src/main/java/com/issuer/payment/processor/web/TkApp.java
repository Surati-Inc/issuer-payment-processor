package com.issuer.payment.processor.web;

import org.takes.Take;
import org.takes.facets.auth.TkSecure;
import org.takes.facets.flash.TkFlash;
import org.takes.facets.fork.FkMethods;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.facets.forward.TkForward;
import org.takes.tk.TkClasspath;
import org.takes.tk.TkSlf4j;
import org.takes.tk.TkWithType;
import org.takes.tk.TkWrap;

import com.minlessika.secure.TkAnonymous;

/**
 * App.
 * 
 * @author Olivier B. OURA (baudolivier.oura@gmail.com)
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
								new FkRegex(
									"/login", 
									new TkAnonymous(new TkLogin(suratiUrl))
								),
								new FkRegex(
									"/transaction/new", 
									new TkSecure(new TkNewTransaction())
								),
								new FkRegex(
									"/transaction/route", 
									new TkSecure(new TkRouteTransactionToSurati(suratiUrl))
								),
								new FkRegex(
									"/transaction/routing/output", 
									new TkSecure(new TkOutputTransactionRouting())
								),
								new FkRegex(
									"/api/card/activate", 
									new TkFork(
										new FkMethods("POST", new TkActivateCard())
									)						
								),
								new FkRegex(
									"/api/card/deactivate", 
									new TkFork(
										new FkMethods("POST", new TkDeactivateCard()) 
									)
								),
								new FkRegex(
									"/api/transaction/route", 
									new TkFork(
										new FkMethods("POST", new TkRouteTransactionToSuratiApi(suratiUrl))
									)									
								),
								new FkRegex(
									"/card/new", 
									new TkSecure(
										new TkNewCardToEnroll()
									)
								),
								new FkRegex(
									"/card/enroll", 
									new TkSecure(
										new TkEnrollCard(suratiUrl)
									)
								)
							)
						)
					)
				);
	}
}
