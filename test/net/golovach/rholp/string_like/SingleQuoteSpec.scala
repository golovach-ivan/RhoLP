package net.golovach.rholp.string_like

import java.util.Arrays.asList

import net.golovach.rholp.LexerAssertUtils._
import net.golovach.rholp.RhoTokenType.{EOF, ERROR, IDENT}
import net.golovach.rholp.log.impl.DiagnosticCollector
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

class SingleQuoteSpec extends FlatSpec with Matchers with OneInstancePerTest {

  val ERR_CODE_ABSENT_OPERATOR = "lexer.err.operator.absent.single-quote"

  val collector = new DiagnosticCollector

  "RhoLexer" should "return [ERROR, EOF] for \"'\"" in {
    val tokens = tokenize("'", collector)

    tokens shouldBe asList(
      ERROR.T("'"), EOF.T)
    verify(collector.getDiagnostics) eqTo
      error(ERR_CODE_ABSENT_OPERATOR)
        .row(1).col(1).len(1).offset(0)
  }

  "RhoLexer" should "return [IDENT, ERROR, EOF] for \"a'\"" in {
    val tokens = tokenize("a'", collector)

    tokens shouldBe asList(
      IDENT.T("a"), ERROR.T("'"), EOF.T)
    verify(collector.getDiagnostics) eqTo
      error(ERR_CODE_ABSENT_OPERATOR)
        .row(1).col(2).len(1).offset(1)
  }

  "RhoLexer" should "return [ERROR, IDENT, EOF] for \"'a\"" in {
    val tokens = tokenize("'a", collector)

    tokens shouldBe asList(
      ERROR.T("'"), IDENT.T("a"), EOF.T)
    verify(collector.getDiagnostics) eqTo
      error(ERR_CODE_ABSENT_OPERATOR)
        .row(1).col(1).len(1).offset(0)
  }

  "RhoLexer" should "return [IDENT, ERROR, IDENT, EOF] for \"a'b\"" in {
    val tokens = tokenize("a'b", collector)

    tokens shouldBe asList(
      IDENT.T("a"), ERROR.T("'"), IDENT.T("b"), EOF.T)
    verify(collector.getDiagnostics) eqTo
      error(ERR_CODE_ABSENT_OPERATOR)
        .row(1).col(2).len(1).offset(1)
  }

  "RhoLexer" should "return [IDENT, ERROR, IDENT, EOF] for \"a'b\\n\"" in {
    val tokens = tokenize("a'b\n", collector)

    tokens shouldBe asList(
      IDENT.T("a"), ERROR.T("'"), IDENT.T("b"), EOF.T)
    verify(collector.getDiagnostics) eqTo
      error(ERR_CODE_ABSENT_OPERATOR)
        .row(1).col(2).len(1).offset(1)
  }

  "RhoLexer" should "return [IDENT, ERROR, IDENT, EOF] for \"a'b\\r\"" in {
    val tokens = tokenize("a'b\r", collector)

    tokens shouldBe asList(
      IDENT.T("a"), ERROR.T("'"), IDENT.T("b"), EOF.T)
    verify(collector.getDiagnostics) eqTo
      error(ERR_CODE_ABSENT_OPERATOR)
        .row(1).col(2).len(1).offset(1)
  }

  "RhoLexer" should "return [IDENT, ERROR, IDENT, IDENT, ERROR, IDENT, EOF] for \"a'b\\nc'd\\r\\n\"" in {
    val tokens = tokenize("a'b\nc'd\r\n", collector)

    tokens shouldBe asList(
      IDENT.T("a"), ERROR.T("'"), IDENT.T("b"), IDENT.T("c"), ERROR.T("'"), IDENT.T("d"), EOF.T)

    verify(collector.getDiagnostics).eqTo(
      error(ERR_CODE_ABSENT_OPERATOR)
        .row(1).col(2).len(1).offset(1),
      error(ERR_CODE_ABSENT_OPERATOR)
        .row(2).col(2).len(1).offset(5)
    )
  }

  "RhoLexer" should "return [IDENT, ERROR, IDENT, IDENT, ERROR, IDENT, EOF] for \"a'b\\r\\nc'd\\n\"" in {
    val tokens = tokenize("a'b\r\nc'd\n", collector)

    tokens shouldBe asList(
      IDENT.T("a"), ERROR.T("'"), IDENT.T("b"), IDENT.T("c"), ERROR.T("'"), IDENT.T("d"), EOF.T)
    verify(collector.getDiagnostics).eqTo(
      error(ERR_CODE_ABSENT_OPERATOR)
        .row(1).col(2).len(1).offset(1),
      error(ERR_CODE_ABSENT_OPERATOR)
        .row(2).col(2).len(1).offset(6)
    )
  }
}






















