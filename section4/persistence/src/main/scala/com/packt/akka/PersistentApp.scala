package com.packt.akka

import akka.actor.ActorSystem
import com.packt.akka.actor.Counter
import com.packt.akka.actor.Counter._

import scala.concurrent.Await
import scala.concurrent.duration._


object PersistentApp extends App {

  val system = ActorSystem("persistent-actors")
  val counter = system.actorOf(Counter.props)

  counter ! Cmd(Increment(3))
  counter ! Cmd(Increment(5))
  counter ! Cmd(Decrement(3))
  counter ! "print"

  Thread.sleep(1000)

  system.terminate()
  Await.ready(system.whenTerminated, 10 seconds)
}