package models

import anorm._
import play.api.db.DB
import play.api.Play.current

case class Section(
  name: String, content: String, tooltip: String, pos: Int)

object Section {
  // var sections = Set(
  //   Section("Introduction", "Blah Blah Blah Blah Blah", "About Me", 0),
  //   Section("Education","Blah\nBlah\nBlah", "Education", 1),
  //   Section("Employment History","Blah<br/> Blah<br/> Blah<br/> Blah<br/>", "Employment History", 2)
  // )

  // def add(section: Section) {
  //   sections = sections + section
  // }

  // def findAll = sections.toList.sortBy(_.pos)

  // def findByPosition(pos: Int) = sections.find(_.pos == pos)

  def add(section: Section) {
    //sections = sections + section
    println("Saving " + section + " with name " + section.name)
    DB.withConnection { implicit c =>
      // val result: Boolean = SQL("INSERT INTO section VALUES (id = {id}, body = {body}, tooltip = {tooltip}, name = {name})")
      val result: Boolean = SQL("INSERT INTO section (id, body, tooltip, name) VALUES ({id}, {body}, {tooltip}, {name})")
        .on("id" -> section.pos, "body" -> section.content, "tooltip" -> section.tooltip, "name" -> section.name)
        // .on("id" -> section.pos, "body" -> section.content, "tooltip" -> section.tooltip, "name" -> "geordie")
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
      // sections.find(_.pos == pos)
      val query = SQL("SELECT * FROM section WHERE id={id}")
        .on("id" -> pos)
      // val sections =
      var result = query().map(row => Section(row[String]("name"), row[String]("body"), row[String]("tooltip"), row[Int]("id"))).toList
      println("Results")
      result.foreach(item =>
        print(result + ", ")
      )
      println()
      result
      // List[Section]()
      // sections.toList.sortBy(_.pos)

      // val row = query().head
      // Section(row[String]("name"), row[String]("content"), row[String]("tooltip"), row[Int]("pos"))
    }
  }

  // def spokenLanguages(query: String): Option[SpokenLanguages] = {
  //   val sections: List[(Integer, String, String, String)] = SQL(
  //     """
  //       select c.name, l.language from Country c
  //       join CountryLanguage l on l.CountryCode = c.Code
  //       where c.code = {code};
  //     """
  //   )
  //   .on("code" -> countryCode)
  //   .as(str("name") ~ str("language") map(flatten) *)

  //   languages.headOption.map { f =>
  //     SpokenLanguages(f._1, languages.map(_._2))
  //   }
  // }
}