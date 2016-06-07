/*
Sketch: 28_05_16
*/


Pbind(
	\instrument,\play,
	\bufnum,b[\piano].choose,
	\rate,2,
	\dur,Pseq([[1,1,1]/3,[1,1],[1.5,0.5],[1,1]].flat*0.25,inf)
	).play

Pbind(

	\instrument,\play,
	\bufnum,b[\piano].choose,
	\rate,100,
	\dur,Pseq([3,3,3,3,2,2]*0.25,inf)
	).play