Pdef(\play,	
	PbindProxy(
		\bufnum, Prand(b.choose.choose,inf), 
		\instrument, i.smpN,
		// \rate, -1 * {rrand(0.9,1.1)}!3 * Pwrand([1,5],[0.9,0.1],inf),
		\rate, 1,
		\startPos, Pwhite(0.5,1.0),
		\loop, 1,
		\amp, 0.5,
		\pan, Pwhite(-1.0,1.0),
		\legato,  1,
		\dur,  Pseq([1,1,1,0.5,0.5],inf) 
	)
).q_(8);
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
\gtrPlay.q_(8) @ (
	Pdef(\play) >< 
	PbindProxy(
		\bufnum , b.guitar.choose[0],
		\startPos, Pwhite(0.0,2.0),
		// \rate, Prand([[1,0.5]], inf) * Prand([-1,1], inf) ,
		\rate, 1,
		\legato,2,
		\amp, 0.1,
		\instrument, i.smpN,
		\trig, 1,
		'dur', Pseq([4,8,2,2],inf)
		) 
	);
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
\gtrPlay2.q_(8) @ ( 
	Pdef(\play) >< 
	PbindProxy(\bufnum , 
		b.guitar.choose[0],
		\startPos, Pwhite(0.0,1.0),
		\rate, Prand([[1,0.5]], inf) * Prand([-1,1], inf) ,
		\amp, 0.1,
		\instrument, i.smpN,
		\trig, 1,
		'dur', Pseq([4,8,2,2],inf)
		) 
	);
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
\gtrLin.q_(8) @ ( 
	Pdef(\play) >< 
	PbindProxy(\bufnum , 
		b.guitar.choose[0],
		\startPos, Pwhite(0.0,1.0),
		\amp, 0.05,
		\instrument,  i.smpN ,
		\trig, 1,
		'dur', Pseq([60]),
		\rate, {XLine.kr(0.05,100,60) }

		) 
	);
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
\crackle.q_(8) @ Pdef( \crk,
	Pmono(
		'smp.asr2',
		\bufnum, b.noise.glitch[0],
		\loop,1,
		\rate, Pwhite(0.1,2.0),
		\lag,2,
		\dur,Pwhite(2.0,4.0)
		) 
	);
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
\dolSong @ (
	\dolCascade |? [
	\dur, { Prand([0.25,0.125],inf)},
	\legato, {Pwhite(0.25,0.5) * 5},
	\freq, {Pseq([0,4,7,11], inf)+  ( Pwhite(6,6) * 12)},
	\detune, {0.1}
	]
);
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
\loline.q_(8) @ (
	Pdef(\loline,
			PbindProxy(
				\instrument, \bass,
				\midinote, Prand([0,2,4,7,11,14],inf) + 31,
				\dur, Pseq([8]),
				\amp,0.5
				\rel, Pfunc(|e| e.dur * e.legato)
				)
		)
	)
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
\noiseLine.q_(0) @ { 
	var line;
	line = XLine.kr(0.001,1, 0.1);
	LPF.ar(
		WhiteNoise.ar(line * 0.02),
		line * 18000 + 40
		)
}