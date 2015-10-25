import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  "Application" should {

    "render the Get vehicle information from DVLA page" in new WithApplication{
      val home = route(FakeRequest(GET, "/")).get

      status(home) must equalTo(OK)
      contentType(home) must beSome.which(_ == "text/html")
      contentAsString(home) must contain ("Get vehicle information from DVLA - GOV.UK")
    }

    "send 422 when 'What you were doing' is not provided when reporting a problem" in new WithApplication {
      val request = FakeRequest(POST,"/contact/govuk/problem_reports?").withFormUrlEncodedBody(
        "utf8" -> "%E2%9C%93",
        "url" -> "https%3A%2F%2Fwww.gov.uk%2Fget-vehicle-information-from-dvla",
        "source" -> "",
        "page_owner" -> "",
        "what_doing" -> "",
        "what_wrong" -> "",
        "javascript_enabled" -> "true",
        "referrer" -> "unkown")

      val problems_report = route(request)
      problems_report must beSome.which (status(_) == UNPROCESSABLE_ENTITY)
    }

    "send 201 when a problem has been reported correctly" in new WithApplication {

      val request = FakeRequest(POST,"/contact/govuk/problem_reports?").withFormUrlEncodedBody(
      "utf8" -> "%E2%9C%93",
      "url" -> "https%3A%2F%2Fwww.gov.uk%2Fget-vehicle-information-from-dvla",
      "source" -> "",
      "page_owner" -> "",
      "what_doing" -> "this+is+what+i+am+doing",
      "what_wrong" -> "",
      "javascript_enabled" -> "true",
      "referrer" -> "unkown")

      val problems_report = route(request)
      problems_report must beSome.which (status(_) == CREATED)
    }

    "return json object when a problem has been reported correctly" in new WithApplication {

      val request = FakeRequest(POST,"/contact/govuk/problem_reports?").withFormUrlEncodedBody(
        "utf8" -> "%E2%9C%93",
        "url" -> "https%3A%2F%2Fwww.gov.uk%2Fget-vehicle-information-from-dvla",
        "source" -> "",
        "page_owner" -> "",
        "what_doing" -> "this+is+what+i+am+doing",
        "what_wrong" -> "",
        "javascript_enabled" -> "true",
        "referrer" -> "unkown")

      val problems_report = route(request).get
      contentType(problems_report) must beSome.which(_ == "application/json")
      contentAsString(problems_report) must contain ("{\"message\":\"<h2>Thank you for your help.</h2> <p>If you have more extensive feedback, please visit the <a href='/contact'>contact page</a>.</p>\",\"status\":\"success\"}")
    }

  }
}
