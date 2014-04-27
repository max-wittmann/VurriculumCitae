package models

import anorm._
import play.api.db.DB
import play.api.Play.current

case class Section(
  id: Int, name: String, sectionType: String, pos: Int)

object Section {

  def add(section: Section) {
    // println("Saving " + section + " with name " + section.name)
    DB.withConnection { implicit c =>
      val result: Boolean = SQL("INSERT INTO section (id, name, sectionType, pos) VALUES ({id}, {name}, {sectionType}, {pos})")
        .on("id" -> section.id, "name" -> section.name, "sectionType" -> section.sectionType, "pos" -> section.pos)
        .execute()
    }
  }

  def findAll = {
    DB.withConnection { implicit c =>
      val query = SQL("SELECT * FROM section")
      val sections = query().map(row => Section(row[Int]("id"), row[String]("name"), row[String]("sectionType"), row[Int]("pos")))
      val result = sections.toList.sortBy(_.pos)
      // result.foreach(item =>
      //   print(result + ", ")
      // )
      // println()
      // List[Section]()
      result
    }
  }

  def findByPosition(pos: Int) = {
    DB.withConnection { implicit c =>
      val query = SQL("SELECT * FROM section WHERE id={id}")
        .on("id" -> pos)
      query().map(row => Section(row[Int]("id"), row[String]("name"), row[String]("sectionType"), row[Int]("pos"))).toList
    }
  }
}