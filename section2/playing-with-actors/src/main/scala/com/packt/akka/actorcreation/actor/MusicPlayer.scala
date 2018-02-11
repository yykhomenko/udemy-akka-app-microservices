package com.packt.akka.actorcreation.actor

import akka.actor.{Actor, Props}

object MusicPlayer {

  sealed trait PlayerMsg
  case object StartMusic extends PlayerMsg
  case object StopMusic extends PlayerMsg

  def props = Props[MusicPlayer]
}

class MusicPlayer extends Actor {
  import MusicPlayer._

  def receive = {
    case StartMusic =>
      val controller = context.actorOf(MusicController.props, "music-controller")
      controller ! MusicController.Play
    case StopMusic =>
      println("I don't want to stop music.")
    case m =>
      println(s"$getClass: unknown message: $m")
  }
}