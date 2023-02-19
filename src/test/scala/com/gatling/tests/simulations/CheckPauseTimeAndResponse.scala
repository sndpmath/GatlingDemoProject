package com.gatling.tests.simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class CheckPauseTimeAndResponse extends Simulation{

  val httpconf = http.baseUrl("http://reqres.in")
    .header("Accept", "application/json")

  val scn = scenario("user api calls")
    .exec(http("list all users ")
    .get("/api/users?page=2")
      .check(status.is(200)))
    .pause(5)

    .exec(http("single user api")
    .get("/api/users/2")
    .check(status.in(200 to 210)))
    .pause(1,10)
    .exec(http("single user not found api")
    .get("/api/users/23")
    .check(status.not(400), status.not(500)))
    .pause(3000.milliseconds)

  setUp(
    scn.inject(
      nothingFor(5 seconds),
      constantUsersPerSec(100) during (1 seconds),
      rampUsersPerSec(1) to (500) during (2 seconds)
    ).protocols(httpconf))
}

