#inputAlphabet
a-b

#tapeAlphabet
a-b

#states
q0
q1
q2
q3

#initial
q0

#accepting
q3

#transitions
q0:a?q0:a:>
q0:b?q1:b:>
q0:!?q3:!:-
q1:a?q1:a:>
q1:b?q2:b:>
q2:a?q1:a:>
q2:b?q2:b:>
q2:!?q3:!:-
