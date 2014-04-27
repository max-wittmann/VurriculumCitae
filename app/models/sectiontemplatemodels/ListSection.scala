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
// create table listSection (
//   id int not null,
//   listPos int not null,
//   header TEXT,
//   body TEXT,
//   PRIMARY KEY(id, listPos),
//   FOREIGN KEY (id) REFERENCES section(id)
// );

  override def storeInDB(section: ListSection) {
    DB.withConnection { implicit c =>
      var pos = 0
      section.listItems.foreach{listItem =>
        SQL("INSERT INTO listSection (id, listPos, header, body) VALUES ({id}, {listPos}, {header}, {body})")
          .on("id" -> section.id, "listPos" -> pos, "header" -> listItem.listHeader, "body" -> listItem.listBody)
          .execute()
        pos += 1
      }
    }
  }

  override def readFromDB(section: Section) = {
    val listItems : List[ListItem] = DB.withConnection { implicit c =>
      val query = SQL("SELECT * FROM listSection WHERE (id = {id}) ORDER BY listPos")
        .on("id" -> section.id)

      val listItems : List[ListItem] = query().map(row => new ListItem(row[String]("header"), row[String]("body"))).toList
      listItems
    }

    ListSection(section.id, section.pos, section.name, listItems)
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

  //Custom to this type
  def listItems(): List[ListItem] = {
    _listItems
  }

}