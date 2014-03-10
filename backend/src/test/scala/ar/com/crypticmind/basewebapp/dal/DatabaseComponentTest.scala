package ar.com.crypticmind.basewebapp.dal

import org.scalatest.{Matchers, WordSpecLike}
import ar.com.crypticmind.basewebapp.dal.DatabaseComponent.Version

class DatabaseComponentTest extends WordSpecLike with Matchers {

  "Version objects" should {

    "be comparable" in {
      Version(1, 2, 3) should not be Version(4, 5, 6)
      Version(1, 2, 3) should be (Version(1, 2, 3))
      Version(1, 0, 0) should be > Version(0, 9, 0)
      Version(1, 0, 0) should be < Version(1, 1, 0)
    }

    "be constructed from string notation" in {
      Version(1, 2, 3) should be (Version("1.2.3"))
    }

    "enforce the expression major.minor.revision" in {
      intercept[IllegalArgumentException] {
        Version("a, b, c")
      }
      intercept[IllegalArgumentException] {
        Version("1.2.3.4")
      }
    }

  }

}
