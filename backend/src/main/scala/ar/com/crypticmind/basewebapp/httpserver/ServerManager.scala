package ar.com.crypticmind.basewebapp.httpserver

import akka.actor.{ ActorLogging, Actor, ActorSystem, ActorRef }
import ar.com.crypticmind.basewebapp.core.ServiceSettings
import akka.io.IO
import spray.can.Http
import scala.concurrent.duration._
import akka.util.Timeout

class ServerManager(val httpServiceActor: ActorRef, val settings: ServiceSettings)(implicit system: ActorSystem)
    extends Actor
    with ActorLogging {

  import akka.pattern._
  import ServerManager._

  implicit val timeout = Timeout(30 seconds)

  def receive = stopped orElse unknown

  def unknown: Receive = {
    case x ⇒ log.error("Received unexpected message {}", x)
  }

  def stopped: Receive = {
    case Start ⇒
      IO(Http) ! Http.Bind(httpServiceActor, settings.interface, settings.port)
      become(starting)
  }

  def starting: Receive = {
    case Http.Bound(address) ⇒
      become(started(listener = sender))
  }

  def started(listener: ActorRef): Receive = {
    case Stop ⇒
      listener ! Http.Unbind
      become(stopping)
  }

  def stopping: Receive = {
    case Http.Unbound ⇒
      IO(Http) ? Http.CloseAll
      become(stopped)
  }

  def become(receive: Receive) = context.become(receive orElse unknown)
}

object ServerManager {
  case object Start
  case object Stop
}
