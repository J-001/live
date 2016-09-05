SynthDef(\xplode, {|
	out=0, gate = 1, done = 2,
	freq = 30, amp = 0.3,
	detune = 0.001,
	atk = 0.001, rel = 4, crv = -4,
	pan = 0, mix = 0.8,
	hicut = 10000, locut = 80|
	var sig,env;
	var freqs;
	var wet;
	// Sig
	env = EnvGen.ar(Env.perc(atk, rel, curve: crv), gate, doneAction: done);
	freqs = freq * (1 - [detune,0,detune.sign]);
	sig = SinOsc.ar(freqs);
	// Distort
	wet = sig * PinkNoise.ar(0.1)!3;
	wet = wet.frac*0.1;
	// Filter
	wet = LPF.ar(wet, env.range(locut, hicut));
	// Mix
	sig = (sig * (1-mix)) + (wet * mix);
	sig = sig.mean;
	// Pan
	sig = Pan2.ar(sig, pan);
	// Out
	Out.ar(out,sig * env * amp);
}).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------