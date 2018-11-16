package net.golovach.rholp.operator

import java.util.Arrays.asList

import net.golovach.rholp.LexerAssertUtils.tokenize
import net.golovach.rholp.RhoTokenType._
import net.golovach.rholp.log.impl.DiagnosticCollector
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

class GtOkSpec extends FlatSpec with Matchers  with OneInstancePerTest {

  val collector = new DiagnosticCollector

  "RhoLexer" should "return [GT, EOF] for \">\"" in {
    val tokens = tokenize(">", collector)

    tokens shouldBe asList(GT.T, EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [GT_EQ, EOF] for \">=\"" in {
    val tokens = tokenize(">=", collector)

    tokens shouldBe asList(GT_EQ.T, EOF.T)
    collector.getDiagnostics shouldBe empty
  }
}
