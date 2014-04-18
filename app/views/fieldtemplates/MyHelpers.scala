package views.fieldtemplates

import views.html.helper.FieldConstructor
import play.api.i18n._
import views.html.helper._

import views.html.fieldtemplates.defaultBootstrapTemplateConstructor

object MyHelpers
{
  implicit val defaultBootstrapField = FieldConstructor(defaultBootstrapTemplateConstructor.f)
}