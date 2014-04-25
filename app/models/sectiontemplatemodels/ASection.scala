package models.sectiontemplatemodels

import play.templates._
// import play.templates.TemplateMagic._

// import play.api.templates._
// import play.api.templates.PlayMagic._
// import models._
// import controllers._
// import play.api.i18n._
// import play.api.mvc._
// import play.api.data._
// import views.html._


import models.sectiontemplatemodels.TextSection
//T is the data object which

trait ASection {
  def id(): Integer
  def pos(): Integer
  def title(): String
  def applyTemplate(): play.api.templates.HtmlFormat.Appendable

  def generateHtml(): String
  def generateString(): String
}

object ASection {
  var sections = List[ASection]()

  add(TextSection(0, 0, "First", "Body"))
  add(TextSection(1, 1, "Second", "Body"))
  add(TextSection(2, 2, "Third", "Body"))
  println("Inside ASection init")

  def add(section: ASection) {
    sections ::= section
    println("Now sections is " + sections)
  }

  def findAll = {
    sections
  }

  def findById(id: Int) = {
    sections.filter(section => section.id == id)
  }
}