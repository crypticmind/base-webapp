package ar.com.crypticmind.basewebapp.integration

import ar.com.crypticmind.basewebapp.testsupport.TestCore

class AnotherTest extends TestCore {

  override def afterAll() {
    super.afterAll()
  }

  "Some other test" should {

    "do something as expected" in {

      log.info(s"Database ${core.testDatabaseName} version ${core.database.currentVersion}")

    }

  }
}
