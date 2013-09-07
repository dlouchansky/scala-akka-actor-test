package com.test

import akka.actor._

class WorkerActor(sleepTime: Long, parentActor: ActorRef) extends Actor {
  var count: Int = 0

  def receive = {
    case Add =>
      if (sleepTime > 0)
        Thread.sleep(sleepTime)
      count += 1

    case Exit =>
      parentActor ! Done
      self ! PoisonPill

    case GetCount =>
      IOModule.outCounted(count)

    case _ =>
      IOModule.outError("Wrong action sent to WorkerActor")
  }
}
