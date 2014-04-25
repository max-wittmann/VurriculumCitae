package models.sectiontemplatemodels

import views.html.sectionTemplates._

case class TextSection (_id: Integer, _pos: Integer, _title: String, _body: String) extends ASection
{
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

  override def applyTemplate(): play.api.templates.HtmlFormat.Appendable = {
    textSection(this)
  }

  //Custom to this type
  def body(): String = {
    _body
  }
}