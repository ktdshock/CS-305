package com.snhu.sslserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class SslServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SslServerApplication.class, args);
	}
}

@RestController
class SslServerController {

	@RequestMapping("/hash")
	public String myHash() throws NoSuchAlgorithmException {
		String data = "Hello World Check Sum!";
		String name = "Kenneth Dandrow";
		String[] splitname = name.split(" ");
		String firstname = splitname[0];
		String lastname = splitname[splitname.length - 1];

		// Hash the name
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] nameHash = md.digest(name.getBytes(StandardCharsets.UTF_8));
		
		// Hash the data string as well
		byte[] dataHash = md.digest(data.getBytes(StandardCharsets.UTF_8));

		// Build the response
		StringBuilder response = new StringBuilder();
		response.append("Data: ").append(data).append("<br><br>");
		response.append("Name: ").append(firstname).append(" ").append(lastname).append("<br><br>");
		response.append("Name SHA-256 Checksum: ").append(bytesToHex(nameHash)).append("<br><br>");
		response.append("Data SHA-256 Checksum: ").append(bytesToHex(dataHash));
		
		return response.toString();
	}

	public String bytesToHex(byte[] sha256) {
		BigInteger hex = new BigInteger(1, sha256);
		StringBuilder checksum = new StringBuilder(hex.toString(16));
		while (checksum.length() < 64) {  // SHA-256 produces a 64-character hex string
			checksum.insert(0, '0');
		}
		return checksum.toString();
	}
}
