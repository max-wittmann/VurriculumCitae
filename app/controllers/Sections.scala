package controllers

import play.api._
import play.api.mvc.{Action, Controller, Call, SimpleResult, Request, AnyContent}
import play.api.data.Form
import play.api.data.Forms.{mapping, number, text, nonEmptyText}
import play.api.i18n.Messages

import play.api.mvc.Flash

import models.Section

object Sections extends Controller {
  private val sectionForm: Form[Section] = Form(
    mapping(
        "Id" -> number.verifying("validation.pos.duplicate", Section.findByPosition(_).isEmpty),
        "Name" -> text.verifying("validation.name.empty", {!_.isEmpty}),
        "SectionType" -> text,
        "Position" -> number.verifying("validation.pos.duplicate", Section.findByPosition(_).isEmpty)
    )(Section.apply)(Section.unapply)
  )

  def list = Action { implicit request =>
    implicit val page = routes.Sections.list.url

    println("RouteAppIND: " + routes.Application.index.getClass)
    val t = routes.Application.index
    println(t.getClass)

    val sections = Section.findAll

    Ok(views.html.sections.list(sections))
      .withSession("redirectTo" -> routes.Sections.list.url)
  }

  def show(pos: Int) = Action {
    implicit request =>
      implicit val page = routes.Sections.show(pos).url
      val sections = Section.findByPosition(pos)

      if(sections != List.empty) {
        val section = sections.last
          session.get("redirectTo").map {
            redirectTo =>
              Ok(views.html.sections.details(section, redirectTo))
          }.getOrElse {
            Ok(views.html.sections.details(section, routes.Application.index.url))
          }
      } else {
        NotFound
      }
  }

  def save = Action { implicit request =>
    implicit val page = routes.Sections.save.url

    val newSectionForm = sectionForm.bindFromRequest()

    Redirect(routes.Sections.newSection())
    newSectionForm.fold(
      hasErrors = { form =>
        {
          println(newSectionForm.errors)
          Logger.info("Flash is: " + Flash(form.data))
          Redirect(routes.Sections.newSection()).
            flashing(Flash(form.data) +
              ("error" -> Messages("validation.errors"))
            )
          }
      },
      success = { newSection =>
        Section.add(newSection)
        val message = Messages("sections.new.success", newSection.name)
        Redirect(routes.Sections.show(newSection.pos))
          .flashing("success" -> message)
          .withSession("redirectTo" -> routes.Application.index.url)
      }
    )
  }

  def newSection() = Action { implicit request =>
    implicit val page = routes.Sections.newSection.url

    val form = if(flash.get("error").isDefined)
    {
      println("Flash data is " + flash.data)
      println(sectionForm.errors)
      sectionForm.bind(flash.data)
    }
    else
    {
      sectionForm
    }
    Ok(views.html.sections.editSection(form))
  }
}