package ar.com.crypticmind.basewebapp.integration



class UserCrudTest extends CoreTest {

  override def afterAll() {
    super.afterAll()
  }

  "The User CRUD" should {

    "return None when asked for a non-existent user" in {

      //core.userService.findByUsername("non-existent") should be (None)

    }

  }
}
