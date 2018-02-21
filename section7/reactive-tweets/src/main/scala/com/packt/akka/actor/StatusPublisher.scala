package com.packt.akka.actor

import akka.actor.Props
import akka.stream.actor.ActorPublisher
import com.packt.akka.model.Tweet

object StatusPublisher {
  def props = Props[StatusPublisher]
}

class StatusPublisher extends ActorPublisher[Tweet] {

  val sub = context.system.eventStream.subscribe(self, classOf[Tweet])

  override def receive: Receive = {
    case s: Tweet => if (isActive && totalDemand > 0) onNext(s)
    case _ =>
  }

  override def postStop() =
    context.system.eventStream.unsubscribe(self)
}