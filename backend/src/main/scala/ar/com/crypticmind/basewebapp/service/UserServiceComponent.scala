package ar.com.crypticmind.basewebapp.service

import ar.com.crypticmind.basewebapp.dal.{DatabaseComponent, UserRepositoryComponent}
import ar.com.crypticmind.basewebapp.model.User


trait UserServiceComponent { this: UserRepositoryComponent with DatabaseComponent ⇒

  val userService: UserService

  class UserService {

    def authenticate(username: String) = database.withSession { implicit session => findByUsername(username).isDefined }

    def findByUsername(username: String): Option[User] = database.withSession { implicit session => userRepository.findByUsername(username) }

    def save(user: User): User = database.withTransaction { implicit session => userRepository.save(user) }

    def delete(user: User): Unit = database.withTransaction { implicit session => userRepository.delete(user) }

    def getAllUsers: List[User] = database.withSession { implicit session => userRepository.getAllUsers }
  }
}
