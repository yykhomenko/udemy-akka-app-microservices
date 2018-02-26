package akka.pattern.throttle

import akka.actor.{Actor, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import com.akka.pattern.throttle.Throttler.{SetTarget, _}
import com.akka.pattern.throttle.TimerBasedThrottler
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}

import scala.concurrent.duration._

object TimerBasedThrottlerSpec {
  class EchoActor extends Actor {
    def receive = {
      case x ⇒ sender ! x
    }
  }
}

class TimerBasedThrottlerSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
  with WordSpecLike with MustMatchers with BeforeAndAfterAll {

  def this() = this(ActorSystem("TimerBasedThrottlerSpec"))

  override def afterAll = system.terminate()

  "A throttler" must {

    "must pass the ScalaDoc class documentation example program" in {
      // A simple actor that prints whatever it receives
      val printer = system.actorOf(Props(new Actor {
        def receive = {
          case x ⇒ println(x)
        }
      }))
      // The throttler for this example, setting the rate
      val throttler = system.actorOf(Props(new TimerBasedThrottler(3 msgsPer (1 second))))
      // Set the target 
      throttler ! SetTarget(Some(printer))
      // These three messages will be sent to the echoer immediately
      throttler ! "1"
      throttler ! "2"
      throttler ! "3"
      // These two will wait until a second has passed
      throttler ! "4"
      throttler ! "5"
    }

    "keep messages until a target is set" in {
      val echo = system.actorOf(Props[TimerBasedThrottlerSpec.EchoActor])
      val throttler = system.actorOf(Props(new TimerBasedThrottler(3 msgsPer (1 second))))
      throttler ! "1"
      throttler ! "2"
      throttler ! "3"
      throttler ! "4"
      throttler ! "5"
      throttler ! "6"
      expectNoMessage(1 second)
      throttler ! SetTarget(Some(echo))
      within(2 second) {
        expectMsg("1")
        expectMsg("2")
        expectMsg("3")
        expectMsg("4")
        expectMsg("5")
        expectMsg("6")
      }
    }

    "respect the rate (3 msg/s)" in {
      val echo = system.actorOf(Props[TimerBasedThrottlerSpec.EchoActor])
      val throttler = system.actorOf(Props(new TimerBasedThrottler(3 msgsPer (1 second))))
      throttler ! SetTarget(Some(echo))
      throttler ! "1"
      throttler ! "2"
      throttler ! "3"
      throttler ! "4"
      throttler ! "5"
      throttler ! "6"
      throttler ! "7"
      within(1 second) {
        expectMsg("1")
        expectMsg("2")
        expectMsg("3")
        expectNoMessage(remaining)
      }
      within(1 second) {
        expectMsg("4")
        expectMsg("5")
        expectMsg("6")
        expectNoMessage(remaining)
      }
      within(1 seconds) {
        expectMsg("7")
      }
    }

    "respect the rate (4 msg/s)" in {
      val echo = system.actorOf(Props[TimerBasedThrottlerSpec.EchoActor])
      val throttler = system.actorOf(Props(new TimerBasedThrottler(4 msgsPer (1 second))))
      throttler ! SetTarget(Some(echo))
      throttler ! "1"
      throttler ! "2"
      throttler ! "3"
      throttler ! "4"
      throttler ! "5"
      throttler ! "6"
      throttler ! "7"
      throttler ! "8"
      throttler ! "9"
      within(1 second) {
        expectMsg("1")
        expectMsg("2")
        expectMsg("3")
        expectMsg("4")
        expectNoMessage(remaining)
      }
      within(1 second) {
        expectMsg("5")
        expectMsg("6")
        expectMsg("7")
        expectMsg("8")
        expectNoMessage(remaining)
      }
      within(1 seconds) {
        expectMsg("9")
      }
    }
  }
}