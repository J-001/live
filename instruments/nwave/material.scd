Pdef(\nwave,
	Pbind(
	\type, \instr,
	\instr, Prand(i.nwave,inf),
	\noise, Prand([ {PinkNoise.ar}, {WhiteNoise.ar}
		// {Instr(\sine).ar(rrand(1,10))} 
		],inf),
	\amp,Pwhite(0.1,0.5),
	\legato, Prand((4..6), inf),
	\dur, Pwrand([0.25,2]*2, [0.5,0.25,0.25], inf), 

	\direction,Prand([-1,1],inf),
	\locut, Pwhite(200,19500),
	\hicut, 20000 - Pkey(\locut),
	\lfospd, Pfunc{|e|(e.dur * e.legato).linlin(0,32,20,0.01)},
	)
);
