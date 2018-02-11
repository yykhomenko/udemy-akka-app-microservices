package com.packt.akka.talktoactor.actor

import akka.actor.{Actor, Props}
import com.packt.akka.talktoactor.dto.User

object Checker {

  sealed trait CheckerMsg
  case class CheckUser(user: User) extends CheckerMsg
  case class WhiteUser(user: User) extends CheckerMsg
  case class BlackUser(user: User) extends CheckerMsg

  def props = Props[Checker]
}

class Checker extends Actor {
  import Checker._

  val blackList = List(User("Adam", "adam@mail.com"))

  def receive = {

    case CheckUser(user) =>

      if (blackList.contains(user)) {
        println(s"Checker: $user is in blackList.")
        sender ! BlackUser(user)
      } else {
        println(s"Checker: $user isn't in blackList.")
        sender ! WhiteUser(user)
      }

    case m =>
      println(s"$getClass: unknown message: $m")
  }
}