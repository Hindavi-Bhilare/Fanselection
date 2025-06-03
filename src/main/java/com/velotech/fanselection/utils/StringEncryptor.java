
package com.velotech.fanselection.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

public class StringEncryptor {

	public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";

	public static final String DES_ENCRYPTION_SCHEME = "DES";

	public static final String DEFAULT_ENCRYPTION_KEY = "This is a fairly long phrase used to encrypt";

	private KeySpec keySpec;

	private SecretKeyFactory keyFactory;

	private Cipher cipher;

	private static final String UNICODE_FORMAT = "UTF8";

	/**
	 * constructor to initialise the encryption scheme type
	 */
	public StringEncryptor(String encryptionScheme) // throws
													// EncryptionException
	{

		this(encryptionScheme, DEFAULT_ENCRYPTION_KEY);
	}

	/**
	 * constructor to initialise the encryption scheme type and encyption key
	 */
	public StringEncryptor(String encryptionScheme, String encryptionKey)
	// throws EncryptionException
	{

		// System.out.println("encryptionScheme : "+encryptionScheme);
		// System.out.println("encryptionKey : "+encryptionKey);

		if (encryptionKey == null)
			throw new IllegalArgumentException("encryption key was null");

		/*
		 * if ( encryptionKey.trim().length() < 10 ) throw new
		 * IllegalArgumentException(
		 * "encryption key was less than 10 characters" );
		 */

		try {
			byte[] keyAsBytes = encryptionKey.getBytes(UNICODE_FORMAT);

			if (encryptionScheme.equals(DESEDE_ENCRYPTION_SCHEME)) {
				keySpec = new DESedeKeySpec(keyAsBytes);
			} else if (encryptionScheme.equals(DES_ENCRYPTION_SCHEME)) {
				keySpec = new DESKeySpec(keyAsBytes);
			} else {

			}

			keyFactory = SecretKeyFactory.getInstance(encryptionScheme);
			cipher = Cipher.getInstance(encryptionScheme);

		} catch (InvalidKeyException e) {

			// throw new EncryptionException( e );
		} catch (UnsupportedEncodingException e) {

			// throw new EncryptionException( e );
		} catch (NoSuchAlgorithmException e) {

			// throw new EncryptionException( e );
		} catch (NoSuchPaddingException e) {

			// throw new EncryptionException( e );
		} catch (Exception e) {

			// throw new EncryptionException( e );
		}

	}

	/**
	 * This method is used to encrypt the String on the key provided
	 * 
	 * @param unencryptedString
	 *            - String to be encrypted
	 * @return String - encrypted String
	 */
	public String encrypt(String unencryptedString) // throws
													// EncryptionException
	{

		byte[] ciphertext = null;

		if (unencryptedString == null || unencryptedString.trim().length() == 0) {

			return new String(Base64.getEncoder().encode(ciphertext));
		}

		ciphertext = unencryptedString.getBytes();

		try {
			SecretKey key = keyFactory.generateSecret(keySpec);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] cleartext = unencryptedString.getBytes(UNICODE_FORMAT);
			ciphertext = cipher.doFinal(cleartext);
		} catch (Exception e) {

			// throw new EncryptionException( e );
		}

		return new String(Base64.getEncoder().encode(ciphertext));
	}

	/**
	 * This method is used to decrypt the String on the key provided
	 * 
	 * @param encryptedString
	 *            - String to be decrypted
	 * @return String - decrypted String
	 */
	public String decrypt(String encryptedString) // throws EncryptionException
	{

		// BandBrokersLogger.logInfo(CLASSNAME,METHODNAME," decrypting String "
		// );

		byte[] ciphertext = null;

		if (encryptedString == null || encryptedString.trim().length() <= 0) {

			return bytes2String(ciphertext);
		}

		ciphertext = encryptedString.getBytes();

		try {
			SecretKey key = keyFactory.generateSecret(keySpec);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] cleartext = Base64.getDecoder().decode(encryptedString);
			ciphertext = cipher.doFinal(cleartext);
		} catch (Exception e) {

			// throw new EncryptionException( e );
		}
		return bytes2String(ciphertext);
	}

	/**
	 * This method is used to convert byte array into String on the key provided
	 * 
	 * @param bytes
	 *            - Array of bytes
	 * @return String - String converted
	 */
	private static String bytes2String(byte[] bytes) {

		StringBuffer stringBuffer = new StringBuffer();
		for (Integer i = 0; i < bytes.length; i++) {
			stringBuffer.append((char) bytes[i]);
		}
		return stringBuffer.toString();
	}

	/**
	 * an inner class for Encryption exception
	 */
	public static class EncryptionException extends Exception {

		public EncryptionException(Throwable t) {

			super(t);
		}
	}
}