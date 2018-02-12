package com.packt.akka.actor.router

import akka.actor.{Actor, ActorRef, Props}
import com.packt.akka.actor.Worker
import com.packt.akka.actor.Worker.Work

object RouterPool {
  def props = Props[RouterPool]
}

class RouterPool extends Actor {

  var routees: List[ActorRef] = _

  override def preStart() =
    routees = List.fill(5)(context.actorOf(Worker.props))

  def receive() = {
    case msg@Work =>
      println(s"${getClass.getSimpleName}: I received a Message.....")
      routees(util.Random.nextInt(routees.size)) forward msg
  }
}
