package net.golovach.rholp.comment

import java.util.Arrays.asList

import net.golovach.rholp.LexerAssertUtils.tokenize
import net.golovach.rholp.RhoTokenType.{DIV, EOF, IDENT}
import net.golovach.rholp.log.impl.DiagnosticCollector
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

class CommentLineSpec extends FlatSpec with Matchers  with OneInstancePerTest {

  val collector = new DiagnosticCollector

  "RhoLexer" should "return [EOF] for \"//\"" in {
    val tokens = tokenize("//", collector)

    tokens shouldBe asList(EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [EOF] for \"//a\"" in {
    val tokens = tokenize("//a", collector)

    tokens shouldBe asList(EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IDENT, EOF] for \"a//\"" in {
    val tokens = tokenize("a//", collector)

    tokens shouldBe asList(IDENT.T("a"), EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IDENT, EOF] for \"a//b\"" in {
    val tokens = tokenize("a//b", collector)

    tokens shouldBe asList(IDENT.T("a"), EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IDENT, EOF] for \"a//*b\"" in {
    val tokens = tokenize("a//*b", collector)

    tokens shouldBe asList(IDENT.T("a"), EOF.T)
    collector.getDiagnostics shouldBe empty
  }

  "RhoLexer" should "return [IDENT, IDENT, EOF] for \"a//b\\nc//d\\r\\n\"" in {
    val tokens = tokenize("a//b\nc//d\r\n", collector)

    tokens shouldBe asList(IDENT.T("a"), IDENT.T("c"), EOF.T)
    collector.getDiagnostics shouldBe empty
  }
}
