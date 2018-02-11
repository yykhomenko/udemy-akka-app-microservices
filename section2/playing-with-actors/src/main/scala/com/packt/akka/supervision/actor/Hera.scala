package com.packt.akka.supervision.actor

import akka.actor.SupervisorStrategy.{Escalate, Restart, Resume, Stop}
import akka.actor.{Actor, ActorRef, OneForOneStrategy, Props}

import scala.concurrent.duration._

object Hera {
  def props = Props[Hera]
}

class Hera extends Actor {
  import Aphrodite._

  var childRef: ActorRef = _

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 second) {
      case ResumeException      => Resume
      case RestartException     => Restart
      case StopException        => Stop
      case _: Exception         => Escalate
    }

  override def preStart() = {
    // Create Aphrodite Actor
    childRef = context.actorOf(Aphrodite.props, "aphrodite")
    Thread.sleep(100)
  }

  override def postStop() =
    println("Hera postStop...")

  def receive = {
    case msg =>
      println(s"Hera received $msg")
      childRef ! msg
      Thread.sleep(100)
  }
}