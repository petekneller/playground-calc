package com.github.petekneller.playground.calc.analysis

import com.github.petekneller.playground.calc.Calculator
import com.github.petekneller.playground.calc.utilities.Excel.TabularData
import com.github.petekneller.playground.calc.utilities.{ElapsedTimeAnnotation, Statistics}
import com.github.petekneller.playground.calc.utilities.Statistics.Results
import com.github.petekneller.playground.calc.utilities.Excel._

object Api {

  def trials(n: Int): TabularData[String] = {
    val calc = ElapsedTimeAnnotation(Calculator.run(_))
    (0 until n).
      map(_ => calc("(+ 1 2 3)")).
      map( _._2.toNanos.toString ).
      map(_ :: Nil)
  }

  def statistics(n: Int): Results = Statistics(n, Calculator.run(_))

  def spit(filename: String, data: TabularData[String]): Unit = write(toSv(data), filename)

}
