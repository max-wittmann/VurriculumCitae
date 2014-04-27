package models.sectiontemplatemodels

import views.html.sectionTemplates._
import models.Section

import anorm._
import play.api.db.DB
import play.api.Play.current

object TextSectionDBHelper extends ASectionDBHelper [TextSection] {
// create table textSection (
//   id int not null,
//   body TEXT,
//   PRIMARY KEY (id),
//   FOREIGN KEY (id) REFERENCES section(id)
// );
  override def storeInDB(section: TextSection) {
    DB.withConnection { implicit c =>
      val result: Boolean = SQL("INSERT INTO textSection (id, body) VALUES ({id}, {body})")
        .on("id" -> section.id, "body" -> section.body)
        .execute()
    }
  }

  override def readFromDB(section: Section) = {
   DB.withConnection { implicit c =>
      val query = SQL("SELECT * FROM textSection where (id = {id})").on("id" -> section.id)
      val sections = query().map(row =>
        TextSection(section.id, section.pos, section.name, row[String]("body"))
      )
      // sections.toList match {
      //   case Nil => Nil
      //   case A
      // }
      val sectionsList = sections.toList
      if(sectionsList.length > 0)
        sections.toList.head
      else
        TextSection(-1, -1, "Bad", "Bad")
      // sections.toList.sortBy(_.pos)
    }
  }
}

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

  override def sectionType(): String = {
    "TextSection"
  }

  override def applyTemplate(): play.api.templates.HtmlFormat.Appendable = {
    textSection(this)
  }

  override def storeInDB() {
    TextSectionDBHelper.storeInDB(this)
  }


  //Custom to this type
  def body(): String = {
    _body
  }
}