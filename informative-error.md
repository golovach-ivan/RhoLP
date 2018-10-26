## Informative error

### Too big integer literals

### Long literals

### Floating-point literals
Detect as error any varian of [Java-style floating-point literals](https://docs.oracle.com/javase/specs/jls/se11/html/jls-3.html#jls-3.10.2) except [HexadecimalFloatingPointLiteral](https://docs.oracle.com/javase/specs/jls/se11/html/jls-3.html#jls-HexadecimalFloatingPointLiteral).
- "0f", "0F", "0d", "0D"
- "0.0", "0.0f", "0.0F", "0.0d", "0.0D"
- "1.", "1.f", "1.F", "1.d", "1.D"
- "42.", "42.f", "42.F", "42.d", "42.D"
- "1e1", "1e1f", "1e1F", "1e1d", "1e1D"
- "1e+1", "1e+1f", "1e+1F", "1e+1d", "1e+1D"
- "1e-1", "1e-1f", "1e-1F", "1e-1d", "1e-1D"
- "1.1e1", "1.1e1f", "1.1e1F", "1.1e1d", "1.1e1D"
- "1.1e+1", "1.1e+1f", "1.1e+1F", "1.1e+1d", "1.1e+1D"
- "1.1e-1", "1.1e-1f", "1.1e-1F", "1.1e-1d", "1.1e-1D"
- ".1e1", ".1e1f", ".1e1F", ".1e1d", ".1e1D"
- ".1e+1", ".1e+1f", ".1e+1F", ".1e+1d", ".1e+1D"
- ".1e-1", ".1e-1f", ".1e-1F", ".1e-1d", ".1e-1D"
- "1.e1", "1.e1f", "1.e1F", "1.e1d", "1.e1D"
- "1.e+1", "1.e+1f", "1.e+1F", "1.e+1d", "1.e+1D"
- "1.e-1", "1.e-1f", "1.e-1F", "1.e-1d", "1.e-1D"
