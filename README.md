# About

A math-solving program, built to solve many types of equations (complex, multi-variable, polynomial...) and to perfom various calculations (simplifications, deriatives...)

![screenshot1-2](https://user-images.githubusercontent.com/8051412/47298307-1be39f80-d620-11e8-95c7-241a957507c4.jpg)
![screenshot3-4](https://user-images.githubusercontent.com/8051412/47298312-1f772680-d620-11e8-8199-b9f73409eb62.jpg)


## Code Structure

### SYMBOL

A SYMBOL is a mathematical expression (equation is not a SYMBOL, as it consists of two mathematical expressions).
It may or may not contain other SYMBOL parameters.

Examples of symbol types- addition, multiplication, number, variable...

example of the symbol representation of mathematical expressions: 
- 4x + 3: addition(multiplication( number(4), variable(x) ), number(3))
- x^2 : power(variable(x), number(2))
- 34/2 : fraction(number(34), number(2))

- 4ax : multiplication(number(4), variable(a), variable(x))

- 2*(x+2) : multiplication(number(2), addition(variable(x),number(2))) 

SYMBOL is an abstract class, and different types of SYMBOLS are subclasses.

NOTE ABOUT SYMBOLS: A SYMBOL is by default a MUTABLE object. Some SYMBOLS may be defined as an IMMUTABLE object (number, variable...)

### STANDARIZATION

Usually, are many ways to represent the same mathematical expression:
- 4 + 2 + 4 : addition(addition(4,2), 4)
- 4 + 2 + 4: addition(4,2,4)
- 4 + 2 + 4: addition(4, addition(2,4))

we want to be able to represent a mathematical expression in a canonical form.
  
  
STANDARIZATION is the act of transforming a SYMBOL to its canonical form:

we define different methods of STANDARIZATION of the different SYMBOL types.

example: for addition, if a parameter is an addition, we "extract" the nested addition's parameters to the nesting addition's
parameter list:

addition(addition(4,2), 4) -> addition(4,2,4)

### SIMPLIFICATION

A SIMPLIFICATION of mathematical expression is the act of transforming it to another equal mathematical expression which is in an       *always-prefered* form.

example:
  
- 4 + 2 -> 6
- 4x*x -> 4x^2
- 3*(x/2) -> 3x/2

Other forms of manipulation, such as opening of a polynom:
  
(x+2)(x-1) -> x^2 + x -2
  
are considered specific operations, and not SIMPLIFICATIONS, because we may want to keep them in their current state.
  
For example:
  
in (x+2)(x+4)(x-1)=0, we do not want to open the multiplication, so it must not be considered as a SIMPLIFICATION.
