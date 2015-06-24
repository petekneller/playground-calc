package com.github.petekneller.playground.calc

class AcceptanceTest extends AcceptanceTestFixture {

  acceptanceTests("A Polish notation calculator", Calculator.run(_))

}
