package com.test

import akka.actor._
import util.Random

trait ChildActorService {

  def newActorProp(parentActor: ActorRef): Props

  def sendMessage(actorId: ActorRef)

  def killActor(actorId: ActorRef)

  def actorSystem(): ActorSystem

  def createActor(parentActor: ActorRef): ActorRef = {
    actorSystem().actorOf(newActorProp(parentActor))
  }

  def killActors(store: List[ActorRef]) {
    store.foreach(actor => killActor(actor))
  }

  def createActors(n: Int, parentActor: ActorRef): List[ActorRef] = {
    createActors(n, List(), parentActor)
  }

  def createActors(n: Int, store: List[ActorRef], parentActor: ActorRef): List[ActorRef] = {
    if (n < 1)
      List()
    else {
      val greeter = createActor(parentActor)

      if (n == 1)
        greeter :: store
      else {
        createActors(n - 1, greeter :: store, parentActor)
      }
    }
  }

  def sendMessagesToOne(count: Int, actor: ActorRef) {
    if (count != 0) {
      sendMessage(actor)
      sendMessagesToOne(count - 1, actor)
    }
  }

  def sendMessages(actorIds: List[Int], actors: List[ActorRef]) {
    actorIds.foreach(actorId => sendMessage(actors(actorId)))
  }

  def generateRandomActorIds(resultCount: Int, actorCount: Int): List[Int] = {
    List.fill(resultCount)(Random.nextInt(actorCount))
  }
}
