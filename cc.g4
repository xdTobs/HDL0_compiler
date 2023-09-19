grammar cc;


start : '.inputs' inputsdecl '.outputs' outputsdecl '.latches' latchesdecl '.update' updatedecl '.simulate' simulatedecl EOF ;


modus: (update|simulatedecl);

updatedecl : update+ ;

update : SIGNAL '=' expr ;

simulatedecl : simulate+ ;
simulate :  SIGNAL '=' BINARY ;

latchesdecl : latches+ ;
latches :  SIGNAL '->' SIGNAL ;

inputsdecl : SIGNAL+ ;

outputsdecl : SIGNAL+ ;



expr : '!' expr
     |expr '&&' expr
     | expr '||' expr
     | '(' expr ')'
     | SIGNAL
     ;

SIGNAL : [a-zA-Z][a-zA-Z1-9_]* ;

BINARY : [0-1]+ ;

COMMENT : '//' ~[\n]* -> skip;

LONGCOMMENT : '/*' (~[*] | '*'~[/])* '*/' -> skip;

WHITESPACE : [ \n\t]+ -> skip ;
