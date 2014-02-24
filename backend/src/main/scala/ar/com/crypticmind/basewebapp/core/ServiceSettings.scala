package ar.com.crypticmind.basewebapp.core

import spray.util.SettingsCompanion
import com.typesafe.config.Config

case class ServiceSettings(interface: String,
                           port: Int,
                           dbDriver: String,
                           dbDialect: String,
                           dbUrl: String,
                           dbUsername: String,
                           dbPassword: String) {
  require(interface.nonEmpty, "Interface must be non-empty")
  require(0 < port && port < 65536, "Illegal port")
}

object ServiceSettings extends SettingsCompanion[ServiceSettings]("app") {
  def fromSubConfig(c: Config) = {
    val server = c.getConfig("server")
    val db = c.getConfig("database")
    apply(
      server getString "interface",
      server getInt "port",
      db getString "driver-class",
      db getString "dialect",
      db getString "url",
      db getString "username",
      db getString "password")
  }
}
