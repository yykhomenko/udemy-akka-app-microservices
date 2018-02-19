package com.packt.akka.actor

import akka.actor.{Actor, Props}
import com.packt.akka.actor.Counter._

object Counter {

  case object Increment
  case object Decrement
  case object GetCount
  
  def props = Props[Counter]
}

class Counter extends Actor {
  
  var count: Int = 0

  def receive = {
    case Increment => count += 1
    case Decrement => count -= 1
    case GetCount  => sender ! count
  }
}