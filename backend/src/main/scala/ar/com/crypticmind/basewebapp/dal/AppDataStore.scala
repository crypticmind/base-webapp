package ar.com.crypticmind.basewebapp.dal

import ar.com.crypticmind.basewebapp.model._


trait AppDataStore { this: DatabaseComponent =>

  import jdbcDriver.simple._

  object schema {
    class Users(tag: Tag) extends Table[User](tag, "tusers") {
      def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
      def username = column[String]("username")
      def * = (id, username) <> (User.tupled, User.unapply)
    }
    val users = TableQuery[Users]
  }

}
