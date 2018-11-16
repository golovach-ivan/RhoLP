package net.golovach.rholp.log.impl;

import net.golovach.rholp.log.Diagnostic;
import net.golovach.rholp.log.DiagnosticListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.synchronizedList;

/**
 * Provides an easy way to collect diagnostics in a list.
 *
 * <p> Variation of {@link javax.tools.DiagnosticCollector}.
 */
public class DiagnosticCollector implements DiagnosticListener {
    private List<Diagnostic> diagnostics =
            synchronizedList(new ArrayList<Diagnostic>());

    public void report(Diagnostic diagnostic) {
        if (diagnostic == null) {
            throw new IllegalArgumentException("diagnostic == null");
        }
        diagnostics.add(diagnostic);
    }

    /**
     * Gets a list view of diagnostics collected by this object.
     *
     * @return a list view of diagnostics
     */
    public List<Diagnostic> getDiagnostics() {
        return new ArrayList<>(diagnostics);
    }

    @Override
    public void eof() {
        // NOP
    }
}
