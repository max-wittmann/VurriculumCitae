package views.fieldtemplates

import views.html.helper.FieldConstructor
import play.api.i18n._
import views.html.helper._

import views.html.fieldtemplates.defaultBootstrapTemplateConstructor

object MyHelpers
{
  // implicit val myFields = FieldConstructor(defaultBootstrapTemplate.scala.html)

  // implicit val defaultBootstrapField = new FieldConstructor {
  //   def apply(elts: FieldElements) = defaultBootstrapTemplateConstructor(elts)
  // }

  implicit val defaultBootstrapField = FieldConstructor(defaultBootstrapTemplateConstructor.f)
}