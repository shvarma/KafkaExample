package com;

import java.util.Properties;
import java.time.Duration;
import java.util.Arrays;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class SimpleConsumer {
   public static void main(String[] args) throws Exception {
	   runConsumer();
   }
   
   static void runConsumer() throws InterruptedException {
	      String topic = "Message523";
	      Consumer<String, String> consumer = createConsumer(topic); 
	      final int giveUp = 100;   int noRecordsCount = 0;
	      while (true) {
			final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
			if (records.count()==0) {
                noRecordsCount++;
                if (noRecordsCount > giveUp) break;
                else continue;
            }
	        for (ConsumerRecord<String, String> record : records)
	        	System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
	      }
	      consumer.close();
   }
   
	private static Consumer<String, String> createConsumer(String topic) {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("group.id", "test-consumer-group");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", StringDeserializer.class.getName());
		props.put("value.deserializer", StringDeserializer.class.getName());

		final Consumer<String, String> consumer = new KafkaConsumer<>(props);

		consumer.subscribe(Arrays.asList(topic));
		System.out.println("Subscribed to topic " + topic);

		return consumer;
   }
}