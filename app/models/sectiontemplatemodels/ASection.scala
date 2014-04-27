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
  val typeToSection = Map("ListSection" -> ListSectionDBHelper, "TextSection" -> TextSectionDBHelper)
  // var sections = List[ASection]()

  // add(TextSection(0, 0, "First", "Body"))
  // add(TextSection(1, 1, "Second", "Body"))
  // add(TextSection(2, 2, "Third", "Body"))
  // add(ListSection(3, 3, "Fourth", List(new ListItem("Eins", "Blah blah blah"), new ListItem("Zwei", "Blah blah blah"), new ListItem("Drei", "Blah blah blah"))))

  def add(section: ASection) {
    //Add basic section details to db
    DB.withConnection { implicit c =>
      val result: Boolean = SQL("INSERT INTO section (id, pos, sectionType, name) VALUES ({id}, {pos}, {sectionType}, {name})")
        .on("id" -> section.id, "pos" -> section.pos, "sectionType" -> section.sectionType, "name" -> section.title)
        .execute()
    }

    //Add 'custom' section details to db
    section.storeInDB()

    // typeToSection(section.type).storeInDB()

    // sections ::= section
    // println("Now sections is " + sections)
  }

  // def findAll = {
  //   sections
  // }

  // def findById(id: Int) = {
  //   sections.filter(section => section.id == id)
  // }

  def findAll : List [ASection] = {
    DB.withConnection { implicit c =>
      val query = SQL("SELECT * FROM section")
      val sections: Stream[Section] = query().map(row => Section(row[Int]("id"), row[String]("name"), row[String]("sectionType"), row[Int]("pos")))
      // val result: List[Section] = sections.toList

      // result.foreach(baseSection =>
      //   println(baseSection))

      // result.map(baseSection =>
      //   generateSection(baseSection)
      // )

      val result = sections.toList.sortBy(_.pos)

      result.map(baseSection =>
        generateSection(baseSection)
      )

    }
  }

  private def generateSection(baseSection: Section): ASection = {
    val sectionType = baseSection.sectionType
    typeToSection(sectionType).readFromDB(baseSection)
  }

  def findById(pos: Int) = {
    DB.withConnection { implicit c =>
      val query = SQL("SELECT * FROM section WHERE id={id}")
        .on("id" -> pos)
      val baseResult = query().map(row => Section(row[Int]("id"), row[String]("name"), row[String]("sectionType"), row[Int]("pos"))).toList
      baseResult.map(baseSection => generateSection(baseSection))
    }
  }
}