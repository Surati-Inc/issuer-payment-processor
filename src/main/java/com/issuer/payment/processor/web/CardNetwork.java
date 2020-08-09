package com.issuer.payment.processor.web;

/**
 * Card networks supported
 * @author Olivier B. OURA (baudoliver7@gmail.com)
 *
 */
public enum CardNetwork {
	
	NONE("NONE"),
	VISA("VISA"),
	MASTERCARD("MASTERCARD"),
	GIM_UEMOA("GIM-UEMOA"),
	AMERICAN_EXPRESS("AMERICAN EXPRESS");
	
	private final String name;
	
	/**
	 * Ctor.
	 * @param name Card network name
	 */
	private CardNetwork(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
