package com.github.petekneller.playground.calc

import scala.util.parsing.combinator.RegexParsers
import scalaz.{-\/, Applicative, \/}
import scalaz.std.list._
import scalaz.syntax.traverse._
import \/._

object Calculator {

  type EitherAppl[x] = String \/ x

  def run(input: String): String \/ Double = {

    sealed trait AST
    case class Lit(literal: String) extends AST
    case class Op(operation: String, operands: List[AST]) extends AST

    object parsers extends RegexParsers {
      def literal: Parser[AST] = """([^\s()]+)""".r ^^ { l => Lit(l) }
      def operator: Parser[String] = """([\w+]+)""".r
      def operand: Parser[AST] = literal
      def operation: Parser[AST] = "(" ~> operator ~ (operand *) <~ ")" ^^ { case operator ~ operands => Op(operator, operands) }

      def validInput: Parser[AST] = literal | operation
    }

    def parseLiteral(input: String): String \/ Double = \/.fromTryCatchNonFatal(input.toDouble).leftMap(t => s"Invalid literal: '$input' is not a floating point number; full error follows: \n ${t.toString}")

    import parsers._
    parse(validInput, input) match {
      case Failure(msg, _) =>
        -\/(msg)
      case Success(Lit(literal), _) =>
        parseLiteral(literal)
      case Success(Op(operator, operands), _) =>
        operator match {
          case "+" =>
            operands.
              map{ case Lit(lit) => parseLiteral(lit) }.
              sequence[EitherAppl, Double].
              map(_.fold(0: Double){ case (acc, m) => acc + m })
        }
    }
  }
}
