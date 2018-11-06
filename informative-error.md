## Informative error

- Lexer sceleton: Diagnostics API
  - Standard error format, error codes
  - Error/warn messages database
  - One scan - multiple diagnostic messages
- Non-existed literals handling
  - Int problems: too big integer literals, absent Hex/Binary format ('0xFF', '0b1010')
  - Floating-point literals: '42.42e-42f'
  - Char literals: 'A', '\uFFFF'
- Non-existed token types
  - Absent operators: '->', '%', '&', '&&', '^', etc
  - Absent keywords: 'do', 'int', 'this', etc
  - Absent UTF support

- General improvements
  - Diagnostics API: standart error format, error codes
  - Multiple error  
  - Советы о синтаксисе
  - Граммотный ответ на типичные ошибки: '+=', '&', '&&', '%'
- Non-existed literals
  - Integer problems: too big integer literals, absent HEX format, absent Binary format, Long postfix notation
  - Floating-point literals: \[-+\]?\[0-9\]\*\.?\[0-9\]*\[eE\]\[0-9\]*\[fFdD\]
  - Char literals, 'a', '\uFFFF'
  - String encodings: "\n\r\t\f"
- Non-existed token types
  - Absent operators: '->', '%', '&', '&&', '^', etc
  - Absent keywords: 'do', 'int', 'this', etc
  - Absent UTF support

### Absent operators
- ^, $, #, ?, %, & 
- &&, ||, >>, <<, >>>, ::
- ^^, \*\*, >>=, ::=, \\, \\\\, 
- <=, ->
- +=, -=, \*=, /=, &=, |=, ^=, %=, <<=, >>=, >>>=



### Absent keywords
- 2: as, of, do, eq
- 3: url, div, mul, add, sub, pow, mod, neq, xml, def, let, end, Map
- 4: this, self, json, type, byte, char, case, loop, send, list, long
- 5: class, throw, super, until, yield, where, begin, break, short, float, array, while
- 6: object, public, throws, module, scheme, import, double, return, forall 
- 7: private, default, integer, defined, newtype, receive, extends, boolean 
- 8: function, property, continue
- 9: protected
- 0: instanceof, implements  

- "val xs = 'A' :: 'B' :: 'C' :: Nil"
- "while (@x > 0) {x++}"
- "do {x++} while (x < 42)"


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

### Misused operators: PARSER
=> , !, %%
