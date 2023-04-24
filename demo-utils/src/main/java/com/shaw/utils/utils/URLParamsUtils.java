package com.shaw.utils.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class URLParamsUtils {

	public static class URLParamsException extends Exception {
		private static final long serialVersionUID = 1L;
		private int code;

		public int getCode() {
			return code;
		}

		public URLParamsException(int aCode, String msg) {
			super(msg);
			code = aCode;
		}
	}

	public static Map<String, String> toMap(String params) throws URLParamsException, UnsupportedEncodingException {
		if (params == null || params.length() == 0)
			return null;
		params = URLDecoder.decode(params, StandardCharsets.UTF_8.name());
		String[] ps = params.split("&");
		Map<String, String> result = new HashMap<String, String>();
		for (String s : ps) {
			String[] kv = s.split("=");
			if (kv.length != 2)
				throw new URLParamsException(1, "URL params pattern error!");
			if (kv[0].length() == 0 || kv[1].length() == 0)
				throw new URLParamsException(1, "URL params pattern error!");
			result.put(kv[0], kv[1]);
		}
		return result;
	}
}
