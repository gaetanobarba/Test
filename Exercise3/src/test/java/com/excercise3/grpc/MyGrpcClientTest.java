/**
 * 
 */
package com.excercise3.grpc;

import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalAnswers.delegatesTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;

import com.excercise3.grpc.proto.MessageResponse;
import com.excercise3.grpc.proto.Product;
import com.excercise3.grpc.proto.ProductServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.testing.GrpcCleanupRule;

/**
 * @author teigaba
 *
 */
public class MyGrpcClientTest {

	/**
	 * This rule manages automatic graceful shutdown for the registered servers and
	 * channels at the end of test.
	 */
	@Rule
	public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

	private final ProductServiceGrpc.ProductServiceImplBase serviceImpl = mock(
			ProductServiceGrpc.ProductServiceImplBase.class,
			delegatesTo(new ProductServiceGrpc.ProductServiceImplBase() {
				// By default the client will receive Status.UNIMPLEMENTED for all RPCs.
				// You might need to implement necessary behaviors for your test here, like
				// this:
				//
				// @Override
				// public void sayHello(HelloRequest request, StreamObserver<HelloReply>
				// respObserver) {
				// respObserver.onNext(HelloReply.getDefaultInstance());
				// respObserver.onCompleted();
				// }
			}));

	private MyGrpcClient client;

	@Before
	public void setUp() throws Exception {
		// Generate a unique in-process server name.
		String serverName = InProcessServerBuilder.generateName();

		// Create a server, add service, start, and register for automatic graceful
		// shutdown.
		grpcCleanup.register(
				InProcessServerBuilder.forName(serverName).directExecutor().addService(serviceImpl).build().start());

		// Create a client channel and register for automatic graceful shutdown.
		ManagedChannel channel = grpcCleanup
				.register(InProcessChannelBuilder.forName(serverName).directExecutor().build());

		// Create a HelloWorldClient using the in-process channel;
		client = new MyGrpcClient(channel);
	}

	/**
	 * To test the client, call from the client against the fake server, and verify
	 * behaviors or state changes from the server side.
	 */
	@Test
	public void greet_messageDeliveredToServer() {
		ArgumentCaptor<Product> requestCaptor = ArgumentCaptor.forClass(Product.class);

		// client.greet("test name");

		verify(serviceImpl).addProduct(requestCaptor.capture(),
				ArgumentMatchers.<StreamObserver<MessageResponse>>any());

		// verify(serviceImpl).sayHello(requestCaptor.capture(),
		// ArgumentMatchers.<StreamObserver<HelloReply>>any());
		assertEquals("OK", requestCaptor.getValue().getCategoryValue());
	}
}
