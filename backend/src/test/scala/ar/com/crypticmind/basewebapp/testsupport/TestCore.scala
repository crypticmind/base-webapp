package ar.com.crypticmind.basewebapp.testsupport

import ar.com.crypticmind.basewebapp.core.{ ServiceSettings, Core, SettingsComponent }
import ar.com.crypticmind.basewebapp.dal.DatabaseComponent
import akka.actor.ActorSystem
import org.slf4j.LoggerFactory

trait TestCore
    extends SettingsComponent
    with DatabaseComponent
    with TestDataSource
    with DatabaseUpdateComponent
    with Core {

  val system = ActorSystem(getClass.getSimpleName)
  val settings = ServiceSettings(system)
  val log = LoggerFactory.getLogger(getClass)

  updateDatabase()

}
