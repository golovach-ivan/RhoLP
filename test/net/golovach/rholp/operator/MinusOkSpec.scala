package net.golovach.rholp.operator

import java.util.Arrays.asList

import net.golovach.rholp.LexerAssertUtils.tokenize
import net.golovach.rholp.RhoTokenType._
import net.golovach.rholp.log.impl.DiagnosticCollector
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

class MinusOkSpec extends FlatSpec with Matchers  with OneInstancePerTest {

  val collector = new DiagnosticCollector

  "RhoLexer" should "return [MINUS, EOF] for \"-\"" in {
    val tokens = tokenize("-", collector)

    tokens shouldBe asList(MINUS.T, EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [MINUS_MINUS, EOF] for \"--\"" in {
    val tokens = tokenize("--", collector)

    tokens shouldBe asList(MINUS_MINUS.T, EOF.T)
    collector.getDiagnostics shouldBe empty
  }
}
