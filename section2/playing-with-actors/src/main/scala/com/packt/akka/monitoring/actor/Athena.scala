package com.packt.akka.monitoring.actor

import akka.actor.{Actor, Props}

object Athena {
  def props = Props[Athena]
}

class Athena extends Actor {

  def receive = {
    case msg =>
      println(s"Athena received $msg")
      context.stop(self)
  }
}