package ar.com.crypticmind.basewebapp.dal

import ar.com.crypticmind.basewebapp.model.User


trait UserRepositoryComponent { this: DatabaseComponent ⇒

  val userRepository: UserRepository

  class UserRepository {

    def findByUsername(username: String): Option[User] = {
      import com.googlecode.mapperdao.Query._
      val u = database.UserEntity
      val q = select from u where u.username === username
      database.queryDao.query(q).headOption
    }

    def save(user: User): User = {
      import com.googlecode.mapperdao.Update._
      val u = database.UserEntity
      user.id match {
        case 0 =>
          database.mapperDao.insert(u, user)
        case id =>
          (update(u) set (u.username === user.username) where(u.id === id)).run(database.queryDao)
          user
      }
    }

    def remove(user: User) {
      import com.googlecode.mapperdao.Delete._
      val u = database.UserEntity
      (delete from u where(u.id === user.id)).run(database.queryDao)
    }

    def getAllUsers: List[User] = {
      import com.googlecode.mapperdao.Query._
      database.queryDao.query(select from database.UserEntity)
    }
  }
}
