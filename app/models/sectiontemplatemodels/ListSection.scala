package models.sectiontemplatemodels

import views.html.sectionTemplates._
import models.Section

import anorm._
import play.api.db.DB
import play.api.Play.current

class ListItem (_listHeader: String, _listBody: String) {
  def listHeader(): String = {
    _listHeader
  }

  def listBody(): String = {
    _listBody
  }
}

object ListSectionDBHelper extends ASectionDBHelper [ListSection]  {
  override def storeInDB(section: ListSection) {
    // DB.withConnection { implicit c =>
    //   val result: Boolean = SQL("INSERT INTO section (id, pos, body, tooltip, name) VALUES ({id}, {pos}, {body}, {tooltip}, {name})")
    //     .on("id" -> section.id, "pos" -> section.pos, "body" -> section.content, "tooltip" -> section.tooltip, "name" -> section.name)
    //     .execute()
    // }
  }

  override def readFromDB(section: Section) = {
    ListSection(0, 0, "Boo", List())
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

  override def sectionType(): String = {
    "ListSection"
  }

  override def applyTemplate(): play.api.templates.HtmlFormat.Appendable = {
    listSection(this)
  }

  override def storeInDB() {
    ListSectionDBHelper.storeInDB(this)
  }

// create table listSection (
//   id int not null,
//   listPos int not null,
//   header TEXT,
//   body TEXT,
//   PRIMARY KEY(id, listPos),
//   FOREIGN KEY (id) REFERENCES section(id)
// );

  //Custom to this type
  def listItems(): List[ListItem] = {
    _listItems
  }

}