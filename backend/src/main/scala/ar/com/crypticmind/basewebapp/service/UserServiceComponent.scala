package ar.com.crypticmind.basewebapp.service

import ar.com.crypticmind.basewebapp.dal.{ DatabaseComponent, UserRepositoryComponent }
import ar.com.crypticmind.basewebapp.model.User

trait UserServiceComponent { this: UserRepositoryComponent with DatabaseComponent ⇒

  val userService: UserService

  class UserService {

    def authenticate(username: String) = findByUsername(username).isDefined

    def findByUsername(username: String): Option[User] = userRepository.findByUsername(username)

    def save(user: User): User = userRepository.save(user)

    def delete(user: User): Unit = userRepository.remove(user)

    def getAllUsers: List[User] = userRepository.getAllUsers
  }
}
