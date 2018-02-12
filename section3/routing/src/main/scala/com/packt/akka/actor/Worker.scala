package com.packt.akka.actor

import akka.actor.{Actor, Props}

object Worker {
  case object Work
  def props = Props[Worker]
}

class Worker extends Actor {
  import Worker._

  def receive = {
    case Work => println(s"${getClass.getSimpleName}: I received Work Message and My ActorRef: $self")
    case m    => println(s"$getClass: unknown message: $m")
  }
}

