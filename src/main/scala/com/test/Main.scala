package com.test

import akka.actor.{Kill, ActorRef, Actor, Props}

object Main {

  case object Done
}

class Main extends Actor {

  var stopped: Int = 0

  override def preStart() {
    Worker.killActors(Worker.createActors(5, context))
  }

  def receive = {
    case Main.Done ⇒ {
      stopped = stopped + 1
    }
  }

}

