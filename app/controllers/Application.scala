package controllers

import play.api._
import play.api.mvc._
import play.api.mvc.Flash

import models.Section

import anorm._
import play.api.db.DB
import play.api.Play.current

object Application extends Controller {

  def index = Action {
    implicit request =>
      DB.withConnection { implicit c =>
        var result: Boolean = SQL("Select 1").execute()
        println(result)

        var result2 = SQL("SHOW TABLES IN playtest").apply()
        println(result2)

        //SHOW [FULL] TABLES [{FROM | IN} db_name]
      }

      implicit val page = routes.Application.index.url
      Logger.info("In Index")
      val sections = Section.findAll

      Ok(views.html.index(sections))
        .withSession("redirectTo" -> routes.Application.index.url)
  }

  def about = Action {
    implicit request =>
      implicit val page = routes.Application.about.url
      Ok(views.html.about())
  }

  def contact = Action {
    implicit request =>
      implicit val page = routes.Application.contact.url
      Ok(views.html.contact())
  }

}