package com.packt.akka

import akka.actor.ActorSystem
import com.packt.akka.actor.Worker._
import com.typesafe.config.ConfigFactory

object MemberServiceLookupApp extends App {

  val config = ConfigFactory.load.getConfig("MemberServiceLookup")
  val system = ActorSystem("MemberServiceLookup", config)

  val worker = system.actorSelection("akka.tcp://MembersService@127.0.0.1:2552/user/remote-worker")

  worker ! Work("Hi Remote Actor")
}