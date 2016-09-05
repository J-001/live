"~/Live/setup/init.scd".loadPaths;
//-----------------------------------------------------------------------
b.load;
i.load('fracDust','smp');
//-----------------------------------------------------------------------

Pdef(\superBell,
	Ppar(	
		Pbind(
			\bufnum, b.bell.hand[0], 
			\instrument, i.smpN,
			\rate, Pwhite(0.95,1.05) * Prand((0..5),inf),
			\startPos, 0,
			\loop, 1,
			\amp, 0.3,
			\pan, Pwhite(-1.0,1.0),
			\legato,  1,
			\dur, 8,
		)!5,inf)
);

\superBell.pd.q_(4).p;
i.load('bass')
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------

n[\knob0].cc_({|val| Ndef(\name).smap(\key, val)});