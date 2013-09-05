package com.test

import akka.actor.{Kill, ActorRef, Actor, Props}
import util.Random

object Main {
  case object Done
}

class Main extends Actor {

  var stopped: Int = 0

  override def preStart() {
    val randomIds: List[Int] = generateRandomActorIds(5, 5)
    val actors: List[ActorRef] = Worker.createActors(5, context)
    Worker.sendMessages(randomIds, actors)
    Worker.killActors(actors)
  }

  def receive = {
    case Main.Done ⇒ {
      stopped = stopped + 1
    }
  }

  def generateRandomActorIds(resultCount: Int, actorCount: Int): List[Int] = {
    List.fill(resultCount)(Random.nextInt(actorCount))
  }

}

