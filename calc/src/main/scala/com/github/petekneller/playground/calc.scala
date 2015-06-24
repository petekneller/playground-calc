package com.github.petekneller.playground

import scalaz.\/

package object calc {

  type Result = String \/ Double
  type Calculator = String => Result

  type OperatorBinding = (String, List[Double] => Result)

}
