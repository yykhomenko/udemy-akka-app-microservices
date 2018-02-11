package com.packt.akka

import akka.actor.{Actor, ActorSystem, Props}

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

object MusicPlayer {
  case object StartMusic
  case object StopMusic
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
      println("Unknown message: " + m)
  }
}

object Creation extends App {

  // Create the 'creation' actor system
  val system = ActorSystem("creation")

  // Create the 'Zeus' actor
  val player = system.actorOf(Props[MusicPlayer], "music-player")

  //send StartMusic Message to actor
  player ! MusicPlayer.StartMusic

  // Send StopMusic Message to actor
  player ! MusicPlayer.StopMusic

  player ! "Hello"

  //shutdown system
  system.terminate()
}