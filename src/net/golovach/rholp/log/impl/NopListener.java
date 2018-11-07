package net.golovach.rholp.log.impl;

import net.golovach.rholp.log.Diagnostic;
import net.golovach.rholp.log.DiagnosticListener;

public class NopListener implements DiagnosticListener {

    @Override
    public void report(Diagnostic diagnostic) {
        /*NOP*/
    }

    @Override
    public void eof() {
        /*NOP*/
    }
}
