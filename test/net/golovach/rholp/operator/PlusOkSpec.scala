package net.golovach.rholp.operator

import java.util.Arrays.asList

import net.golovach.rholp.LexerAssertUtils.tokenize
import net.golovach.rholp.RhoTokenType._
import net.golovach.rholp.log.impl.DiagnosticCollector
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

class PlusOkSpec extends FlatSpec with Matchers  with OneInstancePerTest {

  val collector = new DiagnosticCollector

  "RhoLexer" should "return [PLUS, EOF] for \"+\"" in {
    val tokens = tokenize("+", collector)

    tokens shouldBe asList(PLUS.T, EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [PLUS_PLUS, EOF] for \"++\"" in {
    val tokens = tokenize("++", collector)

    tokens shouldBe asList(PLUS_PLUS.T, EOF.T)
    collector.getDiagnostics shouldBe empty
  }
}
