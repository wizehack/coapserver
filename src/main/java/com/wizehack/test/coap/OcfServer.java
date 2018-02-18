package com.wizehack.test.coap;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.EndpointManager;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class OcfServer extends CoapServer {

	private static final int COAP_PORT = NetworkConfig.getStandard().getInt(NetworkConfig.Keys.COAP_PORT);

	/**
	 * Add individual endpoints listening on default CoAP port on all IPv4 addresses of all network interfaces.
	 */
	public void addEndpoints() {
		for (InetAddress addr : EndpointManager.getEndpointManager().getNetworkInterfaces()) {
			// only binds to IPv4 addresses and localhost
			if (addr instanceof Inet4Address || addr.isLoopbackAddress()) {
				InetSocketAddress bindToAddress = new InetSocketAddress(addr, COAP_PORT);
				addEndpoint(new CoapEndpoint(bindToAddress));
			}
		}
	}

	/*
	 * Constructor for a server. Here, the resources
	 * of the server are initialized.
	 */
	public OcfServer() throws SocketException {

		// provide an instance of a resource
		add(new DummyResource());
	}

	/*
	 * Definition of the Resource
	 * refer to http://programmingwithreason.com/article-iot-coap.html
	 */
	class DummyResource extends CoapResource {

		public DummyResource() {

			// set resource identifier
			super("dymmyResource");

			// set display name
			getAttributes().setTitle("dymmy Resource");
		}

		@Override
		public void handleGET(CoapExchange exchange) {
			int format = exchange.getRequestOptions().getContentFormat();;
			String plain = exchange.getRequestText();
			String responseTxt = "Received text: '" + plain + "'";
			System.out.println(responseTxt);
			exchange.respond("GET_REQUEST_SUCCESS");

			if (format == MediaTypeRegistry.TEXT_PLAIN) {
				System.out.println("format: " + format);
			}
		}

		@Override
		public void handlePOST(CoapExchange exchange) {
			int format = exchange.getRequestOptions().getContentFormat();;
			String plain = exchange.getRequestText();
			String responseTxt = "Received text: '" + plain + "'";
			System.out.println(responseTxt);
			exchange.respond("POST_REQUEST_SUCCESS");

			if (format == MediaTypeRegistry.TEXT_PLAIN) {
				System.out.println("format: " + format);
			}
		}

		@Override
		public void handlePUT(CoapExchange exchange) {
			int format = exchange.getRequestOptions().getContentFormat();;
			String payload = exchange.getRequestText();
			String responseTxt = "Received text: '" + payload + "'";
			System.out.println(responseTxt);
			exchange.respond("PUT_REQUEST_SUCCESS");

			if (format == MediaTypeRegistry.TEXT_PLAIN) {
				System.out.println("format: " + format);
			}

			if(format == MediaTypeRegistry.APPLICATION_JSON) {

	            JSONParser jsonParser = new JSONParser();
	            try {
					JSONObject jsonObj = (JSONObject) jsonParser.parse(payload);
					// parse json file
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}
}
