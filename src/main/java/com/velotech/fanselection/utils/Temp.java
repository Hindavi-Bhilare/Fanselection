
package com.velotech.fanselection.utils;

import java.nio.file.Path;
import java.util.regex.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Temp {

	/**
     * Minimum length for a decent password
     */
    protected static final int MAX_LENGTH = 12;
 
    /**
     * The random number generator.
     */
    private static java.util.Random r = new java.util.Random();
 
    /**
     * I, L and O are good to leave out as are numeric zero and one.
     */
    private static final String DIGITS = "23456789";
    private static final String LOCASE_CHARACTERS = "abcdefghjkmnpqrstuvwxyz";
    private static final String UPCASE_CHARACTERS = "ABCDEFGHJKMNPQRSTUVWXYZ";
    private static final String SYMBOLS = "@#$%=:?";
    private static final String ALL = DIGITS + LOCASE_CHARACTERS + UPCASE_CHARACTERS + SYMBOLS;
    private static final char[] upcaseArray = UPCASE_CHARACTERS.toCharArray();
    private static final char[] locaseArray = LOCASE_CHARACTERS.toCharArray();
    private static final char[] digitsArray = DIGITS.toCharArray();
    private static final char[] symbolsArray = SYMBOLS.toCharArray();
    private static final char[] allArray = ALL.toCharArray();
	public static void main(String[] args) {

		// System.out.println(roundAvoid(1.7171, 2));
		//String myString = "a+b-c*d/e";
		//String[] result = myString.split("(?<=[-+*/])|(?=[-+*/])");
		//System.out.println(Arrays.toString(result));

		/*List<Double> a = new ArrayList<>();
		a.add(10d);
		a.add(20d);
		a.add(30d);

		System.out.println(a.indexOf(20d));

		String path = "/pumpselection_ksb\\userTemp\\2FA3D5630451633E7C4C6324897C3CFEVelotech\\OfferMasterReport.pdf";
		Path pathnew = Paths.get(path);
		System.out.println(pathnew.getFileName().toString());*/
		
		// setup
	    String regex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%=:\\?]).{8,12})";
	 
	    // execute
	    String thePassword = generatePassword();
	    
	    System.out.println(thePassword);
	}

	public static String generatePassword() {
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
	/*public static double roundAvoid(double value, int places) {

		double scale = Math.pow(10, places);
		return Math.round(value * scale) / scale;
	}
*/
}
