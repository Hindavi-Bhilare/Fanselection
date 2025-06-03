package com.velotech.fanselection.utils;

import org.springframework.stereotype.Component;

@Component
public class PasswordUtils {

	/**
	 * Minimum length for a decent password
	 */
	protected final int MAX_LENGTH = 12;

	/**
	 * The random number generator.
	 */
	private java.util.Random r = new java.util.Random();

	/**
	 * I, L and O are good to leave out as are numeric zero and one.
	 */
	private final String DIGITS = "23456789";
	private final String LOCASE_CHARACTERS = "abcdefghjkmnpqrstuvwxyz";
	private final String UPCASE_CHARACTERS = "ABCDEFGHJKMNPQRSTUVWXYZ";
	private final String SYMBOLS = "@#$%=:?";
	private final String ALL = DIGITS + LOCASE_CHARACTERS + UPCASE_CHARACTERS + SYMBOLS;
	private final char[] upcaseArray = UPCASE_CHARACTERS.toCharArray();
	private final char[] locaseArray = LOCASE_CHARACTERS.toCharArray();
	private final char[] digitsArray = DIGITS.toCharArray();
	private final char[] symbolsArray = SYMBOLS.toCharArray();
	private final char[] allArray = ALL.toCharArray();

	/**
	 * Generate a random password based on security rules
	 *
	 * - at least 8 characters, max of 12 - at least one uppercase - at least
	 * one lowercase - at least one number - at least one symbol
	 *
	 * @return
	 */
	public String generatePassword() {
		StringBuilder sb = new StringBuilder();

		// get at least one lowercase letter
		sb.append(locaseArray[r.nextInt(locaseArray.length)]);

		// get at least one uppercase letter
		sb.append(upcaseArray[r.nextInt(upcaseArray.length)]);

		// get at least one digit
		sb.append(digitsArray[r.nextInt(digitsArray.length)]);

		// get at least one symbol
		sb.append(symbolsArray[r.nextInt(symbolsArray.length)]);

		// fill in remaining with random letters
		for (int i = 0; i < MAX_LENGTH - 4; i++) {
			sb.append(allArray[r.nextInt(allArray.length)]);
		}

		return sb.toString();
	}
}
