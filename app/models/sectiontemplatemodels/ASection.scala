package models.sectiontemplatemodels

//T is the data object which

trait ASection {
  def id(): Integer
  def pos(): Integer
  def title(): String

  def generateHtml(): String
  def generateString(): String
}