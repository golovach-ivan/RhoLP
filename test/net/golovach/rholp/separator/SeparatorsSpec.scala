package net.golovach.rholp.separator

import java.util.Arrays.asList

import net.golovach.rholp.LexerAssertUtils._
import net.golovach.rholp.RhoTokenType._
import net.golovach.rholp.log.impl.DiagnosticCollector
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

class SeparatorsSpec extends FlatSpec with Matchers  with OneInstancePerTest {

  val collector = new DiagnosticCollector

  "RhoLexer" should "return [LPAREN, RPAREN, LBRACKET, RBRACKET, LBRACE, RBRACE, EOF] for \"()[]{}\"" in {
    val tokens = tokenize("()[]{}", collector)

    tokens shouldBe asList(
      LPAREN.T, RPAREN.T,
      LBRACKET.T, RBRACKET.T,
      LBRACE.T, RBRACE.T,
      EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [COMMA, SEMI, EOF] for \",;\"" in {
    val tokens = tokenize(",;", collector)

    tokens shouldBe asList(
      COMMA.T,
      SEMI.T,
      EOF.T)
    collector.getDiagnostics shouldBe empty
  }
}
