package com.github.petekneller.playground.calc.http

import com.github.petekneller.playground.calc.{Operations, AcceptanceTestFixture}

class LocalClusterTest extends AcceptanceTestFixture with HttpFixtures {

  acceptanceTests("a local cluster of calculators that together perform the basic arithmetic operations", (input: String) => {

    val additionServer = new OnlineCalculator(operators = Operations.addition :: Nil)
    withCalcHttp(additionServer) { additionPort =>

      val subtractionServer = new OnlineCalculator(operators = Operations.subtraction :: Nil)
      withCalcHttp(subtractionServer) { subtractionPort =>

        val multiplicationServer = new OnlineCalculator(operators = Operations.multiplication :: Nil)
        withCalcHttp(multiplicationServer) { multiplicationPort =>

          val divisionServer = new OnlineCalculator(operators = Operations.division :: Nil)
          withCalcHttp(divisionServer) { divisionPort =>

            val root = new OnlineCalculator(operators = List(
              "+" -> new OperationProxy("+", "localhost", additionPort),
              "-" -> new OperationProxy("-", "localhost", subtractionPort),
              "*" -> new OperationProxy("*", "localhost", multiplicationPort),
              "/" -> new OperationProxy("/", "localhost", divisionPort)
            ))

            withCalcHttp(root) { rootPort =>

              val client = testClient(rootPort)
              client(input)
            }
          }
        }
      }
    }
  })

}
