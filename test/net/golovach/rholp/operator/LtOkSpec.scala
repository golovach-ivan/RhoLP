package net.golovach.rholp.operator

import java.util.Arrays.asList

import net.golovach.rholp.LexerAssertUtils.tokenize
import net.golovach.rholp.RhoTokenType._
import net.golovach.rholp.log.impl.DiagnosticCollector
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

class LtOkSpec extends FlatSpec with Matchers  with OneInstancePerTest {

  val collector = new DiagnosticCollector

  "RhoLexer" should "return [LT, EOF] for \"<\"" in {
    val tokens = tokenize("<", collector)

    tokens shouldBe asList(LT.T, EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [BACK_ARROW, EOF] for \"<=\"" in {
    val tokens = tokenize("<=", collector)

    tokens shouldBe asList(BACK_ARROW.T, EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [BIND_LINEAR, EOF] for \"<-\"" in {
    val tokens = tokenize("<-", collector)

    tokens shouldBe asList(BIND_LINEAR.T, EOF.T)
    collector.getDiagnostics shouldBe empty
  }
}
