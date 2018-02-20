package com.packt.akka

import akka.actor.{Actor, ActorRef}

class Child(parent: ActorRef) extends Actor {
  def receive = {
    case "ping" => parent ! "pong"
  }
}