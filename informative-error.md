## Informative error

### Too big integer literals

### Long literals

### Floating-point literals
Detect as error any varian of [Java-style floating-point literals](https://docs.oracle.com/javase/specs/jls/se11/html/jls-3.html#jls-3.10.2) except [HexadecimalFloatingPointLiteral](https://docs.oracle.com/javase/specs/jls/se11/html/jls-3.html#jls-HexadecimalFloatingPointLiteral).
- "1f", "1F", "1d", "1D"
- "1.1", "1.1f", "1.1F", "1.1d", "1.1D"
- "1.", "1.f", "1.F", "1.d", "1.D"
- ".1", ".1f", ".1F", ".1d", ".1D"
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

### UTF identifiers + $

### Absent operators
%, #, >>, <<, /=, += ... 

### Absent keywords
'class', 'object', 'function', 'property', 'private', 'public', 'protecfed', 'default', 'constructor', 'this', 'self',    
int, byte, string, short, float, double, char, Char, array, return

### Keywords normalization (typo error recovery)
null -> Nil, 

### Char literals
'AAA'
