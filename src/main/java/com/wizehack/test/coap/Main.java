package com.wizehack.test.coap;

import java.net.SocketException;

public class Main {

	public static void main(String[] args) {
		/*
		 * Application entry point.
		 */
		try {

			// create server
			OcfServer server = new OcfServer();
			// add endpoints on all IP addresses
			server.addEndpoints();
			server.start();

		} catch (SocketException e) {
			System.err.println("Failed to initialize server: " + e.getMessage());
		}
	}

}
