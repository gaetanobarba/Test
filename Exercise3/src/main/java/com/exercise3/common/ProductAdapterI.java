/**
 * 
 */
package com.exercise3.common;

/**
 * @author teigaba
 * @param <K>
 * @param <P>
 *
 */
public interface ProductAdapterI<V> {

	void encode(V product);

	V decode();

}
