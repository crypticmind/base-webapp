package ar.com.crypticmind.basewebapp.integration

import org.scalatest.{WordSpecLike, BeforeAndAfterAll, Matchers}
import ar.com.crypticmind.basewebapp.core._
import ar.com.crypticmind.basewebapp.dal.DatabaseComponent
import akka.actor.ActorSystem


trait CoreTest extends WordSpecLike with Matchers with BeforeAndAfterAll {

  val system = ActorSystem(getClass.getSimpleName)
  val settings = ServiceSettings(system)

  val core = new Object with SettingsComponent with DatabaseComponent with Core {
    val settings = CoreTest.this.settings
  }

  override def afterAll() {
    system.shutdown()
  }
}
