package com.rolex.alphax

import org.apache.flink.api.scala._

/**
 * @author rolex
 * @since 2020
 */
object BatchWordCount {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment

    val text = env.readTextFile("src\\main\\resources\\wordcount.txt")

    val value = text.flatMap(_.split(" "))
      .map((_, 1))
      .groupBy(0)
      .sum(1)

    value.print()
  }
}
