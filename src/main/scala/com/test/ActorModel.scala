package com.test

import akka.actor._
import util.Random

object ActorModel {

  def createActor(system: ActorSystem, actorProp: Props): ActorRef = {
    system.actorOf(actorProp)
  }

  def sendMessage(actorId: ActorRef) {
    actorId ! Add
  }

  def killActors(store: List[ActorRef]) {
    store.foreach(actor => killActor(actor))
  }

  def killActor(actorId: ActorRef) {
    actorId ! Kill
  }

  def createActors(n: Int, system: ActorSystem, actorProp: Props): List[ActorRef] = {
    createActors(n, List(), system, actorProp)
  }

  def createActors(n: Int, store: List[ActorRef], system: ActorSystem, actorProp: Props): List[ActorRef] = {
    if (n < 1)
      List()
    else {
      val greeter = createActor(system, actorProp)

      if (n == 1)
        greeter :: store
      else {
        createActors(n - 1, greeter :: store, system, actorProp)
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
