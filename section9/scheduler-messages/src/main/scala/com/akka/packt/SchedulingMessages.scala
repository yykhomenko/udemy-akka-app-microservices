package com.akka.packt

import akka.actor.{ActorSystem, Props}

object SchedulingMessages extends App{

  val system = ActorSystem("Scheduling-Messages")

//  val scheduler = system.actorOf(Props[ScheduleInConstructor], "schedule-in-constructor")
  val scheduler = system.actorOf(Props[ScheduleInReceive], "schedule-in-receive")

  Thread.sleep(5000)
  system.terminate()
}