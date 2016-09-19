"Pattern : ".post;
Pdef(\radioNoise.postln,
	Pmono(
		\fracDustAsr,
		\amp,0.7,
		\freq, Prand({rand(10000,15000)}!3,inf),
		\locut, Pwhite(100,500),
		\hicut, Pwhite(1000,1200),
		\dur, Pwhite(0.125,0.5),
		\lag,0.1,
		\mix,Pwhite(0.7,1.0),
		\detune,0.0
		)
);
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
// Pdef(\noise2clean);
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
"Pattern : ".post;
Pdef(\noise2clean.postln, 
	Pmono(
		\fracDustAsr,
		\amp,0.7,
		\freq, Prand({rand(10000,15000)}!3,inf),
		\locut, Pwhite(100,500),
		\hicut, Pwhite(1000,1200),
		\dur, Pwhite(0.125,0.5),
		\lag,0.1,
		\mix,Pwhite(0.7,1.0),
		\detune,0.0
	)
)