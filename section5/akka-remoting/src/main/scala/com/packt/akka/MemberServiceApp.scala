package com.packt.akka

import akka.actor.ActorSystem
import com.packt.akka.actor.Worker
import com.typesafe.config.ConfigFactory

object MemberServiceApp extends App {

  val config = ConfigFactory.load.getConfig("MembersService")
  val system = ActorSystem("MembersService", config)

  val worker = system.actorOf(Worker.props, "remote-worker")

  println(s"Worker actor path is ${worker.path}")
}