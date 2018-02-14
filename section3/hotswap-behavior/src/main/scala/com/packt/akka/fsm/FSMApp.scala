package com.packt.akka.fsm

import akka.actor.ActorSystem
import com.packt.akka.fsm.actor.UserStorageFSM
import com.packt.akka.fsm.actor.UserStorageFSM._

import scala.concurrent.Await
import scala.concurrent.duration._

object FSMApp extends App {

  val system = ActorSystem("hotswap-FSM")
  val userStorage = system.actorOf(UserStorageFSM.props, "user-storage-fsm")

  userStorage ! Connect
  userStorage ! Operation(DBOperation.Create, User("Admin", "admin@packt.com"))
  userStorage ! Disconnect

  Thread.sleep(100)

  system.terminate()
  Await.ready(system.whenTerminated, 10 seconds)
}