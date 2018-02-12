package com.packt.akka.actor.router

import akka.actor.{Actor, Props}
import com.packt.akka.actor.Worker.Work

object RouterGroup {
  def props(routees: List[String]) = Props(classOf[RouterGroup], routees)
}

class RouterGroup(routees: List[String]) extends Actor {

  def receive = {
    case msg@Work =>
      println(s"${getClass.getSimpleName}: I receive Work Message....")
      context.actorSelection(routees(util.Random.nextInt(routees.size))) forward msg
  }
}