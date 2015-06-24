package com.github.petekneller.playground.calc

class ConfigurationTest extends ConfigurationTestFixture {

  configurationTests("A Polish notation calculator", (ops: List[OperatorBinding]) => Calculator.run(_, ops))

}
