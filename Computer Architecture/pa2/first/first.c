#include <s t d i o . h>
#include <s t d l i b . h>
void getMem ( int ∗ p )
{
p = m all oc ( 5 ∗ s i z e o f ( int ) ) ;
}
int main ( int argc , char∗ argv [ ] )
{
int ∗ p t r ;
getMem ( &p t r ) ;
fo r ( int i = 0 ; i < 5 ; i++) {
p t r [ i ] = i ∗ i ;
p r i n t f ( ”%d\n” , p t r [ i ] ) ;
}
f r e e ( p t r ) ;
}