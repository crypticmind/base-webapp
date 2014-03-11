package ar.com.crypticmind.basewebapp.integration

import ar.com.crypticmind.basewebapp.model.User

class UserCrudTest extends IntegrationTest {

  "The User CRUD" should {

    "Return None when asked for a non-existent user" in {
      userService.findByUsername("non-existent") should not be 'defined
    }

    val testUserName = "test"

    "Save a user and returns its new id" in {
      val user = User(0, testUserName)
      val persistedUser = userService.save(user)
      persistedUser.username should be(user.username)
      persistedUser.id should not be 0
    }

    "Retrieve an existing user" in {
      val existingUser = userService.findByUsername(testUserName).get
      existingUser.username should be(testUserName)
      existingUser.id should not be 0
    }

    "Delete a user" in {
      val existingUser = userService.findByUsername(testUserName).get
      existingUser.username should be(testUserName)
      existingUser.id should not be 0
      userService.delete(existingUser)
    }

    "Return None when asked for a user that has been deleted" in {
      userService.findByUsername(testUserName) should not be 'defined
    }

  }
}
