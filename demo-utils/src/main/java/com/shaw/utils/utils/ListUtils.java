package com.shaw.utils.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * @author wzj
 * @date 2015-4-1
 */
public abstract class ListUtils {

	protected final static Logger logger = LoggerFactory.getLogger(ListUtils.class);

	public static <T> List<T> subList(List<T> list, int fromIndex, int toIndex) {
		int size = 0;
		if (list == null || (size = list.size()) == 0)
			return list;
		if (fromIndex < 0)
			fromIndex = 0;
		if (toIndex > size)
			toIndex = size;
		if (fromIndex > toIndex)
			fromIndex = toIndex = 0;
		return list.subList(fromIndex, toIndex);
	}

	public static <T> List<List<T>> subList(List<T> listObj, int groupNum) {
		List<List<T>> resultList = new ArrayList<List<T>>();
		if (groupNum <= 0) {
			resultList.add(listObj);
		} else {
			int loopCount = (listObj.size() % groupNum == 0) ? (listObj.size() / groupNum)
					: ((listObj.size() / groupNum) + 1);
			for (int i = 0; i < loopCount; i++) {
				int startNum = i * groupNum;
				int endNum = (i + 1) * groupNum;
				if (i == loopCount - 1) {
					endNum = listObj.size();
				}
				List<T> listObjSub = new ArrayList<T>(listObj.subList(startNum, endNum));
				resultList.add(listObjSub);
			}
		}
		return resultList;
	}

	public static <T> List<T> removeNULL(List<T> list) {
		if (list != null) {
			synchronized (list) {
				for (Iterator<T> it = list.iterator(); it.hasNext();) {
					if (it.next() == null) {
						it.remove();
					}
				}
			}
		}
		return list;
	}

	public static <T> List<T> arrayToList(T[] array) {
		if (array != null) {
			List<T> list = new ArrayList<T>(array.length);
			for (T value : array) {
				list.add(value);
			}
			return list;
		}
		return null;
	}

	public static interface GroupBy<T> {
		public T groupby(Object obj);
	}

	public static final <T, D> Map<T, List<D>> group(Collection<D> colls, GroupBy<T> gb) {
		if (gb == null)
			throw new IllegalArgumentException();

		if (colls == null || colls.isEmpty())
			return null;

		Iterator<D> iter = colls.iterator();
		Map<T, List<D>> map = new HashMap<T, List<D>>();
		while (iter.hasNext()) {
			D d = iter.next();
			T t = gb.groupby(d);
			if (map.containsKey(t)) {
				map.get(t).add(d);
			} else {
				List<D> list = new ArrayList<D>();
				list.add(d);
				map.put(t, list);
			}
		}
		return map;
	}

	public static <T extends Comparable<? super T>> void sort(List<T> list) {
		try {
			Collections.sort(list);
		} catch (IllegalArgumentException e) {
			insertSort(list);
		}
	}

	public static <T> void sort(List<T> list, Comparator<? super T> c) {
		try {
			Collections.sort(list, c);
		} catch (IllegalArgumentException e) {
			insertSort(list, c);
		}
	}

	/**
	 * @param targetList
	 *            要排序的实体类List集合
	 *
	 * @param sortField
	 *            排序字段(实体类属性名)
	 *
	 * @param sortMode
	 *            true正序，false倒序
	 */
	@SuppressWarnings("all")
	public static <T> void sort(List<T> targetList, final String sortField, final boolean sortMode) {
		if (targetList == null || targetList.size() < 2 || sortField == null || sortField.length() == 0) {
			return;
		}
		Collections.sort(targetList, new Comparator() {
			@Override
			public int compare(Object obj1, Object obj2) {
				int retVal = 0;
				try {
					// 获取getXxx()方法名称
					String methodStr = "get" + sortField.substring(0, 1).toUpperCase() + sortField.substring(1);
					Method method1 = ((T) obj1).getClass().getMethod(methodStr, null);
					Method method2 = ((T) obj2).getClass().getMethod(methodStr, null);
					if (sortMode) {
						retVal = method1.invoke(((T) obj1), null).toString()
								.compareTo(method2.invoke(((T) obj2), null).toString());
					} else {
						retVal = method2.invoke(((T) obj2), null).toString()
								.compareTo(method1.invoke(((T) obj1), null).toString());
					}
				} catch (Exception e) {
					logger.error("List<" + ((T) obj1).getClass().getName() + ">排序异常！", e);
				}
				return retVal;
			}
		});
	}

	@SuppressWarnings("unchecked")
	public static <T> void insertSort(List<T> list, Comparator<? super T> c) {
		Object[] array = list.toArray();
		int n = array.length;
		int j;
		for (int i = 1; i < n; i++) { // 将a[i]插入a[0:i-1]
			Object t = array[i];
			for (j = i - 1; j >= 0 && c.compare((T) t, (T) array[j]) < 0; j--) {
				array[j + 1] = array[j];
			}
			array[j + 1] = t;
		}

		ListIterator<T> iterator = list.listIterator();
		for (j = 0; j < array.length; j++) {
			iterator.next();
			iterator.set((T) array[j]);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends Comparable<? super T>> void insertSort(List<T> list) {
		Object[] array = list.toArray();
		int n = array.length;
		int j;
		for (int i = 1; i < n; i++) { // 将a[i]插入a[0:i-1]
			Comparable<T> t = (Comparable<T>) array[i];
			for (j = i - 1; j >= 0 && t.compareTo((T) array[j]) < 0; j--) {
				array[j + 1] = array[j];
			}
			array[j + 1] = t;
		}

		ListIterator<T> iterator = list.listIterator();
		for (j = 0; j < array.length; j++) {
			iterator.next();
			iterator.set((T) array[j]);
		}
	}

	public static void main(String[] args) {
		List<String> ss = new ArrayList<String>();
		ss.add("12");
		ss.add("32");
		ss.add("23");
		ss.add("aa");
		ss.add("41");
		ss.add(null);
		ss.add("51");
		ss.add("61");
		ss.add(null);

		ListUtils.sort(ss, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				if (s1 != null && s2 != null) {
					return s1.compareTo(s2);
				} else if (s1 == null) {
					return 1;
				} else if (s2 == null) {
					return -1;
				}
				return 0;
			}
		});
		System.out.println(ss);

	}
}
