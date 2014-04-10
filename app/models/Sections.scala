package models

case class Section(
  name: String, tooltip: String, pos: Int)

object Section {
  var sections = Set(
    Section("Introduction", "About Me", 0),
    Section("Education", "Education", 1),
    Section("Employment History", "Employment History", 2)
  )

  def add(section: Section) {
    sections = sections + section
  }

  def findAll = sections.toList.sortBy(_.pos)

  def findByPosition(pos: Int) = sections.find(_.pos == pos)
}