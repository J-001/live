
Pdef(\drum,
	Pbind(
		\instrument,\samplerM,
	\bufnum,b.kitchen[2],
	\amp,0.1,
	\loop,0,
	\dur,Pseq([
	Pseq([3,3,8,2]),
	Pseq([3,3,10])
	],inf)*0.125
	)
)

Pdef(\drum).clock=t;
Pdef(\drum).quant=0;
Pdef(\drum).play;
Pdef(\drum).stop;

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------


Pdef(\drum2,
	Pbind(
		\instrument,\samplerM,
	\bufnum,b.kitchen.choose,
	\amp,0.1,
	\loop,0,
	\dur,Pseq([
	Pseq([2,1,1]),
	],inf)*0.125
	)
)

Pdef(\drum2).clock=t;
Pdef(\drum2).quant=4;
Pdef(\drum2).play;
Pdef(\drum2).stop;

Pdef(\drum3,
	Pbind(
		\instrument,\samplerM,
	\bufnum,b.kitchen.choose,
	\amp,0.1,
	\loop,0,
	\dur,Pseq([
	Pseq([2,1,1]),
	],inf)*0.125
	)
)

Pdef(\drum3).clock=t;
Pdef(\drum3).quant=4;
Pdef(\drum3).play;
Pdef(\drum3).stop;