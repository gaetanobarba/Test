/**
 * 
 */
package com.exercise3.common;

import java.util.Date;

/**
 * @author teigaba
 *
 */
public class ProductAdapter implements ProductAdapterI<com.excercise3.grpc.proto.Product> {

	Product product;

	/**
	 * @param category
	 * @param quantity
	 * @param price
	 */
	public ProductAdapter(Product product) {
		super();
		this.product = product;
	}

	public ProductAdapter(com.excercise3.grpc.proto.Product protoProduct) {
		super();
		encode(protoProduct);
	}

	@Override
	public void encode(com.excercise3.grpc.proto.Product protoProduct) {
		product = new Product(protoProduct.getProductId(), protoProduct.getDescription(),
				Category.valueOf(protoProduct.getCategory().toString()), protoProduct.getQuantity(),
				protoProduct.getPrice(), new Date(protoProduct.getUpdatedAt()));

	}

	@Override
	public com.excercise3.grpc.proto.Product decode() {
		com.excercise3.grpc.proto.Product protoProduct = com.excercise3.grpc.proto.Product.newBuilder()
				.setProductId(product.getProductId()).setDescription(product.getDescription())
				.setCategory(com.excercise3.grpc.proto.Category.forNumber(product.getCategory().value()))
				.setQuantity(product.getQuantity()).setPrice(product.getPrice())
				.setUpdatedAt(product.getDate().getTime()).build();

		return protoProduct;
	}

}
