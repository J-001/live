//-----------------------------------------------------------------------
// -----------------------------------------------------------------------
SynthDef('sub', {|out=0, gate = 1, done = 2, detune = 0.01, lag = 0.1|
	var sig,env;
	var freqs = \freq.kr(30, lag) * (1 - [detune, 0, detune.sign]);

	env = \env.kr(Env([0,1,0], [0.01,0.99],[-2,2]));
	env = EnvGen.ar(env, gate, doneAction: done);
	sig = Splay.ar(SinOsc.ar(freqs));

	Out.ar(out,sig * env * \amp.kr(0.3, lag));
}, 
variants: (
	\mono : [\env, Env.asr, \done, 0],
	\perc : [\env, Env.perc]
	)
).add;			


Pdef(\b_line,
	PmonoArtic(
		'sub.mono',
		\midinote, [22+12] + Pseq([0, 12], inf),
		\detune, 0.01,
		\amp, 0.5,
		\legato, Prand([1,0.15], inf),
		\dur, Pseq([1.5,1.5,1.5,1.5,1,1], inf)
	)
).q_(16).p

Pdef(\b_line,
	PbindProxy(
		\instrument, 'sub',
		\midinote, [22],
		\amp,0.5,
		\legato, 0.5,
		\dur, 16,
		\env, Pfunc{|e| Env.perc(0.01,e.dur*e.legato)}
		)
	).q_(16).s


Pdef(\b_line_arp,
	PbindProxy(
		\instrument,'sub',
		\midinote, Pseq([0,1,2,3,4,\rest,\rest, Prand((0..11),7)], inf) + 80,
		\detune, Pwhite(0.0,0.5),
		\amp,0.5,
		\dur, Pseq([1,1,1,1,1, 3.rest,1.rest,1,1,1,1,1,1,1],inf) * 0.5,
		\env, [Env.perc(0.01,0.25)]
	)
).quant_(16).s

Pdef(\b_line_arp,
	PbindProxy(
		\instrument,'sub',
		\midinote, Prand({|n| Pseq((0..(2 + n)))}!5, inf)+ 80,
		// \midinote, 80,
		\detune, Pwhite(0.0,0.05),
		\amp,0.5,
		\dur, 0.5,
		\env, [Env.perc(0.01,0.25)]
	)
).quant_(16).p

Pdef(\b_line_arp,
	PbindProxy(
		\instrument,'sub',
		\midinote, Pseq((0..64)) + 80,
		\detune, Pwhite(0.0,0.05),
		\amp,0.5,
		\dur, 0.5,
		\env, [Env.perc(0.01,0.25)]
	)
).quant_(16).p

\b_line_arp_P.c
\b_line_P.c
	
TempoClock.default.tempo = 2;
