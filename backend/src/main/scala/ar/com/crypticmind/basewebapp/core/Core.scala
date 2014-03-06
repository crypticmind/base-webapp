package ar.com.crypticmind.basewebapp.core

import ar.com.crypticmind.basewebapp.service.UserServiceComponent
import ar.com.crypticmind.basewebapp.dal.{ DatabaseComponent, UserRepositoryComponent }

trait Core
    extends UserServiceComponent
    with UserRepositoryComponent { this: DatabaseComponent ⇒

  lazy val userService = new UserService
  lazy val userRepository = new UserRepository
}
