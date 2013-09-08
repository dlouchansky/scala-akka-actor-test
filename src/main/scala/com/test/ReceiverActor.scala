package com.test

import akka.actor._
import io.IOModule

class ReceiverActor(sleepTime: Long, parentActor: ActorRef, ioModule: IOModule) extends Actor {
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
      ioModule.outCounted(count)

    case _ =>
      ioModule.outError("Wrong action sent to WorkerActor")
  }
}
