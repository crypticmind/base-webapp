package ar.com.crypticmind.basewebapp.dal

import ar.com.crypticmind.basewebapp.model.User


trait UserRepositoryComponent { this: AppDataStore with DatabaseComponent =>

  val userRepository: UserRepository

  class UserRepository {

    import jdbcDriver.simple._

    def findByUsername(username: String)(implicit session: Session): Option[User] =
      (for { u <- schema.users if u.username === username } yield u).list.headOption

    private val usersAutoInc = schema.users returning schema.users.map(_.id) into { case (u, id) => u.copy(id = id) }
    def save(user: User)(implicit session: Session): User =
      user.id match {
        case Some(id) => (for { u <- schema.users if u.id === id } yield u).update(user); user
        case None => usersAutoInc.insert(user)
      }

    def delete(user: User)(implicit session: Session) {
      (for { u <- schema.users if u.id === user.id } yield u).delete
    }

    def getAllUsers()(implicit session: Session): List[User] = schema.users.list
  }
}
