package com.packt.akka.monitoring.actor

import akka.actor.{Actor, ActorRef, Props, Terminated}

object Ares {
  def props(athena: ActorRef) = Props(classOf[Ares], athena)
}

class Ares(athena: ActorRef) extends Actor {

  override def preStart() =
    context.watch(athena)

  override def postStop() =
    println("Ares postStop...")

  def receive = {
    case Terminated =>
      context.stop(self)
  }
}