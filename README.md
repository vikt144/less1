# less1
create a simple android program, using Forth for script into program

NOP LIT dup drop swap >R R> CALL branch ;
0> 0< 0= ?branch
@ !
+ - * /MOD
AND OR XOR SHL SHR
ZERO_EXIT 1+ 1-
IN OUT WAIT LIT8 c@ c!
EXIT .

: ; create allot does> , here
compile compile_call immediate >resolve <resolve >mark <mark
name> L>NAME link> >body state@
word find
  
  (  comment )
  
  < > =
if then else
begin until while repeat

var const

over rot ?DUP R@ / mod
2+ 2- 2/
negate
ABS ( A --->абс A )

2dup 2drop 2var 2@ 2!

 (
  float words 
  1.073741824E9 5.304989477E-315 10f 4.6
  compatible java double type
    )
  flit
fdup fswap fdrop
f. f! f@ f+ f- f* f/ f> f< f= f<> f>= f<=
fsin fcos ftan fasin facos fatan fatan2
fln flog fsqrt fexp fabs floor d>f f>d

  ascii -- ascii " вернет 34
s" -размещает строку
type
s+ ( s0 l0 s1 l1 - sn ln конкатенация строк )
spick - дублирование строки
s= сравнение строк  
  
  
