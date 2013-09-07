package com.test

object IOModule {

  def outCounted(count: Int) {
    println(count)
  }

  def outError(error: String) {
    println(error)
  }

  def getMemoryStats() = {


    println("To" + Runtime.getRuntime())
  }

  /*
outStats(Action, Start, End) ->
  io:format("~p at ~p. Memory usage: total - ~p KB, processes - ~p KB.~n", [
Action, diff(Start, End), round(erlang:memory(total) / 1024), round(erlang:memory(processes) / 1024)
]).
*/

  def outStats(action: String, start: Long, end: Long) {
      println(action + " at " + timeDiff(start, end) + ". Memory usage: ")
  }

  def outThroughput(messageCount: Int, start: Long, end: Long) {
    println("Throughput = " + perSec(messageCount, start, end) + " messaged per second.")
  }

  def testTime(description: String, start: Long, finish: Long) {
      println(description + " took " + timeDiff(start, finish) + " seconds.")
  }

  def timeDiff(start: Long, finish: Long): Long =  {
       finish - start
  }

  def perSec(count: Int, start: Long, end: Long): Long = {
    count / timeDiff(start, end)
  }


}
