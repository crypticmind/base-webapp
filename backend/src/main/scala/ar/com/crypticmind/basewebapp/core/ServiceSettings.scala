package ar.com.crypticmind.basewebapp.core

import spray.util.SettingsCompanion
import com.typesafe.config.Config

case class ServiceSettings(interface: String,
                           port: Int,
                           dbDialect: String,
                           dbRequiredVersion: String) {
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
      db getString "dialect",
      db getString "required-version")
  }
}
