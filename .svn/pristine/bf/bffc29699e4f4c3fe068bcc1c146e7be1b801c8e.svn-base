
package com.velotech.fanselection.utils;

public class NumberToWordConverter {

	public static String numberToWordConvert(final int n) {

		final String[] units = { "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen",
				"Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen" };

		final String[] tens = { "", // 0
				"", // 1
				"Twenty", // 2
				"Thirty", // 3
				"Forty", // 4
				"Fifty", // 5
				"Sixty", // 6
				"Seventy", // 7
				"Eighty", // 8
				"Ninety" // 9
		};

		if (n < 0) {
			return "Minus " + numberToWordConvert(-n);
		}

		if (n < 20) {
			return units[n];
		}

		if (n < 100) {
			return tens[n / 10] + ((n % 10 != 0) ? " " : "") + units[n % 10];
		}

		if (n < 1000) {
			return units[n / 100] + " Hundred" + ((n % 100 != 0) ? " " : "") + numberToWordConvert(n % 100);
		}

		if (n < 100000) {
			return numberToWordConvert(n / 1000) + " Thousand" + ((n % 10000 != 0) ? " " : "") + numberToWordConvert(n % 1000);
		}

		if (n < 10000000) {
			return numberToWordConvert(n / 100000) + " Lakh" + ((n % 100000 != 0) ? " " : "") + numberToWordConvert(n % 100000);
		}

		return numberToWordConvert(n / 10000000) + " Crore" + ((n % 10000000 != 0) ? " " : "") + numberToWordConvert(n % 10000000);
	}
}
