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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.excercise3.grpc.proto.Category;
import com.excercise3.grpc.proto.MessageCategoryId;
import com.excercise3.grpc.proto.MessageProductId;
import com.excercise3.grpc.proto.MessageRequest;
import com.excercise3.grpc.proto.MessageResponse;
import com.excercise3.grpc.proto.Product;
import com.excercise3.grpc.proto.ProductList;
import com.excercise3.grpc.proto.ProductServiceGrpc.ProductServiceImplBase;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

/**
 * Created by rayt on 5/16/16.
 */
public class MyGrpcServer {

	private static Map<Integer, Product> productMap = new HashMap<Integer, Product>();

	static public void main(String[] args) throws IOException, InterruptedException {
		Server server = ServerBuilder.forPort(8080).addService(new ProductServiceImpl()).build();

		System.out.println("Starting server...");
		server.start();
		System.out.println("Server started!");
		server.awaitTermination();
	}

	public static class ProductServiceImpl extends ProductServiceImplBase {

		@Override
		public void addProduct(Product request, StreamObserver<MessageResponse> responseObserver) {
			System.out.println(request);

			MessageResponse response = MessageResponse.newBuilder().setErrCode("OK").build();

			if (!productMap.containsKey(request.getProductId()) && request.getPrice() >= 0
					&& request.getQuantity() >= 0) {
				request.toBuilder().setUpdatedAt(System.currentTimeMillis());
				productMap.put(request.getProductId(), request.newBuilder(request).build());
			} else
				response = MessageResponse.newBuilder()
						.setErrCode("ProductId already present or Price and/or Quantity <0").build();

			responseObserver.onNext(response);
			responseObserver.onCompleted();
		}

		@Override
		public void getAll(MessageRequest request, StreamObserver<ProductList> responseObserver) {

			List<Product> productList = productMap.values().stream().collect(Collectors.toList());

			ProductList response = ProductList.newBuilder().addAllProductList(productList).build();
			responseObserver.onNext(response);
			responseObserver.onCompleted();

		}

		@Override
		public void getProductById(MessageProductId request, StreamObserver<Product> responseObserver) {

			Product response = productMap.containsKey(request.getProductId()) ? productMap.get(request.getProductId())
					: Product.newBuilder().buildPartial();
			responseObserver.onNext(response);
			responseObserver.onCompleted();
		}

		@Override
		public void getAllProductByCategory(MessageCategoryId request, StreamObserver<ProductList> responseObserver) {

			Category category = request.getCategory();
			List<Product> listAllProductByCategory = productMap.values().stream()
					.filter(c -> c.getCategory().equals(category)).collect(Collectors.toList());

			ProductList response = ProductList.newBuilder().addAllProductList(listAllProductByCategory).build();
			responseObserver.onNext(response);
			responseObserver.onCompleted();
		}

		@Override
		public void getAllProductsGroupedByCategory(MessageRequest request,
				StreamObserver<ProductList> responseObserver) {

			List<Product> listAllProductsGroupedByCategory = productMap.values().stream()
					.sorted(Comparator.comparingInt(Product::getCategoryValue)).collect(Collectors.toCollection(ArrayList::new));

			ProductList response = ProductList.newBuilder().addAllProductList(listAllProductsGroupedByCategory).build();
			responseObserver.onNext(response);
			responseObserver.onCompleted();
		}

		@Override
		public void updateProduct(Product request, StreamObserver<MessageResponse> responseObserver) {
			MessageResponse response = MessageResponse.newBuilder().setErrCode("OK").build();
			
			if (productMap.containsKey(request.getProductId())) {
				request.toBuilder().setUpdatedAt(System.currentTimeMillis());
				productMap.put(request.getProductId(), request.newBuilder(request).build());
			} else
				response = MessageResponse.newBuilder()
						.setErrCode("ProductId: " + request.getProductId() + " not present!").build();

			responseObserver.onNext(response);
			responseObserver.onCompleted();
		}

	}
}
