package com.packt.akka.actor

import akka.actor.{Actor, Props}
import com.packt.akka.actor.Worker.Work

object Worker {
  case class Work(message: String)
  def props = Props[Worker]
}

class Worker extends Actor {

  def receive = {
    case msg: Work =>
      println(s"I received Work Message and My ActorRef: $self")
  }
}

