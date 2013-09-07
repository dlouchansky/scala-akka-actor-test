package com.test

import akka.actor.{Props, ActorRef, Actor}

class BaseActor extends Actor {
  var stoppedActors: Int = 0

  def receive = {
    case OneTest(messageCount: Int, sleepTime: Int) => {
      val startTime = System.currentTimeMillis()
      IOModule.outStats("Started", startTime, startTime)

      val actor: ActorRef = ActorModel.createActor(Main.system, Props(classOf[WorkerActor], sleepTime))

      ActorModel.sendMessagesToOne(messageCount, actor)
      val sentTime = System.currentTimeMillis()
      IOModule.outStats("Messages sent", startTime, sentTime)

      ActorModel.killActor(actor)
    }

    case MultipleTest(actorCount: Int, messageCount: Int, sleepTime: Int) => {
      val startTime = System.currentTimeMillis()
      IOModule.outStats("Started", startTime, startTime)

      val randomIds: List[Int] = ActorModel.generateRandomActorIds(messageCount, actorCount)
      val randomDone = System.currentTimeMillis()
      IOModule.outStats("Random list generated", startTime, randomDone)

      val actors: List[ActorRef] = ActorModel.createActors(actorCount, Main.system, Props(classOf[WorkerActor], sleepTime))
      val actorsCreatedTime = System.currentTimeMillis()
      IOModule.outStats("Actors started", startTime, actorsCreatedTime)

      ActorModel.sendMessages(randomIds, actors)
      val sentTime = System.currentTimeMillis()
      IOModule.outStats("Messages sent", startTime, sentTime)

      ActorModel.killActors(actors)
    }

    case Done => {
      stoppedActors += 1
      if (stoppedActors == actorCount) {
        val endTime = System.currentTimeMillis()
        IOModule.testTime()
        IOModule.outThroughput()
        Main.system.stop(self)
      }
    }

    case _ =>
      IOModule.outError("Wrong action sent to BaseActor")
  }

}
