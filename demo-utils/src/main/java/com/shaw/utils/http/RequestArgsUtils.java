package com.shaw.utils.http;

import com.shaw.utils.utils.TypeUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 * @author wzj
 * @date 2015-4-10
 */
public abstract class RequestArgsUtils {

    public static String build(Map<String, Object> params) {
        int index = 0;
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, ?> entry : params.entrySet()) {
            if (index == 0) {
                sb.append(entry.getKey()).append("=").append(entry.getValue());
            } else {
                sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
            index++;
        }
        return sb.toString();
    }

    public static String build(String url, Map<String, ?> params) {
        int index = 0;
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, ?> entry : params.entrySet()) {
            if (index == 0) {
                sb.append(entry.getKey()).append("=").append(entry.getValue());
            } else {
                sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
            index++;
        }
        return url + "?" + sb.toString();
    }

    public static HttpEntity<String> buildFormUrlEncoded(String... strings) {
        StringBuilder body = new StringBuilder();
        int i = 0;
        while (i < strings.length) {
            body.append(strings[i++]).append("=").append(strings[i++]).append("&");
        }
        return create(body.toString(), new HttpHeaderContext() {

            @Override
            public MultiValueMap<String, String> init() {
                MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
                headers.set("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                return headers;
            }
        });
    }

    public static <T> HttpEntity<T> create(T object, MultiValueMap<String, String> headers) {
        return new HttpEntity<T>(object, headers);
    }

    public static <T> HttpEntity<T> create(T object) {
        return create(object, DEFAULT_HTTP_HEADER_CONTEXT);
    }

    public static <T> HttpEntity<T> create(T object, HttpHeaderContext httpHeaderContext) {
        return create(object, httpHeaderContext.init());
    }

    public static interface HttpHeaderContext {
        public abstract MultiValueMap<String, String> init();
    }

    public static final HttpHeaderContext DEFAULT_HTTP_HEADER_CONTEXT = new HttpHeaderContext() {

        @Override
        public MultiValueMap<String, String> init() {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
            headers.set("Content-Type", "application/json");
            return headers;
        }
    };

    public static <T> HttpEntity<T> buildParams(Map<String, ?> params) {
        return buildParams(params, null);
    }

    public static <T> HttpEntity<T> buildParams(String... params) {
        return buildParams(com.shaw.utils.utils.MapUtils.<String, Object>newMap((Object[]) params), null);
    }

    public static <T> HttpEntity<T> buildBody(T body) {
        return new HttpEntity<T>(body);
    }

    public static <T> HttpEntity<T> buildBody(T body, Map<String, ?> headers) {
        if (headers != null) {
            LinkedMultiValueMap<String, String> valueMap = new LinkedMultiValueMap<String, String>();
            valueMap.setAll(toStringMap(headers));
            return new HttpEntity<T>(body, valueMap);
        } else {
            LinkedMultiValueMap<String, String> valueMap = new LinkedMultiValueMap<String, String>();
            valueMap.setAll(com.shaw.utils.utils.MapUtils.<String, String>newMap("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8"));
            return new HttpEntity<T>(body, valueMap);
        }
    }

    public static <T> HttpEntity<T> buildHeaders(Map<String, ?> headers) {
        LinkedMultiValueMap<String, String> valueMap = new LinkedMultiValueMap<String, String>(headers.size());
        valueMap.setAll(toStringMap(headers));
        return new HttpEntity<T>(valueMap);
    }

    public static <T> HttpEntity<T> buildHeaders(String... headers) {
        LinkedMultiValueMap<String, String> valueMap = new LinkedMultiValueMap<String, String>(headers.length / 2);
        int i = 0;
        while (i < headers.length) {
            valueMap.set(headers[i++], headers[i++]);
        }
        return new HttpEntity<T>(valueMap);
    }

    @SuppressWarnings("unchecked")
    public static <T> HttpEntity<T> buildParams(Map<String, ?> params, Map<String, ?> headers) {
        LinkedMultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        if (MapUtils.isNotEmpty(params)) {
            for (Map.Entry<String, ?> param : params.entrySet()) {
                paramMap.add(param.getKey(), TypeUtils.toString(param.getValue()));
            }
        }
        return (HttpEntity<T>) buildBody(paramMap, headers);
    }

    @SuppressWarnings("unchecked")
    private static <T> Map<String, String> toStringMap(Map<String, T> map) {
        if (map != null) {
            Object obj = map;
            for (Map.Entry<String, T> entry : map.entrySet()) {
                entry.setValue((T) TypeUtils.toString(entry.getValue()));
            }
            return (Map<String, String>) obj;
        }
        return null;
    }

}
