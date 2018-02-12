package com.packt.akka

import akka.actor.{ActorSystem, Props}
import akka.routing.{FromConfig, RoundRobinPool}
import com.packt.akka.actor.Worker
import com.packt.akka.actor.Worker.Work

import scala.concurrent.Await
import scala.concurrent.duration._

object RoRbnRouterApp extends App {

  val system = ActorSystem("round-robin-router")

  val routerPool = system.actorOf(RoundRobinPool(3).props(Worker.props), "round-robin-pool")

  routerPool ! Work
  routerPool ! Work
  routerPool ! Work
  routerPool ! Work

  Thread.sleep(100)
  println()

  system.actorOf(Worker.props, "w1")
  system.actorOf(Worker.props, "w2")
  system.actorOf(Worker.props, "w3")

  val routerGroup = system.actorOf(FromConfig.props, "round-robin-group")

  routerGroup ! Work
  routerGroup ! Work
  routerGroup ! Work
  routerGroup ! Work

  Thread.sleep(100)

  system.terminate()
  Await.ready(system.whenTerminated, 10 seconds)
}