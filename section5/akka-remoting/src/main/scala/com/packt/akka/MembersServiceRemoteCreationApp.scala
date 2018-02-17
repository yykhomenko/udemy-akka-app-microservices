package com.packt.akka

import akka.actor.ActorSystem
import com.packt.akka.actor.Worker._
import com.typesafe.config.ConfigFactory

object MembersServiceRemoteCreationApp extends App {

  val config = ConfigFactory.load.getConfig("MembersServiceRemoteCreation")
  val system = ActorSystem("MembersServiceRemoteCreation", config)

  val workerActor = system.actorOf(props, "workerActorRemote")

  println(s"The remote path of worker Actor is ${workerActor.path}")

  workerActor ! Work("Hi Remote Worker")
}