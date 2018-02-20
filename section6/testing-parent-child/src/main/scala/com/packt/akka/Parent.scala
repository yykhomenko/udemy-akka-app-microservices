package com.packt.akka

import akka.actor.{Actor, ActorRef, ActorRefFactory}

class Parent(childMaker: ActorRefFactory => ActorRef) extends Actor {

  val child = childMaker(context)
  var ponged = false

  def receive = {
    case "ping" => child ! "ping"
    case "pong" => ponged = true
  }
}