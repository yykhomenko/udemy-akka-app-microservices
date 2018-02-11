package com.packt.akka.actorcreation

import java.util.concurrent.TimeUnit

import akka.actor.{ActorSystem, Props}
import com.packt.akka.actorcreation.actor.MusicPlayer

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object ActorCreation extends App {

  // Create the 'creation' actor system
  val system = ActorSystem("creation")

  // Create the 'Zeus' actor
  val player = system.actorOf(Props[MusicPlayer], "music-player")

  //send StartMusic Message to actor
  player ! MusicPlayer.StartMusic
//  player ! MusicPlayer.StartMusic

  // Send StopMusic Message to actor
  player ! MusicPlayer.StopMusic

  player ! "Hello"

  //shutdown system
  system.terminate()
  Await.ready(system.whenTerminated, Duration(10, TimeUnit.SECONDS))
}