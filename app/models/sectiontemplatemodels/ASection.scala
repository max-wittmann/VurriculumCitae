package models.sectiontemplatemodels

import play.templates._
import models.sectiontemplatemodels._

trait ASection {
  def id(): Integer
  def pos(): Integer
  def title(): String
  def applyTemplate(): play.api.templates.HtmlFormat.Appendable
}

object ASection {
  var sections = List[ASection]()

  add(TextSection(0, 0, "First", "Body"))
  add(TextSection(1, 1, "Second", "Body"))
  add(TextSection(2, 2, "Third", "Body"))
  add(ListSection(3, 3, "Fourth", List(new ListItem("Eins", "Blah blah blah"), new ListItem("Zwei", "Blah blah blah"), new ListItem("Drei", "Blah blah blah"))))

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