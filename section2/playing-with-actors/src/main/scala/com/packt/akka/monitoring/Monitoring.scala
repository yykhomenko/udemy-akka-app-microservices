package com.packt.akka.monitoring

import akka.actor.ActorSystem
import com.packt.akka.monitoring.actor.{Ares, Athena}

import scala.concurrent.Await
import scala.concurrent.duration._

object Monitoring extends App {

  // Create the 'monitoring' actor system
  val system = ActorSystem("monitoring")

  val athena = system.actorOf(Athena.props, "athena")

  val ares = system.actorOf(Ares.props(athena), "ares")

  athena ! "Hi"

  Thread.sleep(200)

  system.terminate()
  Await.ready(system.whenTerminated, 10 seconds)
}