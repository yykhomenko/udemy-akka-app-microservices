package com.packt.akka.clusterSharding

import akka.actor.{ActorIdentity, ActorPath, ActorSystem, Identify, Props}
import akka.cluster.Cluster
import akka.cluster.sharding.{ClusterSharding, ClusterShardingSettings}
import akka.pattern.ask
import akka.persistence.journal.leveldb.{SharedLeveldbJournal, SharedLeveldbStore}
import akka.util.Timeout
import com.packt.akka.clusterSharding.actor.{Counter, Frontend}
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._

object ShardingApp extends App {

  startup(Seq("2551", "2552", "0"))

  def startup(ports: Seq[String]) = {
    ports foreach { port =>
      // Override the configuration of the port
      val config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).
        withFallback(ConfigFactory.load("sharding"))

      // Create an Akka system
      val system = ActorSystem("ClusterSystem", config)

      startupSharedJournal(system, startStore = port == "2551", path =
        ActorPath.fromString("akka.tcp://ClusterSystem@127.0.0.1:2551/user/store"))

      ClusterSharding(system).start(
        typeName = Counter.shardName,
        entityProps = Counter.props,
        settings = ClusterShardingSettings(system),
        extractEntityId = Counter.idExtractor,
        extractShardId = Counter.shardResolver)

      if (port != "2551" && port != "2552"){
        Cluster(system) registerOnMemberUp {
          system.actorOf(Props[Frontend], "frontend")
        }
      }
    }

    def startupSharedJournal(system: ActorSystem, startStore: Boolean, path: ActorPath) = {
      // Start the shared journal one one node (don't crash this SPOF)
      // This will not be needed with a distributed journal
      if (startStore) system.actorOf(Props[SharedLeveldbStore], "store")
      // register the shared journal
      import system.dispatcher
      implicit val timeout = Timeout(15 seconds)

      val f = system.actorSelection(path) ? Identify(None)

      f.foreach {
        case ActorIdentity(_, Some(ref)) =>
          SharedLeveldbJournal.setStore(ref, system)
        case _ =>
          system.log.error("Shared journal not started at {}", path)
          system.terminate()
      }

      f.failed.foreach { _ =>
          system.log.error("Lookup of shared journal at {} timed out", path)
          system.terminate()
      }
    }
  }
}