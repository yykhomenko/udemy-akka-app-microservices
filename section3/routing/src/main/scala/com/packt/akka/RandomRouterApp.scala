package com.packt.akka

import akka.actor.{ActorSystem, Props}
import akka.routing.{FromConfig, RandomGroup}
import com.packt.akka.actor.Worker
import com.packt.akka.actor.Worker.Work

import scala.concurrent.Await
import scala.concurrent.duration._

object RandomRouterApp extends App {

  val system = ActorSystem("random-router")

  val routerPool = system.actorOf(FromConfig.props(Worker.props), "random-router-pool")

  routerPool ! Work
  routerPool ! Work
  routerPool ! Work
  routerPool ! Work

  Thread.sleep(100)
  println()

  system.actorOf(Worker.props, "w1")
  system.actorOf(Worker.props, "w2")
  system.actorOf(Worker.props, "w3")

  val workers = List("/user/w1", "/user/w2", "/user/w3")
  val routerGroup = system.actorOf(RandomGroup(workers).props, "random-router-group")

  routerGroup ! Work
  routerGroup ! Work
  routerGroup ! Work
  routerGroup ! Work

  Thread.sleep(100)

  system.terminate()
  Await.ready(system.whenTerminated, 10 seconds)
}