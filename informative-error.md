## Informative error

- Non-existed literals
  - Integer problems: too big integer literals, absent HEX format, absent Binary format, Long postfix notation
  - Floating-point literals: \[-+\]?\[0-9\]\*\.?\[0-9\]*\[eE\]\[0-9\]*\[fFdD\]
  - Char literals, 'a', '\uFFFF'
  - String encodings: "\n\r\t\f"
- Absent analisys
  - Absent operators
  - Absent keywords
  - Absent UTF support: identifier (пиво, рыба), strings ("aa\uFFFFbb")
- Diagnostics API
  - standart error format, 

### Absent operators
- anithing with: ^, $, #, ?
- incorrect: 
  - %  
  - >>, >>>, <<, <<<, ::, &&, ||, 
  - <-, <=
  - /=, +=, *=, -= 

### Absent keywords
- 2: as, of, in, do, eq, or
- 3: div, mul, add, sub, pow, mod, neq xml, int, def, let, end, not
- 4: this, self, json, type, byte, char, case, loop, send
- 5: class, throw, super, until, yield, where, begin, break, short, float, array, while
- 6: object, public, throws, module, scheme, import, string, double, return, forall 
- 7: private, default, integerб defined, newtype, receive
- 8: function, property, continue
- 9: protected






### Keywords normalization (typo error recovery)
null -> Nil, 

### Char literals
'AAA'

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
