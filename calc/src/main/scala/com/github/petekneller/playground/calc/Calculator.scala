package com.github.petekneller.playground.calc

import scala.util.parsing.combinator.RegexParsers
import scalaz.\/._
import scalaz.std.list._
import scalaz.std.option.optionSyntax._
import scalaz.syntax.traverse._
import scalaz.{-\/, \/}

object Calculator {

  type EitherApplicative[x] = String \/ x

  import Operations._
  val defaultOperations = List(
    addition,
    subtraction,
    multiplication,
    division
  )

  def run(input: String, operations: List[OperatorBinding] = defaultOperations): Result = {

    sealed trait AST
    case class Lit(literal: String) extends AST
    case class Expr(operation: String, operands: List[AST]) extends AST

    object parsers extends RegexParsers {
      def literal: Parser[AST] = """([^\s()]+)""".r ^^ { l => Lit(l) }
      def operator: Parser[String] = """([\S]+)""".r
      def operand: Parser[AST] = validInput
      def expression: Parser[AST] = "(" ~> operator ~ (operand *) <~ ")" ^^ { case operator ~ operands => Expr(operator, operands) }

      def validInput: Parser[AST] = literal | expression
    }

    def parseLiteral(input: String): Result = \/.fromTryCatchNonFatal(input.toDouble).leftMap(t => s"Invalid literal: '$input' is not a floating point number; full error follows: \n ${t.toString}")

    def processExpression(expr: AST): Result = {
      expr match {
        case Lit(literal) =>
          parseLiteral(literal)
        case Expr(operator, operands) =>
          for {
            parsedOperands <- operands.
              map(processExpression).
              sequence[EitherApplicative, Double]
            operatorFn <- operations.toMap.get(operator).toRightDisjunction(s"Invalid operation: '$operator' not recognised")
            result <- operatorFn(parsedOperands)
          } yield result
      }
    }

    import parsers._
    parse(validInput, input) match {
      case Failure(msg, _) => -\/(msg)
      case Success(expr, _) => processExpression(expr)
    }
  }
}
