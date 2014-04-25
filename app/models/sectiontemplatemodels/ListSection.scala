package models.sectiontemplatemodels

import views.html.sectionTemplates._

class ListItem (_listHeader: String, _listBody: String) {
  def listHeader(): String = {
    _listHeader
  }

  def listBody(): String = {
    _listBody
  }
}

case class ListSection (_id: Integer, _pos: Integer, _title: String, _listItems : List[ListItem]) extends ASection
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
    listSection(this)
  }

  //Custom to this type
  def listItems(): List[ListItem] = {
    _listItems
  }
}