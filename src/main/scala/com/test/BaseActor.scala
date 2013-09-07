package com.test

import akka.actor.{PoisonPill, Props, ActorRef, Actor}

class BaseActor extends Actor {
  var stoppedActors: Int = 0
  var runningChildActors: Int = 0
  var overallMessageCnt: Int = 0
  var startTime: Long = 0;

  def receive = {
    case OneTest(messageCount: Int, sleepTime: Long) => {
      if (startTime == 0)
        startTime = System.currentTimeMillis()
      IOModule.outStats("Started", startTime, startTime)

      val actor: ActorRef = ActorModel.createActor(Main.system, Props(classOf[WorkerActor], sleepTime, self))
      runningChildActors += 1

      overallMessageCnt += messageCount
      ActorModel.sendMessagesToOne(messageCount, actor)
      val sentTime = System.currentTimeMillis()
      IOModule.outStats("Messages sent", startTime, sentTime)

      ActorModel.killActor(actor)
    }

    case MultipleTest(actorCount: Int, messageCount: Int, sleepTime: Long) => {
      if (startTime == 0)
        startTime = System.currentTimeMillis()
      IOModule.outStats("Started", startTime, startTime)

      val randomIds: List[Int] = ActorModel.generateRandomActorIds(messageCount, actorCount)
      val randomDone = System.currentTimeMillis()
      IOModule.outStats("Random list generated", startTime, randomDone)

      val actors: List[ActorRef] = ActorModel.createActors(actorCount, Main.system, Props(classOf[WorkerActor], sleepTime, self))
      val actorsCreatedTime = System.currentTimeMillis()
      IOModule.outStats("Actors started", startTime, actorsCreatedTime)
      runningChildActors += actorCount

      overallMessageCnt += messageCount
      ActorModel.sendMessages(randomIds, actors)
      val sentTime = System.currentTimeMillis()
      IOModule.outStats("Messages sent", startTime, sentTime)

      ActorModel.killActors(actors)
    }

    case Done => {
      println("received")
      stoppedActors += 1
      if (stoppedActors == runningChildActors) {
        val endTime = System.currentTimeMillis()
        IOModule.testTime("Overall", startTime, endTime)
        IOModule.outThroughput(overallMessageCnt, startTime, endTime)
        self ! PoisonPill
      }
    }

    case _ =>
      IOModule.outError("Wrong action sent to BaseActor")
  }

}
