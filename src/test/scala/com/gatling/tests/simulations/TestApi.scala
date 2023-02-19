package com.gatling.tests.simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class TestApi extends Simulation {

 val httpconf = http.baseUrl("https://www.flipkart.com")
    .header("Accept", value = "application/json")
    .header("content-type", value = "application/json")

 val scn  = scenario("get user")
    .exec(http("get user request")
      .get("/api/users/2")
      .check(status is 200))

  setUp(scn.inject(atOnceUsers(1000))).protocols(httpconf)
}
