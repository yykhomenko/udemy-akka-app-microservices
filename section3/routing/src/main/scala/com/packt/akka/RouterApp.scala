package com.packt.akka

import akka.actor.ActorSystem
import com.packt.akka.actor.Worker
import com.packt.akka.actor.Worker.Work
import com.packt.akka.actor.router.{RouterGroup, RouterPool}

import scala.concurrent.Await
import scala.concurrent.duration._

object RouterApp extends App {

  val system = ActorSystem("router")

  val router = system.actorOf(RouterPool.props)

  router ! Work
  router ! Work
  router ! Work

  Thread.sleep(100)
  println()

  system.actorOf(Worker.props, "w1")
  system.actorOf(Worker.props, "w2")
  system.actorOf(Worker.props, "w3")
  system.actorOf(Worker.props, "w4")
  system.actorOf(Worker.props, "w5")

  val workers = List("/user/w1", "/user/w2", "/user/w3", "/user/w4", "/user/w5")
  val routerGroup = system.actorOf(RouterGroup.props(workers))

  routerGroup ! Work
  routerGroup ! Work

  Thread.sleep(100)

  system.terminate()
  Await.ready(system.whenTerminated, 10 seconds)
}