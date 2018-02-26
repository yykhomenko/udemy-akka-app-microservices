package com.akka.packt

import akka.actor.{ActorSystem, PoisonPill, Props}
import com.akka.packt.actor.TargetActor
import com.akka.pattern._

object ShutdownApp extends App {

  val system = ActorSystem("shutdown")
  val reaper = system.actorOf(Props[Reaper], Reaper.name)
  val target = system.actorOf(Props[TargetActor], "target")

  target ! "Hello World"
  target ! PoisonPill
}