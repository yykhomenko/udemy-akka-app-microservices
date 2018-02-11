package com.packt.akka.supervision.actor

import akka.actor.{Actor, Props}

object Aphrodite {

  case object ResumeException extends Exception
  case object StopException extends Exception
  case object RestartException extends Exception

  def props = Props[Aphrodite]
}

class Aphrodite extends Actor {
  import Aphrodite._

  override def preStart() =
    println("Aphrodite preStart hook....")

  override def preRestart(reason: Throwable, message: Option[Any]) = {
    println("Aphrodite preRestart hook...")
    super.preRestart(reason, message)
  }

  override def postRestart(reason: Throwable) = {
    println("Aphrodite postRestart hook...")
    super.postRestart(reason)
  }

  override def postStop() =
    println("Aphrodite postStop...")

  def receive = {
    case "Resume"   => throw ResumeException
    case "Stop"     => throw StopException
    case "Restart"  => throw RestartException
    case _          => throw new Exception
  }
}