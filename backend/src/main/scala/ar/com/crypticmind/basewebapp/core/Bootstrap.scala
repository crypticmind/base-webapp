package ar.com.crypticmind.basewebapp.core

import akka.actor.{ Props, ActorSystem }
import ar.com.crypticmind.basewebapp.httpserver.{ ServerManager, ServiceActor }
import ar.com.crypticmind.basewebapp.httpserver.routes.{ WebAppRoutes, ServiceStateRoutes, UserRoutes }
import ar.com.crypticmind.basewebapp.dal.DatabaseComponent

trait Bootstrap {

  // The actor system.
  implicit val system = ActorSystem("app")
  val settings = ServiceSettings(system)

  // The service actor that will dispatch incoming requests.
  private class WiredService
      extends ServiceActor
      with SettingsComponent
      with DatabaseComponent
      with Core
      with UserRoutes
      with WebAppRoutes
      with ServiceStateRoutes {
    val settings = Bootstrap.this.settings
    val actorSystem = Bootstrap.this.system
  }

  // The manager that handles the lifecycle of the service actor.
  val httpService = system.actorOf(Props(new WiredService))
  val serverManager = system.actorOf(Props(new ServerManager(httpService, settings)))

  // Go!
  serverManager ! ServerManager.Start

}
