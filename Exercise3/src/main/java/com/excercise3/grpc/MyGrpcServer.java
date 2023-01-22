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
import com.excercise3.grpc.proto.ResponseCode;
import com.exercise3.common.ProductAdapter;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

/**
 * Created by rayt on 5/16/16.
 */
public class MyGrpcServer {

	private static Map<Integer, ProductAdapter> productMap = new HashMap<Integer, ProductAdapter>();

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

			MessageResponse response = MessageResponse.newBuilder().setResponceCode(ResponseCode.OK)
					.setDescription("OK").build();

			if (!productMap.containsKey(request.getProductId()) && request.getPrice() >= 0
					&& request.getQuantity() >= 0) {
				request.toBuilder().setUpdatedAt(System.currentTimeMillis());
				productMap.put(request.getProductId(), new ProductAdapter(request));
			} else
				response = MessageResponse.newBuilder().setResponceCode(ResponseCode.OK)
						.setDescription("ProductId already present or Price and/or Quantity <0").build();

			responseObserver.onNext(response);
			responseObserver.onCompleted();
		}

		@Override
		public void getAll(MessageRequest request, StreamObserver<ProductList> responseObserver) {

			List<Product> productList = productMap.values().stream().map(p -> p.decode()).collect(Collectors.toList());

			ProductList response = ProductList.newBuilder().addAllProductList(productList).build();
			responseObserver.onNext(response);
			responseObserver.onCompleted();

		}

		@Override
		public void getProductById(MessageProductId request, StreamObserver<Product> responseObserver) {

			Product response = productMap.containsKey(request.getProductId())
					? productMap.get(request.getProductId()).decode()
					: Product.newBuilder().buildPartial();
			responseObserver.onNext(response);
			responseObserver.onCompleted();
		}

		@Override
		public void getAllProductByCategory(MessageCategoryId request, StreamObserver<ProductList> responseObserver) {

			Category category = request.getCategory();
			List<Product> listAllProductByCategory = productMap.values().stream().map(p -> p.decode())
					.filter(c -> c.getCategory().equals(category)).collect(Collectors.toList());

			ProductList response = ProductList.newBuilder().addAllProductList(listAllProductByCategory).build();
			responseObserver.onNext(response);
			responseObserver.onCompleted();
		}

		@Override
		public void getAllProductsGroupedByCategory(MessageRequest request,
				StreamObserver<ProductList> responseObserver) {

			List<Product> listAllProductsGroupedByCategory = productMap.values().stream().map(p -> p.decode())
					.sorted(Comparator.comparingInt(Product::getCategoryValue))
					.collect(Collectors.toCollection(ArrayList::new));

			ProductList response = ProductList.newBuilder().addAllProductList(listAllProductsGroupedByCategory).build();
			responseObserver.onNext(response);
			responseObserver.onCompleted();
		}

		@Override
		public void updateProduct(Product request, StreamObserver<MessageResponse> responseObserver) {
			MessageResponse response = MessageResponse.newBuilder().setResponceCode(ResponseCode.OK)
					.setDescription("OK").build();

			if (productMap.containsKey(request.getProductId())) {
				request.toBuilder().setUpdatedAt(System.currentTimeMillis());
				productMap.put(request.getProductId(), new ProductAdapter(request));
			} else
				response = MessageResponse.newBuilder().setResponceCode(ResponseCode.OK)
						.setDescription("ProductId: " + request.getProductId() + " not present!").build();

			responseObserver.onNext(response);
			responseObserver.onCompleted();
		}

	}
}
