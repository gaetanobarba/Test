/**
 * 
 */
package com.excercise3.grpc;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;

import com.excercise3.grpc.MyGrpcServer.ProductServiceImpl;
import com.excercise3.grpc.proto.Category;
import com.excercise3.grpc.proto.MessageResponse;
import com.excercise3.grpc.proto.Product;
import com.excercise3.grpc.proto.ProductServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;

/**
 * @author teigaba
 *
 */
public class MyGrpcServerTest {
	private Server server;
	private ManagedChannel channel;
	/**
	 * This rule manages automatic graceful shutdown for the registered servers and
	 * channels at the end of test.
	 */
	@Rule
	public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

	/**
	 * To test the server, make calls with a real stub using the in-process channel,
	 * and verify behaviors or state changes from the client side.
	 */
	@Test
	public void greeterImpl_replyMessage() throws Exception {
		// Generate a unique in-process server name.
		String serverName = InProcessServerBuilder.generateName();

		// Create a server, add service, start, and register for automatic graceful
		// shutdown.
		grpcCleanup.register(InProcessServerBuilder.forName(serverName).directExecutor()
				.addService(new ProductServiceImpl()).build().start());

		ProductServiceGrpc.ProductServiceBlockingStub blockingStub = ProductServiceGrpc.newBlockingStub(
				// Create a client channel and register for automatic graceful shutdown.
				grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build()));

		Product product = Product.newBuilder().setCategory(Category.CAT1).build();
		MessageResponse reply = blockingStub.addProduct(product);

		assertEquals("OK", reply.getResponceCode().name());
	}

}
