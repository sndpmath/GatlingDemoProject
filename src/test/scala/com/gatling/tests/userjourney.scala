package com.gatling.tests



import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import io.gatling.http.HeaderNames._
import scala.concurrent.duration._

class userjourney extends Simulation {

  val httpProtocol =http.baseUrl("https://onlineboutique.dev")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")

  val chromeHeaders = Map("User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36")

  val firefoxHeaders = Map("User-Agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val safariHeaders = Map("User-Agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_3) AppleWebKit/600.6.3 (KHTML, like Gecko) Version/8.0.6 Safari/600.6.3")

  val chrome = scenario("User")
    .exec(http("Homepage").get("/").headers(chromeHeaders))
    .exec(http("Select a product").get("/product/OLJCESPC7Z").headers(chromeHeaders))
    .exec(http("Add to Cart").post("cart").headers(chromeHeaders))
    .exec(http("View Cart").get("/checkout").headers(chromeHeaders))


  val firefox = scenario("User Life")
    .exec(http("Homepage").get("/").headers(firefoxHeaders))
    .exec(http("Select a product").get("/product/OLJCESPC7Z").headers(firefoxHeaders))
    .exec(http("Add to Cart").post("/cart").headers(firefoxHeaders))
    .exec(http("View Cart").get("/checkout").headers(firefoxHeaders))
   

  val safari = scenario("User Life Cycle")
    .exec(http("Homepage").get("/").headers(safariHeaders))
    .exec(http("Select a product").get("/product/OLJCESPC7Z").headers(safariHeaders))
    .exec(http("Add to Cart").post("/cart").headers(safariHeaders))
    .exec(http("View Cart").get("/checkout").headers(safariHeaders))
   

  setUp(
    chrome.inject(rampUsers(90)during(5)),
    firefox.inject(rampUsers(90)during (5)),
    safari.inject(rampUsers(90)during(5))
  ).protocols(httpProtocol)
}
