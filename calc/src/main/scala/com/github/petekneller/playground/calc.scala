package com.github.petekneller.playground

import scalaz.\/

package object calc {

  type CalcResult = String \/ Double
  type Calculator = String => CalcResult

}
