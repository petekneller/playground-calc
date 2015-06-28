package com.github.petekneller.playground.calc.utilities

import java.io.{FileWriter, BufferedWriter}

object Excel {

  type TabularData[A] = Seq[Seq[A]]

  def toSv[A](values: TabularData[A], sep: String = "\t"): String = {
    values.map(_.mkString(sep)).mkString(System.lineSeparator)
  }

  def write(data: String, filename: String) = {
    val out = new BufferedWriter(new FileWriter(filename))
    try {
      out.write(data)
    } finally {
      out.flush()
      out.close()
    }
  }

}
