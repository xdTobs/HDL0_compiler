grammar cc;


start : '.hardware' h=hardwaredecl '.inputs' i=inputsdecl '.outputs' o=outputsdecl '.latches' l=latchesdecl '.update' u=updatedecl '.simulate' s=simulatedecl EOF ;

hardwaredecl: SIGNAL
;

updatedecl : u=update+
;

update : input=SIGNAL '=' e=expr
;

simulatedecl : s=simulate+
;
simulate :  input=SIGNAL '=' value=BINARY
;

latchesdecl : latches+ ;
latches :  input=SIGNAL '->' output=SIGNAL       
;

inputsdecl : s=SIGNAL+
;

outputsdecl : s=SIGNAL+
;



expr : '!' e=expr             #Not
     |e1=expr '&&' e2=expr    #And
     | e1=expr '||' e2=expr   #Or
     | '(' e=expr ')'         #Paren
     | SIGNAL                 #Signal
     ;

SIGNAL : [a-zA-Z][a-zA-Z1-9_]* ;

BINARY : [0-1]+ ;

COMMENT : '//' ~[\n]* -> skip;

LONGCOMMENT : '/*' (~[*] | '*'~[/])* '*/' -> skip;

WHITESPACE : [ \n\t]+ -> skip ;
