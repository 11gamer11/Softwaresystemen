package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;

import info.Logging;

public class Security {
	private static final int SS_SECURITY_PORT = 2013;
	private static final String SS_SECURITY_HOST = "ss-security.student.utwente.nl";
	
	private static String get(String command) throws IOException {
		Socket socket = new Socket(SS_SECURITY_HOST, SS_SECURITY_PORT);
		new PrintStream(socket.getOutputStream()).println(command);
		String result = new BufferedReader(new InputStreamReader(
				socket.getInputStream())).readLine();
		Logging.log(0, result);
		socket.close();
		return result;
	}

	public static PublicKey getPublicKey(String user) {
		try {
			byte[] data = Security.base64Decode(get("PUBLICKEY " + user).split(
					" ")[1]);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(data);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePublic(keySpec);
		} catch (InvalidKeySpecException e) {
			Logging.log(Logging.ERROR,
					"Could not convert received data into key specification ["
							+ e.getMessage() + "]");
		} catch (NoSuchAlgorithmException e) {
			Logging.log(
					Logging.ERROR,
					"Could not convert received data into Public Key ["
							+ e.getMessage() + "]");
		} catch (IOException e) {
			Logging.log(
					Logging.ERROR,
					"Error communicating with Security Server ["
							+ e.getMessage() + "]");
		}
		return null;
	}

	public static PrivateKey getPrivateKey(String user, String pass) {
		try {
			byte[] data = Security.base64Decode(get(
					"IDPLAYER " + user + " " + pass).split(" ")[1]);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(data);
			KeyFactory keyFactory;
			keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			Logging.log(
					Logging.ERROR,
					"Could not convert data into Private Key ["
							+ e.getMessage() + "]");
		} catch (InvalidKeySpecException e) {
			Logging.log(
					Logging.ERROR,
					"Could not convert data into key specification ["
							+ e.getMessage() + "]");
		} catch (IOException e) {
			Logging.log(
					Logging.ERROR,
					"Error communicating with Security Server ["
							+ e.getMessage() + "]");
		}

		return null;

	}

	/**
	 * Converteert bytes naar een Base64-String. Wrapper voor de apache library
	 * functie.
	 * 
	 * @param data
	 *            De bytes
	 * @return De String
	 */
	public static String base64Encode(byte[] data) {
		return Base64.encodeBase64String(data);
	}

	/**
	 * Converteert een Bas64-String naar bytes. Wrapper voor de apache library
	 * functie.
	 * 
	 * @param data
	 *            De String
	 * @return De bytes
	 */
	public static byte[] base64Decode(String data) {
		return Base64.decodeBase64(data);
	}

	public static boolean verify(byte[] cypherText, byte[] original,
			PublicKey publicKey) {
		if (cypherText == null || original == null || publicKey == null) {
			return false;
		}

		Signature signature;
		try {
			signature = Signature.getInstance("SHA1withRSA");
			signature.initVerify(publicKey);
			signature.update(original);
			return signature.verify(cypherText);
		} catch (NoSuchAlgorithmException e) {
			Logging.log(Logging.ERROR,
					"Could not verify key [" + e.getMessage() + "]");
		} catch (InvalidKeyException e) {
			Logging.log(Logging.ERROR, "Invalid key [" + e.getMessage() + "]");
		} catch (SignatureException e) {
			Logging.log(Logging.ERROR, "Signature exception [" + e.getMessage()
					+ "]");
		}
		return false;
	}

	public static byte[] sign(byte[] original, PrivateKey pKey) {
		Signature sign;
		try {
			sign = Signature.getInstance("SHA1withRSA");
			sign.initSign(pKey);
			Logging.log(0, "Original: " + Arrays.toString(original));
			sign.update(original);
			return sign.sign();
		} catch (NoSuchAlgorithmException e) {
			Logging.log(Logging.ERROR,
					"Could not verify key [" + e.getMessage() + "]");
		} catch (InvalidKeyException e) {
			Logging.log(Logging.ERROR, "Invalid key [" + e.getMessage() + "]");
		} catch (SignatureException e) {
			Logging.log(Logging.ERROR, "Signature exception [" + e.getMessage()
					+ "]");
		}
		return null;

	}
}
