package com.packt.akka.talktoactor.actor

import akka.actor.{Actor, ActorRef, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.packt.akka.talktoactor.dto.User

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Recorder {

  sealed trait RecorderMsg
  case class NewUser(user: User) extends RecorderMsg

  def props(checker: ActorRef, storage: ActorRef) =
    Props(new Recorder(checker, storage))
}

class Recorder(checker: ActorRef, storage: ActorRef) extends Actor {
  import Recorder._

  implicit val timeout = Timeout(5 seconds)

  def receive = {

    case NewUser(user) =>
      println(s"Recorder receives NewUser for $user")

      checker ? Checker.CheckUser(user) map {
        case Checker.WhiteUser(u) =>
          storage ! Storage.AddUser(u)
        case Checker.BlackUser(u) =>
          println(s"Recorder: $u is in black user.")
      }
    case m =>
      println(s"$getClass: unknown message: $m")
  }
}