package models

import slick.jdbc.PostgresProfile.api._
import scala.concurrent.Await
import scala.concurrent.duration._

case class User(
                 id: Int,
                 uname: String,
                 password: String,
                 email: String,
                 role: String,
                 tempLink: String,
                 active: Boolean
               )
class Users(tag: Tag) extends Table[User](tag, "site_user") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc) //Serial column
  def uname = column[String]("uname")
  def password = column[String]("password")
  def email = column[String]("email")
  def role = column[String]("role")
  def tempLink = column[String]("templink")
  def active = column[Boolean]("active")


  def * = (id, uname, password, email, role, tempLink, active) <> (User.tupled, User.unapply)
}

object Users {
  val users = TableQuery[Users]

  def findActive(email: String): Option[User] =
    Await.result(Datasource.db.run(users.filter(_.active === true).filter(_.email === email).result.headOption), Duration.Inf)

  def updatePw(id: Int, hashedPw: String) = {
    Datasource.db.run(users.filter(_.id === id).map(_.password).update(hashedPw))
  }
}