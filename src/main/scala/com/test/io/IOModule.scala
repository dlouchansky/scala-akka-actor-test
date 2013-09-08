package com.test.io

trait IOModule {

  def outCounted(count: Int)

  def outError(error: String)

  def outStats(action: String, start: Long, end: Long)

  def outThroughput(messageCount: Int, start: Long, end: Long)

  def testTime(description: String, start: Long, finish: Long)

}
