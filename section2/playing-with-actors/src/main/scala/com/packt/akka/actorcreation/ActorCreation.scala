package com.packt.akka.actorcreation

import akka.actor.ActorSystem
import com.packt.akka.actorcreation.actor.MusicPlayer

import scala.concurrent.Await
import scala.concurrent.duration._

object ActorCreation extends App {

  // Create the 'creation' actor system
  val system = ActorSystem("creation")

  // Create the 'Zeus' actor
  val player = system.actorOf(MusicPlayer.props, "music-player")

  //send StartMusic Message to actor
  player ! MusicPlayer.StartMusic

  // Send StopMusic Message to actor
  player ! MusicPlayer.StopMusic

  player ! "Hello"

  //shutdown system
  system.terminate()
  Await.ready(system.whenTerminated, 10 seconds)
}