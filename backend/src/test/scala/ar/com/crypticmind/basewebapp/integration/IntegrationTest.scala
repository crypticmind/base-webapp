package ar.com.crypticmind.basewebapp.integration

import org.scalatest.{ BeforeAndAfterAll, Matchers, WordSpecLike }
import ar.com.crypticmind.basewebapp.testsupport.TestCore

trait IntegrationTest extends TestCore with WordSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll() {
    actorSystem.shutdown()
  }

}
