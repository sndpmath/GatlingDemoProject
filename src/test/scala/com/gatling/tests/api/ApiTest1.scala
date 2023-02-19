package com.gatling.tests.api

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class ApiTest1 extends Simulation {

  val httpProtocol = http.baseUrl("https://reqres.in/api/users/")

  val scn = scenario("Get API Request Demo")
    .exec(
      http("Get Single User")
        .get("/api/login")
        .check(
          status.is(200),
       jsonPath("$.\"email\": \"eve.holt@reqres.in\",").is("    \"password\": \"cityslicka\""))
    )
    .pause(1)
  setUp(
    scn.inject(atOnceUsers(10),
      rampUsers(16)during(5),
    constantUsersPerSec(8)during(3))
      .protocols(httpProtocol)
  )
}
