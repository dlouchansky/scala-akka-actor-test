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

  def outStats(action: String, start: Long, end: Long) {
      println(action + " at " + timeDiff(start, end) + ". Memory usage: ")
  }

  def outThroughput(messageCount: Int, start: Long, end: Long) {

  }

  def testTime(description: String, start: Long, finish: Long) {
      println(description + " took " + timeDiff(start, finish) + " seconds.")
  }

  def timeDiff(start: Long, finish: Long): Long =  {
       finish - start
  }

  /*
  outStats(Action, Start, End) ->
    io:format("~p at ~p. Memory usage: total - ~p KB, processes - ~p KB.~n", [
  Action, diff(Start, End), round(erlang:memory(total) / 1024), round(erlang:memory(processes) / 1024)
  ]).

  outThroughput(Messagecnt, Start, Sent) ->
    io:format("Throughput = ~p messages per second.~n", [
  helpers:perSec(Messagecnt, Start, Sent)
  ]).

  testTime(Desc, Start, Finish) ->
    io:format("~p took ~p seconds.~n", [
  Desc, helpers:diff(Start, Finish)
  ]).*/
}
