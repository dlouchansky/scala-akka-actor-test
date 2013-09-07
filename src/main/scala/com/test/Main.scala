package com.test

import akka.actor._

/* WorkerActor message classes */
case class Add()
case class GetCount()
case class Exit()

/* BaseActor message classes */
case class Done()
case class OneTest(messageCount: Int, sleepTime: Int)
case class MultipleTest(actorCount: Int, messageCount: Int, sleepTime: Int)

object Main {
  val system = ActorSystem("mySystem")

  def main(args: Array[String]) {
    val actorCount: Int = args(0).toInt
    val messageCount: Int = args(1).toInt
    val timeout: Int = args(2).toInt

    val baseActor: ActorRef = system.actorOf(Props(classOf[BaseActor]))

    if (actorCount == 1) {
      baseActor ! OneTest(messageCount, timeout)
    } else if (actorCount > 1) {
      baseActor ! MultipleTest(actorCount, messageCount, timeout)
    }
  }
}


