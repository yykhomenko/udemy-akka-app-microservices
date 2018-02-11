package com.packt.akka.supervision

import akka.actor.ActorSystem
import com.packt.akka.supervision.actor.Hera

import scala.concurrent.Await
import scala.concurrent.duration._

object Supervision extends App {

  // Create the 'supervision' actor system
  val system = ActorSystem("supervision")

  // Create Hera Actor 
  val hera = system.actorOf(Hera.props, "hera")

//   hera ! "Resume"
//   Thread.sleep(1000)
//   println()

//   hera ! "Restart"
//   Thread.sleep(1000)
//   println()

  hera ! "Stop"
  Thread.sleep(1000)
  println()

  system.terminate()
  Await.ready(system.whenTerminated, 10 seconds)
}