package com.gatling.tests

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.scenario.Simulation

class Baeldung extends Simulation {

  val httpProtocol = http.baseUrl("https://www.baeldung.com")

  val scn = scenario("RestSimulation")
    .exec(http("home").get("/"))
    .pause(1)
    .exec(http("article_1").get("/spring-boot"))
    .pause(2)
    .exec(http("rest_series").get("/spring-boot-start"))
    .pause(3)
    .exec(http("rest_category").get("/learn-spring-course"))
    .pause(4)
    .exec(http("article_2").get("/spring-boot"))

  setUp(scn.inject(atOnceUsers(100))).protocols(httpProtocol)
}
