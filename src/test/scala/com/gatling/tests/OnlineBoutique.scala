package com.gatling.tests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class OnlineBoutique extends Simulation {

	val httpProtocol = http
		.baseUrl("https://onlineboutique.dev")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.5")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/109.0")

	val home = exec(http("Home Page")
		.get("/"))
		.pause(8)

	val order = exec(http("Product")
		.get("/product/OLJCESPC7Z"))
		.pause(2)
		.exec(http("Cart")
			.post("/cart")
			.formParam("product_id", "OLJCESPC7Z")
			.formParam("quantity", "1"))
		.pause(1)
		.exec(http("Home Page")
			.get("/"))

	val scn = scenario("OnlineBoutique").exec(home, order)

			setUp(
				scn.inject(rampUsers(100).during(10))
			).protocols(httpProtocol)

}