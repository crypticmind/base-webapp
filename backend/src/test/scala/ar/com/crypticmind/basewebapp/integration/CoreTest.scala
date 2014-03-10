package ar.com.crypticmind.basewebapp.integration

import org.scalatest.{WordSpecLike, BeforeAndAfterAll, Matchers}
import ar.com.crypticmind.basewebapp.core._
import ar.com.crypticmind.basewebapp.dal.DatabaseComponent
import akka.actor.ActorSystem
import org.slf4j.LoggerFactory
import ar.com.crypticmind.basewebapp.misc.DirectoryListing


trait CoreTest extends WordSpecLike with Matchers with BeforeAndAfterAll {

  val system = ActorSystem(getClass.getSimpleName)
  val settings = ServiceSettings(system)
  val log = LoggerFactory.getLogger(getClass)

  val core = new Object with SettingsComponent with DatabaseComponent with Core {
    val settings = CoreTest.this.settings

    val scriptVersions =
      DirectoryListing
        .listResources("database/")
        .map(_.replaceAllLiterally(".sql", ""))
        .sorted

    // Run each file!

  }

  override def afterAll() {
    // When you override this method, you need to 'super.afterAll()' at the end of your implementation.
    system.shutdown()
  }
}
