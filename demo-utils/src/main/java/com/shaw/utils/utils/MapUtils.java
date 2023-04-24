package com.shaw.utils.utils;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapUtils {

	@SuppressWarnings("rawtypes")
	public static <T> T get(Map map, Object key) {
		return get(map, key, null);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T get(Map map, Object key, T def) {
		if (map.containsKey(key)) {
			if (def == null)
				return (T) map.get(key);
			if (map.get(key).getClass().isAssignableFrom(def.getClass()))
				return (T) map.get(key);
		}
		return def;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <K, V> HashMap<K, V> newMap(Object... args) {
		HashMap map = new HashMap(args.length / 2);
		int i = 0;
		while (i < args.length) {
			map.put(args[i++], args[i++]);
		}
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Object... args) {
		LinkedHashMap map = new LinkedHashMap();
		int i = 0;
		while (i < args.length) {
			map.put(args[i++], args[i++]);
		}
		return map;
	}

	public static Map<String, Object> listToMap(String keyName, String valueName, List<?> list) {
		if (list == null || list.size() == 0)
			return null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			for (Object object : list) {
				map.put(String.valueOf(ReflectionUtils.invokeGetterMethod(object, keyName)),
						ReflectionUtils.invokeGetterMethod(object, valueName));
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, Object> objectToMap(String keyName, String valueName, Object object) {
		if (object == null)
			return null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(String.valueOf(ReflectionUtils.invokeGetterMethod(object, keyName)),
				ReflectionUtils.invokeGetterMethod(object, valueName));
		return map;
	}

	public static MultiObject createMultiObject(Object... objs) {
		return new MultiObject(objs);
	}

	public static class MultiObject {
		private Object[] objs;

		public MultiObject(Object... objs) {
			this.objs = objs;
		}

		public Object[] getObjs() {
			return objs;
		}

		@SuppressWarnings("unchecked")
		public <T> T getValue(int i) {
			return (T) objs[i];
		}

		public void setValue(int i, Object value) {
			objs[i] = value;
		}

		public void setObjs(Object[] objs) {
			this.objs = objs;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(objs);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			MultiObject other = (MultiObject) obj;
			if (!Arrays.equals(objs, other.objs))
				return false;
			return true;
		}
	}

	public static final class CaseInsensitiveMap<K, V> extends AbstractMap<K, V> {

		@Override
		public Set<Entry<K, V>> entrySet() {
			return null;
		}

	}

}
