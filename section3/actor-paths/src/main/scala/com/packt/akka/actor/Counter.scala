package com.packt.akka.actor

import akka.actor.{Actor, Props}

object Counter {

	sealed trait CounterMsg
	final case class Inc(num: Int)
	final case class Dec(num: Int)

	def props = Props[Counter]
}

class Counter extends Actor {
	import Counter._

	var count = 0

	def receive = {
		case Inc(x) => count += x
		case Dec(x) => count -= x
	}
}