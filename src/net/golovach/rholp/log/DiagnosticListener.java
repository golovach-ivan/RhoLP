package net.golovach.rholp.log;

/**
 * Interface for receiving diagnostics from tools.
 *
 * <p> Variation of {@link javax.tools.DiagnosticListener}.
 */
public interface DiagnosticListener {

    void report(Diagnostic diagnostic);

    void eof();
}

