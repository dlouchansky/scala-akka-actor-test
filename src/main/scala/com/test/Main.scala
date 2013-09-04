package com.test

import akka.actor.{Kill, ActorRef, Actor, Props}

object Main {

  case object Done;
}

class Main extends Actor {

  override def preStart() {
    killActors(createActors(5))
  }

  def receive = {
    case Main.Done ⇒ context.stop(self)
  }

  def createActors(n: Int): List[ActorRef] = {
    createActors(n, List())
  }

  def createActors(n: Int, store: List[ActorRef]): List[ActorRef] = {
    if (n == 0)
      List()
    else {
      val greeter = context.actorOf(Props[Worker])

      if (n == 1)
        greeter :: store
      else {
        createActors(n - 1, greeter :: store)
      }
    }
  }

  def killActors(store: List[ActorRef]) {
    store.foreach(actor => actor ! Kill)
  }

  def sendMessagesToOne(count: Int, actor: ActorRef) {
    if (count != 0) {
      actor ! Worker.Add
      sendMessagesToOne(count - 1, actor)
    }
  }

  def sendMessages(actorIds: List(Int), actors: List(ActorRef)) {
  }
}

