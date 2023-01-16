/*
 * Copyright 2016 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.excercise3.grpc;

import com.excercise3.grpc.proto.Category;
import com.excercise3.grpc.proto.MessageCategoryId;
import com.excercise3.grpc.proto.MessageProductId;
import com.excercise3.grpc.proto.MessageRequest;
import com.excercise3.grpc.proto.MessageResponse;
import com.excercise3.grpc.proto.Product;
import com.excercise3.grpc.proto.ProductList;
import com.excercise3.grpc.proto.ProductServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class MyGrpcClient {
	public static void main(String[] args) throws InterruptedException {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();

		ProductServiceGrpc.ProductServiceBlockingStub stub = ProductServiceGrpc.newBlockingStub(channel);

		Product product1 = Product.newBuilder().setProductId(1).setCategory(Category.CAT2).setPrice(5).setQuantity(10)
				.buildPartial();
		MessageResponse messageResponse = stub.addProduct(product1);
		System.out.println("Add Product: " + product1.getProductId() + " " + messageResponse);

		Product product2 = Product.newBuilder().setProductId(2).setCategory(Category.CAT3).setPrice(6).setQuantity(-1)
				.buildPartial();
		messageResponse = stub.addProduct(product2);
		System.out.println("Add Product: " + product2.getProductId() + " " + messageResponse);

		Product product3 = Product.newBuilder().setProductId(3).setCategory(Category.CAT2).setPrice(6).setQuantity(1)
				.buildPartial();
		messageResponse = stub.addProduct(product3);
		System.out.println("Add Product: " + product3.getProductId() + " " + messageResponse);

		Product product4 = Product.newBuilder().setProductId(4).setCategory(Category.CAT1).setPrice(6).setQuantity(1)
				.buildPartial();
		messageResponse = stub.addProduct(product4);
		System.out.println("Add Product: " + product4.getProductId() + " " + messageResponse);

		Product product5 = Product.newBuilder().setProductId(5).setCategory(Category.CAT3).setPrice(6).setQuantity(1)
				.buildPartial();
		messageResponse = stub.addProduct(product5);
		System.out.println("Add Product: " + product5.getProductId() + " " + messageResponse);

		MessageRequest messageRequest = MessageRequest.newBuilder().build();
		System.out.println("--------------------------");
		System.out.println("Start getAll");
		ProductList productList = stub.getAll(messageRequest);
		System.out.print(productList);
		System.out.println("End getAll");
		System.out.println("--------------------------");
		System.out.println("");

		MessageProductId productId = MessageProductId.newBuilder().setProductId(1).build();
		Product productById = stub.getProductById(productId);
		System.out.println("--------------------------");
		System.out.println("Start getProductById: " + productId.getProductId());
		System.out.print(productById);
		System.out.println("End getProductById");
		System.out.println("--------------------------");
		System.out.println("");

		MessageCategoryId categoryId = MessageCategoryId.newBuilder().setCategory(Category.CAT2).build();
		ProductList listProductByCatId = stub.getAllProductByCategory(categoryId);
		System.out.println("--------------------------");
		System.out.println("Start getAllProductByCategory: " + categoryId.getCategoryValue());
		System.out.print(listProductByCatId);
		System.out.println("End getAllProductByCategory");
		System.out.println("--------------------------");
		System.out.println("");

		ProductList listAllProductsGroupedByCategory = stub.getAllProductsGroupedByCategory(messageRequest);
		System.out.println("--------------------------");
		System.out.println("Start listAllProductsGroupedByCategory");
		System.out.print(listAllProductsGroupedByCategory);
		System.out.println("End listAllProductsGroupedByCategory");
		System.out.println("--------------------------");
		System.out.println("");

		Product productUpdate = Product.newBuilder().setProductId(4).setCategory(Category.CAT3).buildPartial();
		MessageResponse response = stub.updateProduct(productUpdate);
		System.out.println("--------------------------");
		System.out.println("Start updateProduct");
		System.out.print(response);
		System.out.println("End updateProduct");
		System.out.println("--------------------------");
		System.out.println("");

		channel.shutdown();
	}
}
