package com.test

import akka.actor.{PoisonPill, ActorRef, Actor}
import io.IOModule

class SenderActor(ioModule: IOModule, actorFactory: ChildActorService) extends Actor {
  var stoppedActors: Int = 0
  var runningChildActors: Int = 0
  var overallMessageCnt: Int = 0
  var startTime: Long = 0

  def receive = {
    case OneTest(messageCount: Int) => {
      if (startTime == 0)
        startTime = System.nanoTime()
      ioModule.outStats("Started", startTime, startTime)

      val actor: ActorRef = actorFactory.createActor(self)
      runningChildActors += 1

      overallMessageCnt += messageCount
      actorFactory.sendMessagesToOne(messageCount, actor)
      val sentTime = System.nanoTime()
      ioModule.outStats("Messages sent", startTime, sentTime)

      actorFactory.killActor(actor)
    }

    case MultipleTest(actorCount: Int, messageCount: Int) => {
      if (startTime == 0)
        startTime = System.nanoTime()
      ioModule.outStats("Started", startTime, startTime)

      val randomIds: List[Int] = actorFactory.generateRandomActorIds(messageCount, actorCount)
      val randomDone = System.nanoTime()
      ioModule.outStats("Random list generated", startTime, randomDone)

      val actors: List[ActorRef] = actorFactory.createActors(actorCount, self)
      val actorsCreatedTime = System.nanoTime()
      ioModule.outStats("Actors started", startTime, actorsCreatedTime)
      runningChildActors += actorCount

      overallMessageCnt += messageCount
      actorFactory.sendMessages(randomIds, actors)
      val sentTime = System.nanoTime()
      ioModule.outStats("Messages sent", startTime, sentTime)

      actorFactory.killActors(actors)
    }

    case Done => {
      stoppedActors += 1
      if (stoppedActors == runningChildActors) {
        val endTime = System.nanoTime()
        ioModule.testTime("Overall", startTime, endTime)
        ioModule.outThroughput(overallMessageCnt, startTime, endTime)
        Main.system.shutdown()
      }
    }

    case _ =>
      ioModule.outError("Wrong action sent to BaseActor")
  }

}
