SynthDef(\pure, {|out=0, gate=1, freq=20, amp=0.1, atk=0.05, rel=0.1, lag|
	var sig, env;
	var pan = \pan.kr(0, lag);
	var detune = \detune.kr(0, lag);
	env = EnvGen.ar(Env.asr(attackTime:atk, releaseTime:rel), gate , doneAction:2);
	sig = SinOsc.ar(Lag.kr(freq, lag) * (1 - [detune, 0, detune.sign]));
	sig = Mix(Splay.ar(sig));
	sig = Pan2.ar(sig, pan);
	sig = sig * Lag.kr(amp, lag) * env;
	Out.ar(out, sig);
}).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------

Pdef(\pure,
	Pmono(
		\pure,
		\legato,1,
		// \dur,Prand([0.25,0.125],inf),
		\dur,Pwhite(0.5,16.0),
		\amp,0.05,
		\detune,Pwhite(0.0,0.5),
		\freq,Pwhite(5000,18000),
		\lag,Pkey(\dur),
		\pan,Pwhite(-1.0,1.0)
		)
	).play(t,quant:8)

Pdef(\pure).stop
Pdef(\pure).quant = 8;
Pdef(\pure).play