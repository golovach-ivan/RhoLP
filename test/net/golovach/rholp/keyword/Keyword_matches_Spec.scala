package net.golovach.rholp.keyword

import java.util.Arrays.asList

import net.golovach.rholp.LexerAssertUtils.tokenize
import net.golovach.rholp.RhoTokenType._
import net.golovach.rholp.log.impl.DiagnosticCollector
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

class Keyword_matches_Spec extends FlatSpec with Matchers  with OneInstancePerTest {

  val collector = new DiagnosticCollector

  "RhoLexer" should "return [MATCHES, EOF] for \"matches\"" in {
    val tokens = tokenize("matches", collector)

    tokens shouldBe asList(MATCHES.T, EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IDENT, EOF] for \"matches_\"" in {
    val tokens = tokenize("matches_", collector)

    tokens shouldBe asList(IDENT.T("matches_"), EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IDENT, EOF] for \"matchesX\"" in {
    val tokens = tokenize("matchesX", collector)

    tokens shouldBe asList(IDENT.T("matchesX"), EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IDENT, EOF] for \"matchesmatches\"" in {
    val tokens = tokenize("matchesmatches", collector)

    tokens shouldBe asList(IDENT.T("matchesmatches"), EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IDENT, EOF] for \"matches0\"" in {
    val tokens = tokenize("matches0", collector)

    tokens shouldBe asList(IDENT.T("matches0"), EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [MATCHES, PLUS, EOF] for \"matches+\"" in {
    val tokens = tokenize("matches+", collector)

    tokens shouldBe asList(MATCHES.T, PLUS.T, EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [MATCHES, ERROR, EOF] for \"matches$\"" in {
    val tokens = tokenize("matches$", collector)

    tokens shouldBe asList(MATCHES.T, ERROR.T("$"), EOF.T)
    collector.getDiagnostics should have size 1
  }
}
