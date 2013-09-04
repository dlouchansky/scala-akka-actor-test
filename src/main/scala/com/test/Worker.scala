package com.test

import akka.actor.Actor

object Worker {
  case object Add
  case object GetCount
  case object Exit
}

class Worker extends Actor {

  var count: Int = 0

  def receive = {
    case Worker.Add =>
      count = count + 1
    case Worker.Exit =>
      sender ! Main.Done
    case Worker.GetCount =>
      println(count)
    case _ =>

  }
}