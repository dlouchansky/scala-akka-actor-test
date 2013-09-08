package com.test

import akka.actor.{ActorSystem, Props, ActorRef}
import io.IOModule

class ReceiverActorFactory(sleepTime: Long, ioModule: IOModule, _actorSystem: ActorSystem) extends ChildActorFactory {

  def sendMessage(actorId: ActorRef) {
    actorId ! Add
  }

  def killActor(actorId: ActorRef) {
    actorId ! Exit
  }

  def newActorProp(parentActor: ActorRef): Props = {
    Props(classOf[ReceiverActor], sleepTime, parentActor, ioModule)
  }

  def actorSystem(): ActorSystem = {
    _actorSystem
  }
}
