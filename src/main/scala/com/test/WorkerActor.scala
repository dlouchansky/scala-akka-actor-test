package com.test

import akka.actor._

class WorkerActor(sleepTime: Int) extends Actor {
  var count: Int = 0

  def receive = {
    case Add =>
      if (sleepTime > 0)
        Thread.sleep(sleepTime)
      count += 1

    case Exit =>
      sender ! Done

    case GetCount =>
      IOModule.outCounted(count)

    case _ =>
      IOModule.outError("Wrong action sent to WorkerActor")
  }
}
