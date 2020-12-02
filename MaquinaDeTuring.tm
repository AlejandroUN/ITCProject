#inputAlphabet
A-C
d
D
E

#tapeAlphabet
A-C
d
D
E

#states
q0
q1
otro_estado

#initial
q1

#accepting
otro_estado

#transitions
q0:A?q1:B:>
q0:B?q1:C:>
q0:C?q1:D:>
q0:d?otro_estado:E:>
q1:A?q0:B:>
q1:B?otro_estado:C:>
q1:C?q0:D:>
q1:d?otro_estado:E:>
otro_estado:A?q0:B:>
otro_estado:B?q0:C:>
otro_estado:C?q0:D:>
otro_estado:d?q0:E:>
