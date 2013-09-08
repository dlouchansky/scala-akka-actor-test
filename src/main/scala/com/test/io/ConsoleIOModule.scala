package com.test.io

class ConsoleIOModule extends IOModule {
  val currentRuntime: Runtime = Runtime.getRuntime()

  def outCounted(count: Int) {
    println(count)
  }

  def outError(error: String) {
    println(error)
  }

  def calculateMemory(): Long = {
    (currentRuntime.totalMemory() - currentRuntime.freeMemory()) / 1024
  }

  def outStats(action: String, start: Long, end: Long) {
      println(action + " at " + timeDiff(start, end) + ". Memory usage: " + calculateMemory().toString + " KB.")
  }

  def outThroughput(messageCount: Int, start: Long, end: Long) {
    println("Throughput = " + perSec(messageCount, start, end) + " messages per second.")
  }

  def testTime(description: String, start: Long, finish: Long) {
      println(description + " took " + timeDiff(start, finish) + " seconds.")
  }

  def timeDiff(start: Long, finish: Long): Float =  {
       (finish - start).toFloat / 1000000000f
  }

  def perSec(count: Int, start: Long, end: Long): Float = {
    count.toFloat / timeDiff(start, end)
  }

}
