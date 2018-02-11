package com.packt.akka.talktoactor

import akka.actor.ActorSystem
import com.packt.akka.talktoactor.actor.{Checker, Recorder, Storage}
import com.packt.akka.talktoactor.dto.User

import scala.concurrent.Await
import scala.concurrent.duration._

object TalkToActor extends App {

  // Create the 'talk-to-actor' actor system
  val system = ActorSystem("talk-to-actor")

  // Create the 'checker' actor
  val checker = system.actorOf(Checker.props, "checker")

  // Create the 'storage' actor
  val storage = system.actorOf(Storage.props, "storage")

  // Create the 'recorder' actor
  val recorder = system.actorOf(Recorder.props(checker, storage), "recorder")

  //send NewUser Message to Recorder
  recorder ! Recorder.NewUser(User("Jon", "jon@packt.com"))
  recorder ! Recorder.NewUser(User("Adam", "adam@mail.com"))

  Thread.sleep(100)

  //shutdown system
  system.terminate()
  Await.ready(system.whenTerminated, 10 seconds)
}