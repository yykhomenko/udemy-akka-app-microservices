package com.packt.akka

import akka.actor.Actor
import com.packt.akka.Worker._

object Worker {
  case object Work
  case object Done
}

class Worker extends Actor {

  def receive = {
    case Work =>
      println(s"I received Work Message and My ActorRef: $self")
      sender ! Done
  }
}