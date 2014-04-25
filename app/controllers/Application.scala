package controllers

import play.api._
import play.api.mvc._
import play.api.mvc.Flash

import models.Section
import models.sectiontemplatemodels._

import anorm._
import play.api.db.DB
import play.api.Play.current

object Application extends Controller {

  def index = Action {
    implicit request =>
      implicit val page = routes.Application.index.url
      Logger.info("In Index")
      val sections = Section.findAll
      val asections = ASection.findAll

      Ok(views.html.index(sections, asections))
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