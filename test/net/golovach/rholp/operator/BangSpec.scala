package net.golovach.rholp.operator

import java.util.Arrays.asList

import net.golovach.rholp.LexerAssertUtils.tokenize
import net.golovach.rholp.RhoTokenType._
import net.golovach.rholp.log.impl.DiagnosticCollector
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

class BangSpec extends FlatSpec with Matchers  with OneInstancePerTest {

  val collector = new DiagnosticCollector

  "RhoLexer" should "return [SEND_SINGLE, EOF] for \"!\"" in {
    val tokens = tokenize("!", collector)

    tokens shouldBe asList(SEND_SINGLE.T, EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [SEND_MULTIPLE, EOF] for \"!!\"" in {
    val tokens = tokenize("!!", collector)

    tokens shouldBe asList(SEND_MULTIPLE.T, EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [NOT_EQ, EOF] for \"!=\"" in {
    val tokens = tokenize("!=", collector)

    tokens shouldBe asList(NOT_EQ.T, EOF.T)
    collector.getDiagnostics shouldBe empty
  }
}
