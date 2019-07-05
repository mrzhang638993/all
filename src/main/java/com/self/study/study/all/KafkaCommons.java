package com.self.study.study.all;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.*;

/**
 * Kafka公共服务
 */
public class KafkaCommons {

    // 生产者实例
    private static volatile Producer<String, String> producer;

    // 消费者实例
    private static volatile Consumer<String, String> consumer;

    // 私有构造函数
    private KafkaCommons() {
    }

    /**
     * 获取生产者实例，单例
     *
     * @return
     */
    private static Producer<String, String> getProducerInstance() {
        if (null == producer) {
            synchronized (KafkaCommons.class) {
                if (null == producer) {
                    Properties props = new Properties();
                    props.put("retries", 0);
                    props.put("acks", "all");
                    props.put("linger.ms", 1);
                    props.put("batch.size", 16384);
                    props.put("buffer.memory", 33554432);
                    props.put("bootstrap.servers", "localhost:9092");
                    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
                    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

                    // 实例化生产者
                    producer = new KafkaProducer<String, String>(props);
                }
            }
        }

        return producer;
    }

    /**
     * 获取消费者实例，单例
     *
     * @return
     */
    private static Consumer<String, String> getConsumerInstance(String topic) {
        if (null == consumer) {
            synchronized (KafkaCommons.class) {
                if (null == consumer) {
                    Properties props = new Properties();
                    props.put("group.id", "test");
                    props.put("enable.auto.commit", "true");
                    props.put("session.timeout.ms", "30000");
                    props.put("auto.commit.interval.ms", "1000");
                    props.put("bootstrap.servers", "localhost:9092");
                    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

                    consumer = new KafkaConsumer<>(props);
                    consumer.subscribe(Arrays.asList(topic));
                }
            }
        }

        return consumer;
    }

    /**
     * 向kakfa发送数据
     *
     * @param topic
     * @param msg
     */
    public static void sendMsgToKafka(String topic, String msg) {
        KafkaCommons.getProducerInstance().send(new ProducerRecord<String, String>(topic, String.valueOf(new Date().getTime()), msg));
    }

    /**
     * 从kafka获取数据
     *
     * @param topic
     */
    public static List<String> getMsgFromKafka(String topic) {
        List<String> list = new ArrayList<>();

        ConsumerRecords<String, String> records = KafkaCommons.getConsumerInstance(topic).poll(100);
        if (records.count() > 0) {
            for (ConsumerRecord<String, String> record : records) {
                list.add(record.value());
            }
        }

        return list;
    }

}
