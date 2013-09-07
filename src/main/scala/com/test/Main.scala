package com.test

import akka.actor._

case class Add()
case class GetCount()
case class Exit()
case class Done()

object Main {
  val system = ActorSystem("mySystem")

  def main(args: Array[String]) {
    system.actorOf(Props(classOf[BaseActor], 5000, 50, 50))
  }
}


