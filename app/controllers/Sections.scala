package controllers

import play.api.mvc.{Action, Controller}
import play.api.data.Form
import play.api.data.Forms.{mapping, number, nonEmptyText}
import play.api.i18n.Messages

import models.Section

object Sections extends Controller {
  private val sectionForm: Form[Section] = Form(
    mapping(
        "name" -> nonEmptyText,
        "tooltip" -> nonEmptyText,
        "pos" -> number.verifying("validation.pos.duplicate", Section.findByPosition(_).isEmpty)
    )(Section.apply)(Section.unapply)
  )
  def list = Action { implicit request =>

    val sections = Section.findAll

    Ok(views.html.sections.list(sections))
  }

  def show(pos: Int) = Action { implicit request =>

    Section.findByPosition(pos).map { section =>
      Ok(views.html.sections.details(section))
    }.getOrElse(NotFound)
  }
}