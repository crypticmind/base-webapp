package ar.com.crypticmind.basewebapp.core

import ar.com.crypticmind.basewebapp.service.UserServiceComponent
import ar.com.crypticmind.basewebapp.dal.{ DatabaseComponent, UserRepositoryComponent }
import akka.actor.ActorSystem

trait Core
    extends UserServiceComponent
    with UserRepositoryComponent { this: DatabaseComponent ⇒

  implicit val actorSystem: ActorSystem
  lazy val userService = new UserService
  lazy val userRepository = new UserRepository
}
