import java.util
import java.util.Properties

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.{RemoteWebDriver, DesiredCapabilities}
import org.specs2.matcher.SignificantFigures
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.Play

import play.api.test._
import play.api.test.Helpers._

/**
 * add your integration spec here.
 * An integration test will fire up a whole play application in a real (or headless) browser
 */
@RunWith(classOf[JUnitRunner])
class IntegrationSpec extends Specification {

  System.setProperty("webdriver.chrome.driver","/Users/kodjobaah/projects/voa/drivers/chromedriver")
  "Application" should {

    "Get vehicle information from DVLA" in new WithBrowser(webDriver = WebDriverFactory(classOf[ChromeDriver])) {
      browser.goTo("http://localhost:" + 9000)
      browser.pageSource must contain("Get vehicle information from DVLA")

    }

  }

}
