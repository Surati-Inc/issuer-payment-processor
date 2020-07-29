package com.issuer.payment.processor.web;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.takes.http.Exit;
import org.takes.http.FtCli;

/**
 * Entry of application
 * 
 * @author Olivier B. OURA (baudoliver7@gmail.com)
 *
 */
public final class Main {
	
	public final static String APP_NAME = "IPP";
	
	/**
	 * App entry
	 * @param args Arguments
	 * @throws Exception If some problems in
	 */
	public static void main(String[] args) throws Exception {
		
		final Map<String, String> argsMap = asMap(args);
		
		// initialize App settings
		final String suratiUrl = argsMap.get("surati-url");
		if(suratiUrl == null)
			throw new IllegalArgumentException("You need to set Surati url !");
		
		new FtCli(
			new TkApp(suratiUrl),
			args
		).start(Exit.NEVER);
		
	}
	
	/**
	 * Transforms list of arguments prefix by -- in a map
	 * @param args Arguments
	 * @return Arguments found
	 */
	private static Map<String, String> asMap(final String[] args) {
        final Map<String, String> map = new HashMap<>(0);
        final Pattern ptn = Pattern.compile("--([a-z\\-]+)(=.+)?");
        for (final String arg : args) {
            final Matcher matcher = ptn.matcher(arg);
            if (!matcher.matches()) {
                throw new IllegalStateException(
                    String.format("can't parse this argument: '%s'", arg)
                );
            }
            final String value = matcher.group(2);
            if (value == null) {
                map.put(matcher.group(1), "");
            } else {
                map.put(matcher.group(1), value.substring(1));
            }
        }
        return map;
    }	
    
}
