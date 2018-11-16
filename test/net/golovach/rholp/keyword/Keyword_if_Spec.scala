package net.golovach.rholp.keyword

import java.util.Arrays.asList

import net.golovach.rholp.LexerAssertUtils.tokenize
import net.golovach.rholp.RhoTokenType._
import net.golovach.rholp.log.impl.DiagnosticCollector
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

class Keyword_if_Spec extends FlatSpec with Matchers  with OneInstancePerTest {

  val collector = new DiagnosticCollector

  "RhoLexer" should "return [IF, EOF] for \"if\"" in {
    val tokens = tokenize("if", collector)

    tokens shouldBe asList(IF.T, EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IDENT, EOF] for \"if_\"" in {
    val tokens = tokenize("if_", collector)

    tokens shouldBe asList(IDENT.T("if_"), EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IDENT, EOF] for \"ifX\"" in {
    val tokens = tokenize("ifX", collector)

    tokens shouldBe asList(IDENT.T("ifX"), EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IDENT, EOF] for \"ifif\"" in {
    val tokens = tokenize("ifif", collector)

    tokens shouldBe asList(IDENT.T("ifif"), EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IDENT, EOF] for \"if0\"" in {
    val tokens = tokenize("if0", collector)

    tokens shouldBe asList(IDENT.T("if0"), EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IF, PLUS, EOF] for \"if+\"" in {
    val tokens = tokenize("if+", collector)

    tokens shouldBe asList(IF.T, PLUS.T, EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IF, ERROR, EOF] for \"if$\"" in {
    val tokens = tokenize("if$", collector)

    tokens shouldBe asList(IF.T, ERROR.T("$"), EOF.T)
    collector.getDiagnostics should have size 1
  }
}
