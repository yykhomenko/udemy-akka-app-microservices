package com.packt.akka.actorcreation.actor

import akka.actor.{Actor, Props}

object MusicController {
  case object Play
  case object Stop

  def props = Props[MusicController]
}

class MusicController extends Actor {
  import MusicController._

  def receive = {
    case Play =>
      println("Music Started .............")
    case Stop =>
      println("Music Stopped .............")
    case m =>
      println("Unknown message: " + m)
  }
}