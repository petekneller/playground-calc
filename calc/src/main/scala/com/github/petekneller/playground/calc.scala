package com.github.petekneller.playground

import scalaz.\/

package object calc {

  type Result = String \/ Double
  type Calculator = String => Result

}
