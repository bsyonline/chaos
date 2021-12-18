package com.rolex.alphax

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer

/**
 * @author rolex
 * @since 2020
 */
object SourceTest {
  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val stream1: DataStream[Sensor] = env.fromCollection(List(
      Sensor(1, 1547756886, 37.5),
      Sensor(2, 1547756887, 37.5),
      Sensor(3, 1547756888, 37.5),
      Sensor(4, 1547756889, 37.5)
    ))
    stream1.print("stream1").setParallelism(3)

    val stream2: DataStream[_] = env.fromElements(1, "tom", 20L)
    stream2.print("stream2")

    val stream3: DataStream[String] = env.readTextFile("src/main/resources/sensor.txt")
    stream3.print("stream3")

    env.generateSequence(0, 10).iterate(
      it => {
        val minusOne = it.map(v => v - 1)
        val stillGreaterThanZero = minusOne.filter(_ > 0)
        val lessThanZero = minusOne.filter(_ <= 0)
        (stillGreaterThanZero, lessThanZero)
      }
    ).print("stream4")


    /**
     * kafka
     * .\kafka-topics.bat --create --zookeeper localhost:2181 --topic topic-1 --partitions 1 --replication-factor 1
     * .\kafka-console-producer.bat --broker-list localhost:9092 --topic topic-1
     */
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "localhost:9092")
    properties.setProperty("group.id", "test")
    val stream5 = env.addSource(new FlinkKafkaConsumer[String]("topic-1", new SimpleStringSchema(), properties))
    stream5.print("stream5")

    env.execute()
  }
}

case class Sensor(id: Int, timestamp: Long, temperature: Double)