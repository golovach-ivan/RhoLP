package net.golovach.rholp.comment

import java.util.Arrays.asList

import net.golovach.rholp.LexerAssertUtils._
import net.golovach.rholp.RhoTokenType.{DIV, EOF, ERROR, IDENT}
import net.golovach.rholp.log.impl.DiagnosticCollector
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

class CommentBlockFailSpec extends FlatSpec with Matchers with OneInstancePerTest {

  val ERR_CODE_STRING_UNCLOSED = "lexer.err.comment.unclosed"

  val collector = new DiagnosticCollector

  "RhoLexer" should "return [ERROR, EOF] for \"/*\"" in {
    val tokens = tokenize("/*", collector)

    tokens shouldBe asList(ERROR.T("/*"), EOF.T)
    verify(collector.getDiagnostics) eqTo
      error(ERR_CODE_STRING_UNCLOSED)
        .row(1).col(1).len(2).offset(0)
  }

  "RhoLexer" should "return [IDENT, ERROR, EOF] for \"a/*\"" in {
    val tokens = tokenize("a/*", collector)

    tokens shouldBe asList(IDENT.T("a"), ERROR.T("/*"), EOF.T)
    verify(collector.getDiagnostics) eqTo
      error(ERR_CODE_STRING_UNCLOSED)
        .row(1).col(2).len(2).offset(1)
  }

  "RhoLexer" should "return [ERROR, EOF] for \"/**\"" in {
    val tokens = tokenize("/**", collector)

    tokens shouldBe asList(ERROR.T("/**"), EOF.T)
    verify(collector.getDiagnostics) eqTo
      error(ERR_CODE_STRING_UNCLOSED)
        .row(1).col(1).len(3).offset(0)
  }

  "RhoLexer" should "return [IDENT, ERROR, EOF] for \"a/**\"" in {
    val tokens = tokenize("a/**", collector)

    tokens shouldBe asList(IDENT.T("a"), ERROR.T("/**"), EOF.T)
    verify(collector.getDiagnostics) eqTo
      error(ERR_CODE_STRING_UNCLOSED)
        .row(1).col(2).len(3).offset(1)
  }

  "RhoLexer" should "return [IDENT, ERROR, IDENT, ERROR, EOF] for \"a/*b\\nc/*d\"" in {
    val tokens = tokenize("a/*b\nc/*d", collector)

    tokens shouldBe asList(
      IDENT.T("a"), ERROR.T("/*b"), IDENT.T("c"), ERROR.T("/*d"), EOF.T)
    verify(collector.getDiagnostics).eqTo(
      error(ERR_CODE_STRING_UNCLOSED)
        .row(1).col(2).len(3).offset(1),
      error(ERR_CODE_STRING_UNCLOSED)
        .row(2).col(2).len(3).offset(6)
    )
  }
}
