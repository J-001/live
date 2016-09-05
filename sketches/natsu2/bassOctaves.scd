Pdef(\bassOctaves,
	Ppar({|n|
	Pbind(
		\instrument,\fmPianoASR,
		\scale,Scale.aeolian.degrees,
		\octave,3+[0,1][n],
		\degree,Pseq([
			-2,-3
			],inf),
		// \amp,k.bassOctaves.amp,
		\amp,Pseq([Pwhite(0.01,1.0,4)],inf),
		\detune,Pseq([Pwhite(0.01,1.0,4)],inf),
		\carRatio,Pseq([Pwhite(0.01,1.0,4)],inf),
		\modRatio,Pseq([Pwhite(0.01,1.0,4)],inf),
		\index,Pseq([Pwhite(0.01,1.0,4)],inf),
		\vib,Pseq([Pwhite(0.01,1.0,4)],inf),
		\legato,1,
		\dur,8
		);
	}!2);
);

// Pdef(\bassOctaves).reset;
Pdef(\bassOctaves).clock=t;
Pdef(\bassOctaves).quant=0;
Pdef(\bassOctaves).play;
Pdef(\bassOctaves).stop;



SynthDef(\bass, {|out=0,dur=2,legato=1,amp|
	var sig,env;
	var freqs;
	env = EnvGen.ar(Env([0,1,0],[0.001,0.999]*dur*legato,[1,1,-4]),doneAction:2);
	freqs = \freq.kr(32)*(1-[0.001,0,0.001.sign]);
	sig = SinOsc.ar(freqs);
	sig = Splay.ar(sig);
	Out.ar(out,sig*env*amp);
}).add;


~bass = 
Pbind(
	\instrument,\bass,
	\midinote,24,
	\legato,Pseq([
		1,
		1.0,1.0,1.0,1,1
		],inf)*0.5,
	\dur,Pseq([
		8,
		1.rest,3+1,2,1
		],inf)
	).play(t);

~bass.stop;

Pdef(\bass1,
	Pbind(
	\instrument,\bass,
	\midinote,24,
	\amp,Pwhite(0.1,0.5),
	\legato,0.25,
	\dur,Prand([0.25,0.5],inf)
	)
)

Pdef(\bass1).play(t,quant:8);
c.stop;
c=nil;
// t.tempo_(128/60).permanent_(true);
// t = TempoClock.new
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------

t.tempo_(5)

Pdef(\bassOctaves,
	Pbind(
		\instrument,\fmPianoASR,
		\scale,Scale.aeolian.degrees,
		\octave,5,
		\degree,Pseq([
			-2,-1,0
			],inf),
		// \amp,k.bassOctaves.amp,
		\amp,Pwhite(0.05,0.2),
		\detune,Pwhite(0.01,1.0),
		\carRatio,Pwhite(0.01,1.0),
		\modRatio,Pwhite(0.01,10.0),
		\index,Pwhite(0.01,10.0),
		\vib,Pwhite(0.01,1.2),
		\legato,0.9,
		\dur,Pseq(
			Pseq([4,3,9],inf),
			Pseq([4,3,9],inf),
			)
			*0.18
		);
);



Pdef(\bassOctaves).clock=t;
Pdef(\bassOctaves).quant=0;
Pdef(\bassOctaves).play;
