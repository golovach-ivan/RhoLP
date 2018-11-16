package net.golovach.rholp.operator

import java.util.Arrays.asList

import net.golovach.rholp.LexerAssertUtils.tokenize
import net.golovach.rholp.RhoTokenType._
import net.golovach.rholp.log.impl.DiagnosticCollector
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

class BarOkSpec extends FlatSpec with Matchers  with OneInstancePerTest {

  val collector = new DiagnosticCollector

  "RhoLexer" should "return [WILDCARD, EOF] for \"_\"" in {
    val tokens = tokenize("_", collector)

    tokens shouldBe asList(WILDCARD.T, EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IDENT, EOF] for \"__\"" in {
    val tokens = tokenize("__", collector)

    tokens shouldBe asList(IDENT.T("__"), EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IDENT, EOF] for \"_a\"" in {
    val tokens = tokenize("_a", collector)

    tokens shouldBe asList(IDENT.T("_a"), EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IDENT, EOF] for \"a_\"" in {
    val tokens = tokenize("a_", collector)

    tokens shouldBe asList(IDENT.T("a_"), EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IDENT, EOF] for \"a_b\"" in {
    val tokens = tokenize("a_b", collector)

    tokens shouldBe asList(IDENT.T("a_b"), EOF.T)
    collector.getDiagnostics shouldBe empty
  }
}
