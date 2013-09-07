package com.test

import akka.actor.{Props, ActorRef, Actor}

class BaseActor(sleepTime: Int, messageCount: Int, actorCount: Int) extends Actor {

  var stopped: Int = 0

  override def preStart() {
    val randomIds: List[Int] = ActorModel.generateRandomActorIds(messageCount, actorCount)
    val actors: List[ActorRef] = ActorModel.createActors(actorCount, Main.system, Props(classOf[WorkerActor], sleepTime))
    ActorModel.sendMessages(randomIds, actors)
    ActorModel.killActors(actors)
  }

  def receive = {
    case Done ⇒ {
      println("okdone")
      stopped = stopped + 1
    }
  }

  def getMemoryStats() = {


    println("To" + Runtime.getRuntime())
  }

}
