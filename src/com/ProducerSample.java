package com;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class ProducerSample {
	private static final String topic = "Message523";

	static void runProducer(final int sendMessageCount) throws Exception {
	      final Producer<Long, String> producer = createProducer();
	      long time = System.currentTimeMillis();

	      try {
	          for (long index = time; index < time + sendMessageCount; index++) {
	              final ProducerRecord<Long, String> record =
	                      new ProducerRecord<>(topic, index, "Hello " + index);

	              RecordMetadata metadata = producer.send(record).get();

	              long elapsedTime = System.currentTimeMillis() - time;
	              System.out.printf("sent record(key=%s value=%s) meta(partition=%d, offset=%d) time=%d\n",
	                      record.key(), record.value(), metadata.partition(),
	                      metadata.offset(), elapsedTime);

	          }
	      } finally {
	          producer.flush();
	          producer.close();
	      }
	  }

	private static Producer<Long, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        //props.put(ProducerConfig.CLIENT_ID_CONFIG, "test-producer-group");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }
}
