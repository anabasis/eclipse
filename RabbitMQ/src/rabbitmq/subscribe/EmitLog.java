package rabbitmq.subscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) {
        
    	ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("127.0.0.1");
		factory.setPort(5673);
		factory.setUsername("admin");
		factory.setPassword("admin");
		System.out.println(factory.getHost());
		System.out.println(factory.getPort());
		System.out.println(factory.getUsername());
		System.out.println(factory.getPassword());
        
        try {
        	
        	Connection connection = factory.newConnection();
    		Channel channel = connection.createChannel();
    		
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            String message = argv.length < 1 ? "info: Hello World!" :
                    String.join(" ", argv);

            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }catch(Exception e) {
        	
        }
    }

}