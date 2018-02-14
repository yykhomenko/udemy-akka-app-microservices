package com.packt.akka.become

import akka.actor.ActorSystem
import com.packt.akka.become.actor.UserStorage
import com.packt.akka.become.actor.UserStorage._

import scala.concurrent.Await
import scala.concurrent.duration._

object BecomeApp extends App {

  val system = ActorSystem("hotswap-become")
  val userStorage = system.actorOf(UserStorage.props, "userStorage")

  userStorage ! Operation(DBOperation.Create, Some(User("Admin", "admin@packt.com")))
  userStorage ! Connect
  userStorage ! Disconnect

  Thread.sleep(100)

  system.terminate()
  Await.ready(system.whenTerminated, 10 seconds)
}