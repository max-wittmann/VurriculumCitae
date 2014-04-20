package models

case class Section(
  name: String, content: String, tooltip: String, pos: Int)

object Section {
  var sections = Set(
    Section("Introduction", "Blah Blah Blah Blah Blah", "About Me", 0),
    Section("Education","Blah\nBlah\nBlah", "Education", 1),
    Section("Employment History","Blah<br/> Blah<br/> Blah<br/> Blah<br/>", "Employment History", 2)
  )

  def add(section: Section) {
    sections = sections + section
  }

  def findAll = sections.toList.sortBy(_.pos)

  def findByPosition(pos: Int) = sections.find(_.pos == pos)
}