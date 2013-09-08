package com.test

import akka.actor._
import io.ConsoleIOModule

/* WorkerActor message classes */
case class Add()
case class GetCount()
case class Exit()

/* BaseActor message classes */
case class Done()
case class OneTest(messageCount: Int, sleepTime: Long)
case class MultipleTest(actorCount: Int, messageCount: Int, sleepTime: Long)

object Main {
  val system = ActorSystem("mySystem")

  def main(args: Array[String]) {
    val actorCount: Int = args(0).toInt
    val messageCount: Int = args(1).toInt
    val timeout: Long = args(2).toLong

    val baseActor: ActorRef = system.actorOf(Props(classOf[BaseActor], new ConsoleIOModule))

    if (actorCount == 1) {
      baseActor ! OneTest(messageCount, timeout)
    } else if (actorCount > 1) {
      baseActor ! MultipleTest(actorCount, messageCount, timeout)
    }
  }
}


