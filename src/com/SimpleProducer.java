package com;

import java.util.Properties;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class SimpleProducer {
   
   public static void main(String[] args) throws Exception{
	   runProducer();
   }
   
   static void runProducer() throws InterruptedException {
      String topic = "Message523";
      Producer<String, String> producer = createProducer(topic); 
            
      for(int i = 10; i < 12; i++) {
          producer.send(new ProducerRecord<String, String>(topic, Integer.toString(i), "Message: " + i));
          System.out.println("Messages sent successfully");
      }
      producer.close();
   }
   
	private static Producer<String, String> createProducer(String topic) {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", StringSerializer.class.getName());
		props.put("value.serializer", StringSerializer.class.getName());

		final Producer<String, String> producer = new KafkaProducer<String, String>(props);
		return producer;

	}
}