package net.golovach.rholp.operator

import java.util.Arrays.asList

import net.golovach.rholp.LexerAssertUtils._
import net.golovach.rholp.RhoTokenType._
import net.golovach.rholp.log.impl.DiagnosticCollector
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

class MinusFailSpec extends FlatSpec with Matchers  with OneInstancePerTest {

  val collector = new DiagnosticCollector

  "RhoLexer" should "return [ERROR, EOF] for \"-=\"" in {
    val tokens = tokenize("-=", collector)

    tokens shouldBe asList(ERROR.T("-="), EOF.T)
    verify(collector.getDiagnostics) eqTo
      error("lexer.err.operator.absent.compound-assignment")
        .row(1).col(1).len(2).offset(0)
  }

  "RhoLexer" should "return [ERROR, PAR, EOF] for \"->\"" in {
    val tokens = tokenize("->", collector)

    tokens shouldBe asList(ERROR.T("->"), EOF.T)
    verify(collector.getDiagnostics) eqTo
      error("lexer.err.operator.absent.arrow")
        .row(1).col(1).len(2).offset(0)
  }
}
