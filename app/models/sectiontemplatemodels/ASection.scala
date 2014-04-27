package models.sectiontemplatemodels

import play.templates._
import models.sectiontemplatemodels._
import models.Section

import anorm._
import play.api.db.DB
import play.api.Play.current

trait ASection {
  def id(): Integer
  def pos(): Integer
  def title(): String
  def sectionType(): String
  def applyTemplate(): play.api.templates.HtmlFormat.Appendable
  def storeInDB()
}

//Should force this to subset ASection
trait ASectionDBHelper [T] {
  def storeInDB(section: T)
  def readFromDB(section: Section) : T
}

object ASection {
  val typeToSection = Map(
    "ListSection" -> ListSectionDBHelper,
    "TextSection" -> TextSectionDBHelper,
    "CompoundSection" -> CompoundSectionDBHelper
  )

  def add(section: ASection) {
    //Add basic section details to db
    DB.withConnection { implicit c =>
      val result: Boolean = SQL("INSERT INTO section (id, pos, sectionType, name) VALUES ({id}, {pos}, {sectionType}, {name})")
        .on("id" -> section.id, "pos" -> section.pos, "sectionType" -> section.sectionType, "name" -> section.title)
        .execute()
    }

    //Add 'custom' section details to db
    section.storeInDB()
  }

  def findAll : List [ASection] = {
    DB.withConnection { implicit c =>
      val query = SQL("SELECT * FROM section")
      val sections: Stream[Section] = query().map(row => Section(row[Int]("id"), row[String]("name"), row[String]("sectionType"), row[Int]("pos")))

      val result = sections.toList.sortBy(_.pos)

      result.map(baseSection =>
        generateSection(baseSection)
      )

    }
  }

  // def generateSectionFromId(id: Int): ASection = {
  //   val base

  //   val sectionType = baseSection.sectionType
  //   typeToSection(sectionType).readFromDB(baseSection)
  // }

  private def generateSection(baseSection: Section): ASection = {
    val sectionType = baseSection.sectionType
    typeToSection(sectionType).readFromDB(baseSection)
  }

  //Todo: Should return Option[ASection]
  def findById(pos: Int) : ASection = {
    DB.withConnection { implicit c =>
      val query = SQL("SELECT * FROM section WHERE id={id}")
        .on("id" -> pos)
      val baseResult = query().map(row => Section(row[Int]("id"), row[String]("name"), row[String]("sectionType"), row[Int]("pos"))).toList
      val result = baseResult.map(baseSection => generateSection(baseSection))

      //Todo: Replace this!
      if(result.size == 1) {
        result.head
      }
      else if(result.size == 0) {
        throw new RuntimeException("Didn't find result!")
      }
      else {
        throw new RuntimeException("Found more than one result!")
      }

    }
  }

  // def findById(pos: Int) = {
  //   DB.withConnection { implicit c =>
  //     val query = SQL("SELECT * FROM section WHERE id={id}")
  //       .on("id" -> pos)
  //     val baseResult = query().map(row => Section(row[Int]("id"), row[String]("name"), row[String]("sectionType"), row[Int]("pos"))).toList
  //     baseResult.map(baseSection => generateSection(baseSection))
  //   }
  // }
}