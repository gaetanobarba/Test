package com.excercise3.grpc;

import java.util.concurrent.TimeUnit;

import com.excercise3.grpc.proto.Category;
import com.excercise3.grpc.proto.MessageCategoryId;
import com.excercise3.grpc.proto.MessageProductId;
import com.excercise3.grpc.proto.MessageRequest;
import com.excercise3.grpc.proto.MessageResponse;
import com.excercise3.grpc.proto.Product;
import com.excercise3.grpc.proto.ProductList;
import com.excercise3.grpc.proto.ProductServiceGrpc;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class MyGrpcClient {

	static ProductServiceGrpc.ProductServiceBlockingStub stub;

	public MyGrpcClient(Channel channel) {
		stub = ProductServiceGrpc.newBlockingStub(channel);
	}

	public void operaton(int op) {

		switch (op) {
		case 1:
			System.out.println("--------------------------");
			System.out.println("Start Add Products");

			Product product1 = Product.newBuilder().setProductId(1).setCategory(Category.CAT2).setPrice(5)
					.setQuantity(10).buildPartial();
			MessageResponse messageResponse = stub.addProduct(product1);
			System.out.println("Add Product: " + product1.getProductId() + " " + messageResponse);

			Product product2 = Product.newBuilder().setProductId(2).setCategory(Category.CAT3).setPrice(6)
					.setQuantity(-1).buildPartial();
			messageResponse = stub.addProduct(product2);
			System.out.println("Add Product: " + product2.getProductId() + " " + messageResponse);

			Product product3 = Product.newBuilder().setProductId(3).setCategory(Category.CAT2).setPrice(6)
					.setQuantity(1).buildPartial();
			messageResponse = stub.addProduct(product3);
			System.out.println("Add Product: " + product3.getProductId() + " " + messageResponse);

			Product product4 = Product.newBuilder().setProductId(4).setCategory(Category.CAT1).setPrice(6)
					.setQuantity(1).buildPartial();
			messageResponse = stub.addProduct(product4);
			System.out.println("Add Product: " + product4.getProductId() + " " + messageResponse);

			Product product5 = Product.newBuilder().setProductId(5).setCategory(Category.CAT3).setPrice(6)
					.setQuantity(1).buildPartial();
			messageResponse = stub.addProduct(product5);
			System.out.println("Add Product: " + product5.getProductId() + " " + messageResponse);

			System.out.println("End Add Products");
			System.out.println("--------------------------");

			break;
		case 2:
			System.out.println("--------------------------");
			System.out.println("Start getAll");
			MessageRequest messageRequest = MessageRequest.newBuilder().build();
			ProductList productList = stub.getAll(messageRequest);
			System.out.print(productList);
			System.out.println("End getAll");
			System.out.println("--------------------------");

			break;
		case 3:
			System.out.println("--------------------------");
			MessageProductId productId = MessageProductId.newBuilder().setProductId(1).build();
			Product productById = stub.getProductById(productId);
			System.out.println("Start getProductById: " + productId.getProductId());
			System.out.print(productById);
			System.out.println("End getProductById");
			System.out.println("--------------------------");
			break;
		case 4:
			System.out.println("--------------------------");
			MessageCategoryId categoryId = MessageCategoryId.newBuilder().setCategory(Category.CAT2).build();
			ProductList listProductByCatId = stub.getAllProductByCategory(categoryId);
			System.out.println("Start getAllProductByCategory: " + categoryId.getCategoryValue());
			System.out.print(listProductByCatId);
			System.out.println("End getAllProductByCategory");
			System.out.println("--------------------------");
			System.out.println("");
			break;
		case 5:
			System.out.println("--------------------------");
			System.out.println("Start listAllProductsGroupedByCategory");
			messageRequest = MessageRequest.newBuilder().build();
			ProductList listAllProductsGroupedByCategory = stub.getAllProductsGroupedByCategory(messageRequest);
			System.out.print(listAllProductsGroupedByCategory);
			System.out.println("End listAllProductsGroupedByCategory");
			System.out.println("--------------------------");
			break;
		case 6:
			Product productUpdate = Product.newBuilder().setProductId(4).setCategory(Category.CAT3).buildPartial();
			MessageResponse response = stub.updateProduct(productUpdate);
			System.out.println("--------------------------");
			System.out.println("Start updateProduct");
			System.out.print(response);
			System.out.println("End updateProduct");
			System.out.println("--------------------------");
			break;
		default:
			System.out.println("Invalide argument");
			break;
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();

		try {
			MyGrpcClient client = new MyGrpcClient(channel);
			if (args.length < 1)
				client.operaton(1);
			else
				client.operaton(Integer.valueOf(args[0]));
		} catch (Exception e) {
			System.out.println("Invalide argument");
		} finally {
			channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
		}
	}
}
