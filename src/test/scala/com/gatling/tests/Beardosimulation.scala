package com.gatling.tests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Beardosimulation extends Simulation {

	val httpProtocol = http
		.baseUrl("https://www.snapdeal.com/")
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.5")

	val chromeHeaders = Map("User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36")

	val chrome = scenario("User")
		.exec(http("Homepage").get("/").headers(chromeHeaders))
		.exec(http("Product categories").get("products/mens-tshirts-polos?sort=plrty").headers(chromeHeaders))
		.exec(http("Products details").get("/product/bewakoof-black-cotton-relaxed-fit/643393479629#bcrumbLabelId:3033").headers(chromeHeaders))
		.exec(http("Add to cart").get("/cart/addToCart?supc=SDL109711779&vendorCode=577f11&hardAdded=false").headers(chromeHeaders))
		.exec(http("Proceed to checkout").post("/proceedToCheckoutPage?cartId=e773ea39-b782-42dd-b3e2-c6fed60c0e30").headers(chromeHeaders))

	setUp(chrome.inject(atOnceUsers(100))).protocols(httpProtocol)
}