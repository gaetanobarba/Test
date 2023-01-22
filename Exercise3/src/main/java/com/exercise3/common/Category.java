/**
 * 
 */
package com.exercise3.common;

/**
 * @author teigaba
 *
 */
public enum Category {
	CAT1(1), CAT2(2), CAT3(3);

	private final Integer value;

	/**
	 * @param i
	 */
	Category(int v) {
		value = v;
	}

	public Integer value() {

		return value;
	}

	public static Category fromValue(Integer v) {

		for (final Category c : Category.values()) {
			if (c.value == v)
				return c;
		}

		throw new IllegalArgumentException(v.toString());
	}

}
