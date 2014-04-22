package models

import anorm._
import play.api.db.DB
import play.api.Play.current

case class Section(
  name: String, content: String, tooltip: String, pos: Int)

object Section {

  def add(section: Section) {
    println("Saving " + section + " with name " + section.name)
    DB.withConnection { implicit c =>
      val result: Boolean = SQL("INSERT INTO section (id, body, tooltip, name) VALUES ({id}, {body}, {tooltip}, {name})")
        .on("id" -> section.pos, "body" -> section.content, "tooltip" -> section.tooltip, "name" -> section.name)
        .execute()
    }
  }

  def findAll = {
    DB.withConnection { implicit c =>
      val query = SQL("SELECT * FROM section")
      val sections = query().map(row => Section(row[String]("name"), row[String]("body"), row[String]("tooltip"), row[Int]("id")))
      val result = sections.toList.sortBy(_.pos)
      result.foreach(item =>
        print(result + ", ")
      )
      println()
      List[Section]()
      result
    }
  }

  def findByPosition(pos: Int) = {
    DB.withConnection { implicit c =>
      val query = SQL("SELECT * FROM section WHERE id={id}")
        .on("id" -> pos)
      query().map(row => Section(row[String]("name"), row[String]("body"), row[String]("tooltip"), row[Int]("id"))).toList
    }
  }
}