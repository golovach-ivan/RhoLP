**java -jar RhoLP.jar hello.rho**

```
ERROR
  Error code: lexer.err.literal.absent.floating-point
  Message:    there are no floating-point types
  Line/Column: [2, 14]
  ----------
  chan!([42L, 42.42e-42f, 9999999999999999999])
              ^^^^^^^^^^
ERROR
  Error code: lexer.err.literal.absent.int-L-suffix
  Message:    there is no '''l''' or '''L''' at the ent of Int literals
  Line/Column: [2, 9]
  ----------
  chan!([42L, 42.42e-42f, 9999999999999999999])
         ^^^
ERROR
  Error code: lexer.err.literal.int-too-big
  Message:    too big Int literal, should be in range [-9223372036854775808, 922
3372036854775807]
  Line/Column: [2, 26]
  ----------
  chan!([42L, 42.42e-42f, 9999999999999999999])
                          ^^^^^^^^^^^^^^^^^^^
```
