package net.golovach.rholp.operator

import java.util.Arrays.asList

import net.golovach.rholp.LexerAssertUtils.error
import net.golovach.rholp.LexerAssertUtils.{tokenize, verify}
import net.golovach.rholp.RhoTokenType._
import net.golovach.rholp.log.impl.DiagnosticCollector
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

class BarFailSpec extends FlatSpec with Matchers  with OneInstancePerTest {

  val ERR_CODE_ABSENT_OPERATOR = "lexer.err.operator.absent.logic"

  val collector = new DiagnosticCollector

  "RhoLexer" should "return [ERROR, EOF] for \"||\"" in {
    val tokens = tokenize("||", collector)

    tokens shouldBe asList(ERROR.T("||"), EOF.T)
    verify(collector.getDiagnostics) eqTo
      error(ERR_CODE_ABSENT_OPERATOR)
        .row(1).col(1).len(2).offset(0)
  }

  "RhoLexer" should "return [ERROR, PAR, EOF] for \"|||\"" in {
    val tokens = tokenize("|||", collector)

    tokens shouldBe asList(ERROR.T("||"), PAR.T, EOF.T)
    verify(collector.getDiagnostics) eqTo
      error(ERR_CODE_ABSENT_OPERATOR)
        .row(1).col(1).len(2).offset(0)
  }
}
