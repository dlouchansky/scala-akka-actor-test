package com.test

import akka.actor.{PoisonPill, Props, ActorRef, Actor}
import io.{IOModule}

class BaseActor(ioModule: IOModule) extends Actor {
  var stoppedActors: Int = 0
  var runningChildActors: Int = 0
  var overallMessageCnt: Int = 0
  var startTime: Long = 0

  def receive = {
    case OneTest(messageCount: Int, sleepTime: Long) => {
      if (startTime == 0)
        startTime = System.nanoTime()
      ioModule.outStats("Started", startTime, startTime)

      val actor: ActorRef = ActorModel.createActor(Main.system, Props(classOf[WorkerActor], sleepTime, self, ioModule))
      runningChildActors += 1

      overallMessageCnt += messageCount
      ActorModel.sendMessagesToOne(messageCount, actor)
      val sentTime = System.nanoTime()
      ioModule.outStats("Messages sent", startTime, sentTime)

      ActorModel.killActor(actor)
    }

    case MultipleTest(actorCount: Int, messageCount: Int, sleepTime: Long) => {
      if (startTime == 0)
        startTime = System.nanoTime()
      ioModule.outStats("Started", startTime, startTime)

      val randomIds: List[Int] = ActorModel.generateRandomActorIds(messageCount, actorCount)
      val randomDone = System.nanoTime()
      ioModule.outStats("Random list generated", startTime, randomDone)

      val actors: List[ActorRef] = ActorModel.createActors(actorCount, Main.system, Props(classOf[WorkerActor], sleepTime, self, ioModule))
      val actorsCreatedTime = System.nanoTime()
      ioModule.outStats("Actors started", startTime, actorsCreatedTime)
      runningChildActors += actorCount

      overallMessageCnt += messageCount
      ActorModel.sendMessages(randomIds, actors)
      val sentTime = System.nanoTime()
      ioModule.outStats("Messages sent", startTime, sentTime)

      ActorModel.killActors(actors)
    }

    case Done => {
      stoppedActors += 1
      if (stoppedActors == runningChildActors) {
        val endTime = System.nanoTime()
        ioModule.testTime("Overall", startTime, endTime)
        ioModule.outThroughput(overallMessageCnt, startTime, endTime)
        self ! PoisonPill
      }
    }

    case _ =>
      ioModule.outError("Wrong action sent to BaseActor")
  }

}
