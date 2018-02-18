package com.packt.akka.singleton.actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.cluster.singleton.{ClusterSingletonProxy, ClusterSingletonProxySettings}
import com.packt.akka.singleton.actor.Master.{RegisterWorker, RequestWork, Work}

import scala.concurrent.duration._

object Worker {
  def props = Props[Worker]
}

class Worker extends Actor with ActorLogging {
  import context.dispatcher

  val masterProxy = context.actorOf(ClusterSingletonProxy.props(
    singletonManagerPath = "/user/master",
    settings = ClusterSingletonProxySettings(context.system).withRole(None)),
    name = "masterProxy")

  context.system.scheduler.schedule(0 second, 30 seconds, masterProxy, RegisterWorker(self))
  context.system.scheduler.schedule(3 seconds, 3 seconds, masterProxy, RequestWork(self))

  def receive = {
    case Work(requester, op) =>
      log.info(s"Worker: I received work with op: $op and I will reply to $requester.")
  }
}