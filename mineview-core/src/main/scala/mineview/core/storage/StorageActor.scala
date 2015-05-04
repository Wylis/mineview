package mineview.core.storage

import akka.actor._

class StorageActor extends Actor {
  override def receive: Receive = ???
}

object StorageActor{
  def props(storage: Storage) = Props(new StorageActor)
  
  sealed trait StorageMessage
  sealed trait StorageMessageResponse
  final case class Add(x:AnyVal) extends StorageMessage
  final case class Added(x:AnyVal) extends StorageMessageResponse
  final case class AddFailed(x:AnyVal, e:Exception) extends StorageMessageResponse
}
