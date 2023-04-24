package com.shaw.utils.utils;

import org.springframework.util.Assert;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLUtils {

	private static final Pattern NAMES_PATTERN = Pattern.compile("\\{([^/]+?)\\}");

	private static final String HTTP_PATTERN = "(?i)(http|https):";

	private static final String USERINFO_PATTERN = "([^@\\[/?#]*)";

	private static final String HOST_IPV4_PATTERN = "[^\\[/?#:]*";

	private static final String LAST_PATTERN = "(.*)";

	private static final String HOST_IPV6_PATTERN = "\\[[\\p{XDigit}\\:\\.]*[%\\p{Alnum}]*\\]";

	private static final String HOST_PATTERN = "(" + HOST_IPV6_PATTERN + "|" + HOST_IPV4_PATTERN + ")";

	private static final String PORT_PATTERN = "(\\d*(?:\\{[^/]+?\\})?)";

	private static final String PATH_PATTERN = "([^?#]*)";

	private static final String DEFAULT_VARIABLE_PATTERN = "(.*)";

	private static final Pattern HTTP_URL_PATTERN = Pattern.compile("^" + HTTP_PATTERN + "(//(" + USERINFO_PATTERN
			+ "@)?" + HOST_PATTERN + "(:" + PORT_PATTERN + ")?" + ")?" + PATH_PATTERN + "(\\?" + LAST_PATTERN + ")?");

	public static URI dealWith(String url, Object... uriVariableValues) {
		Parser parser = new Parser(url);
		List<String> variables = parser.getVariableNames();
		int i = 0;
		String result = url;
		for (String name : variables) {
			result = result.replace("{" + name + "}", uriVariableValues[i++] + "");
		}
		return URI.create(result);
	}

	private static class Parser {

		private final List<String> variableNames = new LinkedList<String>();

		private final StringBuilder patternBuilder = new StringBuilder();

		private Parser(String uriTemplate) {
			Assert.hasText(uriTemplate, "'uriTemplate' must not be null");
			Matcher matcher = NAMES_PATTERN.matcher(uriTemplate);
			int end = 0;
			while (matcher.find()) {
				this.patternBuilder.append(quote(uriTemplate, end, matcher.start()));
				String match = matcher.group(1);
				int colonIdx = match.indexOf(':');
				if (colonIdx == -1) {
					this.patternBuilder.append(DEFAULT_VARIABLE_PATTERN);
					this.variableNames.add(match);
				} else {
					if (colonIdx + 1 == match.length()) {
						throw new IllegalArgumentException(
								"No custom regular expression specified after ':' in \"" + match + "\"");
					}
					String variablePattern = match.substring(colonIdx + 1, match.length());
					this.patternBuilder.append('(');
					this.patternBuilder.append(variablePattern);
					this.patternBuilder.append(')');
					String variableName = match.substring(0, colonIdx);
					this.variableNames.add(variableName);
				}
				end = matcher.end();
			}
			this.patternBuilder.append(quote(uriTemplate, end, uriTemplate.length()));
			int lastIdx = this.patternBuilder.length() - 1;
			if (lastIdx >= 0 && this.patternBuilder.charAt(lastIdx) == '/') {
				this.patternBuilder.deleteCharAt(lastIdx);
			}
		}

		private String quote(String fullPath, int start, int end) {
			if (start == end) {
				return "";
			}
			return Pattern.quote(fullPath.substring(start, end));
		}

		private List<String> getVariableNames() {
			return Collections.unmodifiableList(this.variableNames);
		}

	}

	public static boolean verifyOf(String url) {
		return HTTP_URL_PATTERN.matcher(url).matches();
	}

	public static URL createURL(String url) throws MalformedURLException {
		return URI.create(url).toURL();
	}

	public static String appendParameter(String url, String paramName, Object paramValue) {
		if (url.indexOf("?") > -1) {
			if (paramValue != null) {
				return url + "&" + paramName + "=" + paramValue;
			}
		} else {
			if (paramValue != null) {
				return url + "?" + paramName + "=" + paramValue;
			}
		}
		return url;
	}

}
