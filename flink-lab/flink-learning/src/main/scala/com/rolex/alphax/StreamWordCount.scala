package com.rolex.alphax

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala._

/**
 * @author rolex
 * @since 2020
 */
object StreamWordCount {
  def main(args: Array[String]): Unit = {

    val param = ParameterTool.fromArgs(args)
    val host = param.get("host")
    val port = param.getInt("port")

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val socketTextStream = env.socketTextStream(host, port)

    val dataStream = socketTextStream.flatMap(_.split(" "))
      .map((_, 1))
      .keyBy(_._1)
      .sum(1)

    dataStream.print

    env.execute("stream word count")
  }
}
