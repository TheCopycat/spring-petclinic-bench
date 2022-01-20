package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt
import scala.util.Random

class PetClinicSimulation extends Simulation{
  val random = new Random()
  val httpProtocol = http.baseUrl("http://localhost:8080")

  val viewOwnerScenario = scenario("View Owner")
    .exec(http("get /").get("/"))
    .pause(1)
    .exec(http("get /api/customer/owners")
      .get("/api/customer/owners")
      .check(jsonPath("$[*].id").findRandom.saveAs("ownerId")))
    .exec(http("get /api/gateway/owners/ownerid").get("/api/gateway/owners/${ownerId}"))
    .pause(1)
    .exec(http("get /api/vet/vets").get("/api/vet/vets"))

  val editPetTypeScenario = scenario("Edit pet type")
    .exec(http("get /").get("/"))
    .pause(1)
    .exec(http("get /api/customer/owners")
      .get("/api/customer/owners")
      .check(jsonPath("$[*].id").findRandom.saveAs("ownerId")))
    .pause(1)
    .exec(http("get /api/gateway/owners/ownedId").get("/api/gateway/owners/${ownerId}")
      .check(jsonPath("$.pets[*].id").findRandom.saveAs("petId")))
    .pause(1)
    .exec(http("get /api/customer/owners/ownedId/pets/petId").get("/api/customer/owners/${ownerId}/pets/${petId}")
      .check(jsonPath("$.name").exists.saveAs("petName"),
        jsonPath("$.birthDate").exists.saveAs("petBirthDate")))
    .exec(http("get /api/customer/petTypes").get("/api/customer/petTypes")
      .check(jsonPath("$[*].id").findRandom.saveAs("petTypeId")))
    .exec(http("PUT /api/customer/owners/ownerId/pets/petId").put("/api/customer/owners/${ownerId}/pets/${petId}")
      .body(ElFileBody("pet.json.tpl")).asJson)
    .exec(http("get /api/customer/owners").get("/api/customer/owners"))

  setUp(viewOwnerScenario.inject(constantUsersPerSec(2) during(900.seconds)),
    editPetTypeScenario.inject(constantUsersPerSec(1) during(900.seconds))).protocols(httpProtocol)
}
