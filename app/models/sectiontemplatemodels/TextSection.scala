package models.sectiontemplatemodels

class TextSection (id: Integer, pos: Integer, title: String, body: String) extends ASection
{
  //Overriden base stuff
  override def id(): Integer = {
    0
  }

  override def pos(): Integer = {
    0
  }

  override def title(): String = {
    "Default"
  }

  override def generateHtml(): String = {
    "<h1>Hello</h1><p>Goooo</p>"
  }

  override def generateString(): String = {
    "Test"
  }

  //Custom to this type
  def body(): String = {
    "This is a body<br/>Of text</br>Haha!</br>"
  }
}