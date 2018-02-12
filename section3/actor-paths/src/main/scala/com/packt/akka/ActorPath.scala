package com.packt.akka

import akka.actor.ActorSystem
import com.packt.akka.actor.{Counter, Watcher}

import scala.concurrent.Await
import scala.concurrent.duration._

object ActorPath extends App {

  val system = ActorSystem("watch-actor-selection")

  val counter = system.actorOf(Counter.props, "counter")
  val watcher = system.actorOf(Watcher.props, "watcher")

	Thread.sleep(1000)

	system.terminate()
  Await.ready(system.whenTerminated, 10 seconds)
}