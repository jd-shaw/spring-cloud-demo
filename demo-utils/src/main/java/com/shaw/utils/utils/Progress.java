package com.shaw.utils.utils;

/**
 * @author shaw
 * @date 2017年8月16日
 */
public final class Progress {
	private int size;
	private int index = 0;

	private Progress(int size) {
		this.size = size;
	}

	public void init() {
		index = 0;
	}

	public int inrc() {
		synchronized (this) {
			return ++index;
		}
	}

	public boolean isComplete() {
		return get() == size;
	}

	public int get() {
		return index;
	}

	public int getSize() {
		return size;
	}

	@Deprecated
	public static Progress getInstance() {
		Progress progress = new Progress(0);
		progress.init();
		return progress;
	}

	public static Progress getInstance(int size) {
		Progress progress = new Progress(size);
		progress.init();
		return progress;
	}

}
