package net.golovach.rholp.keyword

import java.util.Arrays.asList

import net.golovach.rholp.LexerAssertUtils.tokenize
import net.golovach.rholp.RhoTokenType._
import net.golovach.rholp.log.impl.DiagnosticCollector
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

class Keyword_ByteArray_Spec extends FlatSpec with Matchers  with OneInstancePerTest {

  val collector = new DiagnosticCollector

  "RhoLexer" should "return [AND, EOF] for \"ByteArray\"" in {
    val tokens = tokenize("ByteArray", collector)

    tokens shouldBe asList(BYTE_ARRAY.T, EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IDENT, EOF] for \"ByteArray_\"" in {
    val tokens = tokenize("ByteArray_", collector)

    tokens shouldBe asList(IDENT.T("ByteArray_"), EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IDENT, EOF] for \"ByteArrayX\"" in {
    val tokens = tokenize("ByteArrayX", collector)

    tokens shouldBe asList(IDENT.T("ByteArrayX"), EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IDENT, EOF] for \"ByteArrayByteArray\"" in {
    val tokens = tokenize("ByteArrayByteArray", collector)

    tokens shouldBe asList(IDENT.T("ByteArrayByteArray"), EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IDENT, EOF] for \"ByteArray0\"" in {
    val tokens = tokenize("ByteArray0", collector)

    tokens shouldBe asList(IDENT.T("ByteArray0"), EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [AND, PLUS, EOF] for \"ByteArray+\"" in {
    val tokens = tokenize("ByteArray+", collector)

    tokens shouldBe asList(BYTE_ARRAY.T, PLUS.T, EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [AND, ERROR, EOF] for \"ByteArray$\"" in {
    val tokens = tokenize("ByteArray$", collector)

    tokens shouldBe asList(BYTE_ARRAY.T, ERROR.T("$"), EOF.T)
    collector.getDiagnostics should have size 1
  }
}
