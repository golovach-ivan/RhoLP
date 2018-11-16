package net.golovach.rholp.keyword

import java.util.Arrays.asList

import net.golovach.rholp.LexerAssertUtils.tokenize
import net.golovach.rholp.RhoTokenType._
import net.golovach.rholp.log.impl.DiagnosticCollector
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

class Keyword_for_Spec extends FlatSpec with Matchers  with OneInstancePerTest {

  val collector = new DiagnosticCollector

  "RhoLexer" should "return [FOR, EOF] for \"for\"" in {
    val tokens = tokenize("for", collector)

    tokens shouldBe asList(FOR.T, EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IDENT, EOF] for \"for_\"" in {
    val tokens = tokenize("for_", collector)

    tokens shouldBe asList(IDENT.T("for_"), EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IDENT, EOF] for \"forX\"" in {
    val tokens = tokenize("forX", collector)

    tokens shouldBe asList(IDENT.T("forX"), EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IDENT, EOF] for \"forfor\"" in {
    val tokens = tokenize("forfor", collector)

    tokens shouldBe asList(IDENT.T("forfor"), EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IDENT, EOF] for \"for0\"" in {
    val tokens = tokenize("for0", collector)

    tokens shouldBe asList(IDENT.T("for0"), EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [FOR, PLUS, EOF] for \"for+\"" in {
    val tokens = tokenize("for+", collector)

    tokens shouldBe asList(FOR.T, PLUS.T, EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [FOR, ERROR, EOF] for \"for$\"" in {
    val tokens = tokenize("for$", collector)

    tokens shouldBe asList(FOR.T, ERROR.T("$"), EOF.T)
    collector.getDiagnostics should have size 1
  }
}
