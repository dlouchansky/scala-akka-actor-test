package com.test

import akka.actor._

object Worker {
  case object Add
  case object GetCount
  case object Exit


  def createActors(n: Int, sysContext: ActorContext): List[ActorRef] = {
    createActors(n, List(), sysContext)
  }

  def createActors(n: Int, store: List[ActorRef], sysContext: ActorContext): List[ActorRef] = {
    if (n < 1)
      List()
    else {
      val greeter = sysContext.actorOf(Props[Worker])

      if (n == 1)
        greeter :: store
      else {
        createActors(n - 1, greeter :: store, sysContext)
      }
    }
  }

  def createActor(sysContext: ActorContext): ActorRef = {
    sysContext.actorOf(Props[Worker])
  }

  def killActors(store: List[ActorRef]) {
    store.foreach(actor => killActor(actor))
  }

  def killActor(actorId: ActorRef) {
    actorId ! Kill
  }

  def sendMessagesToOne(count: Int, actor: ActorRef) {
    if (count != 0) {
      actor ! Worker.Add
      sendMessagesToOne(count - 1, actor)
    }
  }

  def sendMessages(actorIds: List[Int], actors: List[ActorRef]) {
    actorIds.foreach(actorId => actors(actorId) ! Worker.Add)
  }
}

class Worker extends Actor {

  var count: Int = 0

  def receive = {
    case Worker.Add =>  //todo wait
      println("ok")
      count = count + 1
    case Worker.Exit =>
      sender ! Main.Done
    case Worker.GetCount =>
      println(count)
    case _ =>  println("ok")

  }
}