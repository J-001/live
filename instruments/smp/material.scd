Pdef(\romance,	
	Pbind(
		\bufnum, b.loops.romance[0], 
		\instrument, i.smpN,
		\rate, 1,
		\startPos, 0,
		\loop, 1,
		\amp, 0.3,
		\pan, 0,
		\legato,  1,
		\dur, Pfunc{|e|e[\rate] * e[\bufnum].duration * e[\legato]},
	)
);
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
Pdef(\train_frag.postln,	
	PbindProxy(
		\type, \on,
		\bufnum, b.piano.chord[0], 
		\instrument, i.smp_lqd,
		\rate, [1,1.5,16/9, 8/7] + Pseq([Pseq(0!(16+12))++Pseq(3!4)], inf),
		\startPos, Pwhite(0.33,0.57),
		\loop, 0,
		\amp, Pwhite(0.1,0.5),
		\pan, Pwhite(-1.0,1.0),
		\legato,  1,
		\dur,  Pseq([0.125], 4),
		\env, Pfunc{|e| 
			Env([0,1,0], [0.01,0.99] * e.legato * e.dur, [-2,2])},
		\lag,0.01
	)
).play;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------


