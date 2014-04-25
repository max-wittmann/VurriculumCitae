package models.sectiontemplatemodels

import views.html.sectionTemplates._

case class TextSection (_id: Integer, _pos: Integer, _title: String, _body: String) extends ASection
{
  //val textTemplate = textSection

  //Overriden base stuff
  override def id(): Integer = {
    _id
  }

  override def pos(): Integer = {
    _pos
  }

  override def title(): String = {
    _title
  }

  override def generateHtml(): String = {
    "<h1>Hello</h1><p>Goooo</p>"
  }

  override def generateString(): String = {
    "Test"
  }

  // override def template(): BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]] = {
  //   textTemplate
  // }

  override def applyTemplate(): play.api.templates.HtmlFormat.Appendable = {
    textSection(this)
  }

  //Custom to this type
  def body(): String = {
    // "This is a body<br/>Of text</br>Haha!</br>"
    _body
  }
}