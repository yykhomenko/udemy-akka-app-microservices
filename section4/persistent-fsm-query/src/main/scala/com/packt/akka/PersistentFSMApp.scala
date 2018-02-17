package com.packt.akka

import akka.actor.ActorSystem
import com.packt.akka.actor.Account
import com.packt.akka.actor.Account._

import scala.concurrent.Await
import scala.concurrent.duration._

object PersistentFSMApp extends App {

  val system = ActorSystem("persistent-fsm-actors")
  val account = system.actorOf(Account.props)

  account ! Operation(1000, CR)
  account ! Operation(10, DR)

  Thread.sleep(1000)

  system.terminate()
  Await.ready(system.whenTerminated, 10 seconds)
}