package rabbitmq.hello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*
 * Consumer
 */
public class Send {

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] args) throws IOException, TimeoutException {
		
		ConnectionFactory factory = new ConnectionFactory();
		String message = "Hello World!";

		factory.setHost("127.0.0.1");
		factory.setPort(5673);
		factory.setUsername("admin");
		factory.setPassword("admin");
		System.out.println(factory.getHost());
		System.out.println(factory.getPort());
		System.out.println(factory.getUsername());
		System.out.println(factory.getPassword());
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");
		
		channel.close();
		connection.close();
	}
}