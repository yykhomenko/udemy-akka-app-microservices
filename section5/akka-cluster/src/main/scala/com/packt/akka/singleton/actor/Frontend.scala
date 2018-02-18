package com.packt.akka.singleton.actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.cluster.singleton.{ClusterSingletonProxy, ClusterSingletonProxySettings}
import com.packt.akka.singleton.actor.Frontend.Tick
import com.packt.akka.singleton.actor.Master.Work

import scala.concurrent.duration._

object Frontend {
  case object Tick
  def props = Props[Frontend]
}

class Frontend extends Actor with ActorLogging {
  import context.dispatcher

  val masterProxy = context.actorOf(ClusterSingletonProxy.props(
    singletonManagerPath = "/user/master",
    settings = ClusterSingletonProxySettings(context.system).withRole(None)),
    name = "masterProxy")

  context.system.scheduler.schedule(0.second, 3.second, self, Tick)

  def receive = {
    case Tick => masterProxy ! Work(self, "add")
  }
}