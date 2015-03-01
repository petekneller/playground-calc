package com.github.petekneller.playground.calc

import scala.util.parsing.combinator.RegexParsers
import scalaz.{-\/, Applicative, \/}
import scalaz.std.list._
import scalaz.std.option.optionSyntax._
import scalaz.syntax.traverse._

import \/._

object Calculator {

  type EitherAppl[x] = String \/ x
  type CalcResult = String \/ Double

  def run(input: String): String \/ Double = {

    sealed trait AST
    case class Lit(literal: String) extends AST
    case class Expr(operation: String, operands: List[AST]) extends AST

    object parsers extends RegexParsers {
      def literal: Parser[AST] = """([^\s()]+)""".r ^^ { l => Lit(l) }
      def operator: Parser[String] = """([\S]+)""".r
      def operand: Parser[AST] = literal
      def expression: Parser[AST] = "(" ~> operator ~ (operand *) <~ ")" ^^ { case operator ~ operands => Expr(operator, operands) }

      def validInput: Parser[AST] = literal | expression
    }

    def parseLiteral(input: String): CalcResult = \/.fromTryCatchNonFatal(input.toDouble).leftMap(t => s"Invalid literal: '$input' is not a floating point number; full error follows: \n ${t.toString}")

    val operations = Map[String, List[AST] => CalcResult](
      "+" -> { operands =>
        operands.
          map{ case Lit(lit) => parseLiteral(lit) }.
          sequence[EitherAppl, Double].
          map(_.fold(0: Double){ case (acc, m) => acc + m })
      },
      "-" -> { operands =>
        operands.
          map{ case Lit(lit) => parseLiteral(lit) }.
          sequence[EitherAppl, Double].
          map(_.reduceLeft[Double]{ case (acc, m) => acc - m })
      },
      "*" -> { operands =>
        operands.
          map{ case Lit(lit) => parseLiteral(lit) }.
          sequence[EitherAppl, Double].
          map(_.reduceLeft[Double]{ case (acc, m) => acc * m })
      },
      "/" -> { operands =>
        operands.
          map{ case Lit(lit) => parseLiteral(lit) }.
          sequence[EitherAppl, Double].
          map(_.reduceLeft[Double]{ case (acc, m) => acc / m })
      }
    )

    import parsers._
    parse(validInput, input) match {
      case Failure(msg, _) =>
        -\/(msg)
      case Success(Lit(literal), _) =>
        parseLiteral(literal)
      case Success(Expr(operator, operands), _) =>
        operations.get(operator).toRightDisjunction(s"Invalid operation: '$operator' not recognised").flatMap(op => op(operands))
    }
  }
}
