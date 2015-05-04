package mineview.core

import akka.actor._


trait Boot extends Actor {
import Boot._
  def receiveInitialize: Actor.Receive = {
    case Initialize => ???
  }

  override def receive: Receive = receiveInitialize

  override def preStart() = {
    self ! Initialize
    super.preStart()
  }
}

object Boot extends Boot {
  sealed trait BootMsg
  sealed trait BootMsgRsp extends BootMsg
  sealed object Initialize extends BootMsg
  sealed object Initialized extends BootMsgRsp
  final case class InitializationFailed(e: Exception) extends BootMsgRsp
}