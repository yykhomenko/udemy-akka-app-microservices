package com.akka.packt

import akka.actor.{ActorSystem, PoisonPill, Props}
import com.akka.pattern._

object OrderTermination extends App{

  val system = ActorSystem("order-termination")

  val terminator = system.actorOf(Terminator.props(Props[Worker], 5))
  val master = system.actorOf(Props(new Master(terminator)))

  master ! "hello world"
  master ! PoisonPill

  Thread.sleep(5000)
  system.terminate()
}