package controllers

import play.api._
import play.api.mvc._
import play.api.mvc.Flash

object Application extends Controller {

   def index = Action {
      Logger.info("In Index")
      Redirect(routes.Sections.list())
  }

}