package ar.com.crypticmind.basewebapp.tool

import akka.actor.ActorSystem
import ar.com.crypticmind.basewebapp.core.{SettingsComponent, ServiceSettings}
import ar.com.crypticmind.basewebapp.dal.DatabaseComponent


object InitializeDatabase extends App {

  // The actor system.
  implicit val system = ActorSystem("app")
  val settings = ServiceSettings(system)

  val schemaContainer = new Object with SettingsComponent with DatabaseComponent {
    val settings = InitializeDatabase.this.settings


  }

  system.shutdown()
}
