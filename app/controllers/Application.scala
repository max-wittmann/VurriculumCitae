package controllers

import play.api._
import play.api.mvc._
import play.api.mvc.Flash

object Application extends Controller {

   def index = Action {
      Logger.info("Hello World!")
      Logger.error("Boo")
      Redirect(routes.Sections.list())
  }

}