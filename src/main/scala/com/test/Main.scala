package com.test

import akka.actor._
import io.{IOModule, ConsoleIOModule}

/* WorkerActor message classes */
case class Add()
case class GetCount()
case class Exit()

/* BaseActor message classes */
case class Done()
case class OneTest(messageCount: Int)
case class MultipleTest(actorCount: Int, messageCount: Int)

object Main {
  val system = ActorSystem("mySystem")

  def main(args: Array[String]) {
    val actorCount: Int = args(0).toInt
    val messageCount: Int = args(1).toInt
    val timeout: Long = args(2).toLong

    val io: IOModule = new ConsoleIOModule
    val childActorFactory: ChildActorFactory = new ReceiverActorFactory(timeout, io, system)
    val baseActor: ActorRef = system.actorOf(Props(classOf[SenderActor], io, childActorFactory))

    if (actorCount == 1) {
      baseActor ! OneTest(messageCount)
    } else if (actorCount > 1) {
      baseActor ! MultipleTest(actorCount, messageCount)
    }
  }
}


