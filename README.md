# Tokenization Example #


NEXT TIME: stmt, then program, then exp

Concrete grammar:

```
primaryExp ::= IDENTIFIER | INTEGER | `(` exp `)`
multExp ::= primaryExp ((`*` | `/`) primaryExp)*
addExp ::= multExp ((`+` | `-`) multExp)*
exp ::= addExp
stmt ::= IDENTIFIER `=` exp `;` |
         `print` exp `;`
program ::= stmt*
```

Abstract grammar:

```
exp ::= IDENTIFIER | INTEGER | exp op exp
op ::= `+` | `-` | `*` | `/`
stmt ::= IDENTIFIER `=` exp `;` |
         `print` exp `;`
program ::= stmt*
```

## Tokens ##

- `IDENTIFIER(String)`
- `INTEGER(int)`
- `LPAREN`
- `RPAREN`
- `PLUS`
- `MINUS`
- `STAR`
- `DIV`
- `EQUALS`
- `SEMICOLON`
- `PRINT`

## Examples ##

```
x = 123;
print x;
```

```
x = 2 * 7;
y = x + (x * 5);
print x + y;
```

### For Monday ###

- Finish Java token representation
- Show token representation for language with pattern matching
