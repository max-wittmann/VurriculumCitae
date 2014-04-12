package controllers

import play.api.mvc.{Action, Controller}
import play.api.data.Form
import play.api.data.Forms.{mapping, number, nonEmptyText}
import play.api.i18n.Messages

import play.api.mvc.Flash

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

  def save = Action { implicit request =>
    val newSectionForm = sectionForm.bindFromRequest()

    Redirect(routes.Sections.newSection())
    newSectionForm.fold(
      hasErrors = { form =>
        Redirect(routes.Sections.newSection()).
          flashing(Flash(form.data) +
            ("error" -> Messages("validation.errors")))
      },
      success = { newSection =>
        Section.add(newSection)
        val message = Messages("sections.new.success", newSection.name)
        Redirect(routes.Sections.show(newSection.pos)).
          flashing("success" -> message)
      }
    )
  }

  def newSection = Action { implicit request =>
    val form = if(flash.get("error").isDefined)
      sectionForm.bind(flash.data)
    else
      sectionForm

    Ok(views.html.sections.editSection(form))
  }
}