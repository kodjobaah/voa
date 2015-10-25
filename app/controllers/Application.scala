package controllers

import com.voa.form.{Problems, Search}
import com.voa.response.ProblemsResponse
import play.api._
import play.api.libs.json.{Writes, Json}
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import play.api.Play.current
import play.api.i18n.Messages.Implicits._

class Application extends Controller {


  val ProblemsMessage: String = "\u003ch2\u003eThank you for your help.\u003c/h2\u003e \u003cp\u003eIf you have more extensive feedback, please visit the \u003ca href='/contact'\u003econtact page\u003c/a\u003e.\u003c/p\u003e"

  val ProblemsStatus: String = "success"

  val VoaTitle = "Get vehicle information from DVLA - GOV.UK"

  val VoaPageHeader = "Get vehicle information from DVLA"

  val problemsForm: Form[Problems] = Form(
    mapping(
      "url" -> optional(text),
      "source" -> optional(text),
      "page_owner" -> optional(text),
      "what_doing" -> nonEmptyText,
      "what_wrong" -> optional(text),
      "utf8" -> optional(text),
      "javascript_enabled" -> optional(text),
      "referer" -> optional(text)
    )(Problems.apply)(Problems.unapply))

  val searchForm: Form[Search] = Form(
    mapping(
      "q" -> text
    )(Search.apply)(Search.unapply)
  )

  implicit val problemsResponseWrites = new Writes[ProblemsResponse] {
    def writes(problemsResponse: ProblemsResponse) = Json.obj(
      "message" -> problemsResponse.message,
      "status" -> problemsResponse.status
    )
  }

  def index = Action {
    Ok(views.html.index(VoaTitle, VoaPageHeader, searchForm, problemsForm))
  }

  def search = Action {
    Ok(views.html.index(VoaTitle, VoaPageHeader, searchForm, problemsForm))
  }

  def problems = Action { implicit request =>

    println("inside problems")
    problemsForm.bindFromRequest.fold(
      errors => {
        UnprocessableEntity
      },
      problems => {
        val problemsResponse = ProblemsResponse(ProblemsMessage,ProblemsStatus)
        val json = Json.toJson(problemsResponse)
        Created(json)
      }
    )
  }
}
