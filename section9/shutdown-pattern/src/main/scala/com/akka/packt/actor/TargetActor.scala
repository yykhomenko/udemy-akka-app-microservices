package com.akka.packt.actor

import java.util.Date

import akka.actor.Actor
import com.akka.pattern.ReaperWatched

class TargetActor extends Actor with ReaperWatched {
  def receive = {
    case msg => println(s"[${new Date().toString}]I received a message: $msg")
  }
}