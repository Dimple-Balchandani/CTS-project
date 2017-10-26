package com.vs.ConsignmentTrackingSystem.utilities;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import com.vs.ConsignmentTrackingSystem.db.entities.UserEntity;
import com.vs.ConsignmentTrackingSystem.db.entities.UserEntityDAO;
import java.util.logging.Logger;
@Component
public class Authorization {

	private final static Logger LOGGER = Logger.getLogger(Authorization.class.getName());
	
	private static Authorization encryptionDecryption;

	public static Authorization getInstance() {
		if (encryptionDecryption == null)
			encryptionDecryption = new Authorization();
		return encryptionDecryption;
	}

	private Authorization() {
	}

	SecretKey SecKey = generateKey();

	public String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 8) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		return salt.toString();

	}

	public SecretKey generateKey() {
		KeyGenerator KeyGen;
		SecretKey SecKey = null;
		try {
			KeyGen = KeyGenerator.getInstance("AES");
			KeyGen.init(128);
			SecKey = KeyGen.generateKey();
			System.out.println("Key:" + SecKey);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return SecKey;
	}

	public String encrypt(String data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		Cipher AesCipher = Cipher.getInstance("AES");
		AesCipher.init(Cipher.ENCRYPT_MODE, SecKey);
		String encryptedString = Base64.encodeBase64String(AesCipher.doFinal(data.getBytes()));
		return encryptedString;
	}

	private String decrypt(String token) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		Cipher AesCipher = Cipher.getInstance("AES");
		AesCipher.init(Cipher.DECRYPT_MODE, SecKey);
		String decryptedString = new String(AesCipher.doFinal(Base64.decodeBase64(token)));
		return decryptedString;

	}

	public boolean verifyToken(UserEntityDAO userEntityDAO, String authToken) {
		UserEntity userEntity = getUserByAuthToken(userEntityDAO, authToken);
		if (userEntity != null && System.currentTimeMillis() - userEntity.getLoginTime() < (8 * 60 * 60 * 1000))
			return true;
		else
			return false;
	}

	public UserEntity getUserByAuthToken(UserEntityDAO userEntityDAO, String authToken) {
		UserEntity userEntity = null;
		try {
			String decryptedToken = decrypt(authToken);
			String tokenwithoutsalt = decryptedToken.substring(0, decryptedToken.length() - 8);
			String username = tokenwithoutsalt.substring(0, tokenwithoutsalt.lastIndexOf(":"));
			long time = Long.parseLong(tokenwithoutsalt.substring(tokenwithoutsalt.lastIndexOf(":") + 1));
			userEntity = userEntityDAO.findByUsername(username, time);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return userEntity;
	}

	

}
