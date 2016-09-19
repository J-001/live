t = TimeStructure();
t.make(
	Area(\intro, 32).make({|area, dur|
		area.addN( 
			TimeGrid(\beat, (1!32).normalizeSum * dur ) 
			TimeGrid(\bar, (4!8) )
			);
	})
);
t.areas[\intro].grids[\bar].marks
t.begin;
t.nextTimeOnGrid
\sin.s
t.areas

p = Pbind().play(quant: [t,0])

Ndef(\sin).stop

Ndef(\sin, {
	SinOsc.ar * 0.1
	}).quant_([t,0]).play;

Ndef(\sin).quant_([t,0])




