package edu.cs408.vormund;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Validator {

	// Useful for bank account/routing numbers that do not conform to a certain standard
	public static boolean numbersOnly(String val, boolean includeZeros) {
		if (includeZeros)
			return doesMatch("^[0-9]+$", val);
		else
			return doesMatch("^[1-9]+$", val);
	}

	// Valid SSN can include hyphens (-) or not
	public static boolean validSSN(String ssn) {
		return doesMatch("^[0-9]{3}-?[0-9]{2}-?[0-9]{4}$", ssn);
	}

	// Useful for not having to write that conditional statement everytime
	public static boolean isNullOrEmpty(String str) {
		return (str == null || str.length() == 0);
	}

	// Should you need to test a custom regex. Also used in this class.
	public static boolean doesMatch(String needle, String haystack) {
		Pattern r = Pattern.compile(needle);
		Matcher m = r.matcher(haystack);

		return m.find();
	}
	
}