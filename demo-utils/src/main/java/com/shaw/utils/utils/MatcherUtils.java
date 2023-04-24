package com.shaw.utils.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author shaw
 *
 */
public class MatcherUtils {

	public static Matcher getMatcherByInsensitive(String str, String regx) {
		Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
		return pattern.matcher(str);
	}

	public static Matcher getMatcher(String str, String regx) {
		Pattern pattern = Pattern.compile(regx);
		return pattern.matcher(str);
	}

	public static boolean find(String str, String regx) {
		return Pattern.compile(regx).matcher(str).find();
	}

	public static boolean matches(String str, String regx) {
		return Pattern.compile(regx).matcher(str).matches();
	}

}
