package models.sectiontemplatemodels

import views.html.sectionTemplates._
import models.Section

import anorm._
import play.api.db.DB
import play.api.Play.current

object CompoundSectionDBHelper extends ASectionDBHelper [CompoundSection]  {

  // create table compoundSection (
  //   parentId int not null,
  //   sectionId int not null,
  //   sectionPos int not null,
  //   PRIMARY KEY(parentId, sectionId),
  //   FOREIGN KEY (parentId) REFERENCES section(id),
  //   FOREIGN KEY (sectionId) REFERENCES section(id)
  // );

  override def storeInDB(section: CompoundSection) {
    DB.withConnection { implicit c =>
      var pos = 0
      section.subsections.foreach{subsection =>
        SQL("INSERT INTO compoundSection (parentId, sectionId, sectionPos) VALUES ({parentId}, {sectionId}, {sectionPos})")
          .on("parentId" -> section.id, "sectionId" -> subsection.id, "sectionPos" -> pos)
          .execute()
        pos += 1
      }
    }
  }

  override def readFromDB(section: Section) = {
    val listItems : List[ASection] = DB.withConnection { implicit c =>
      val query = SQL("SELECT * FROM compoundSection WHERE (parentId = {parentId}) ORDER BY sectionPos")
        .on("parentId" -> section.id)

      // val subsections : List[ASection] = query().map(row => new ListItem(row[String]("header"), row[String]("body"))).toList
      // listItems

      // val subsections : List[ASection] = query().map(row => new ListItem(row[String]("header"), row[String]("body"))).toList
      val subsections : List[ASection] = query().map(row => ASection.findById(row[Int]("sectionId"))).toList
        // new ListItem(row[String]("header"), row[String]("body"))).toList
      subsections
    }

    CompoundSection(section.id, section.pos, section.name, listItems)
  }

  // private def readSection(id: Int): ASection = {
  //   ASection.
  // }
}



case class CompoundSection (_id: Integer, _pos: Integer, _title: String, _subsections : List[ASection]) extends ASection
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
    "CompoundSection"
  }

  override def applyTemplate(): play.api.templates.HtmlFormat.Appendable = {
    compoundSection(this)
  }

  override def storeInDB() {
    CompoundSectionDBHelper.storeInDB(this)
  }

  //Custom to this type
  def subsections(): List[ASection] = {
    _subsections
  }

}