//--------------FRAC-DUST----------------------------------------------
//-----------------------------------------------------------------------
SynthDef(\fracAsr, {|
	out=0, gate = 1, done = 2,
	detune = 0.01,
	atk = 0.001, rel = 4, crv = -4,
	pan = 0, mix = 1|
	var sig,env;
	var freqs;
	var wet;
	var lag = \lag.kr(0);
	env = EnvGen.ar(Env.asr(atk, releaseTime:rel, curve: crv), gate, doneAction: done);
	freqs = \freq.kr(30,lag) * (1 - [detune,0,detune.sign]);
	sig = SinOsc.ar(freqs);
	wet = sig.frac*0.1;
	sig = (sig * (1 - mix)) + (wet * mix);
	sig = sig.mean;

	sig = Pan2.ar(sig, pan);
	Out.ar(out,sig * env * \amp.kr(0.5,lag));
}).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
SynthDef(\fracDustAsr, {|
	out=0, gate = 1, done = 2,
	detune = 0.001,
	atk = 0.001, rel = 4, crv = -4,
	pan = 0, mix = 1,
	hicut = 1500, locut = 800|
	var sig,env;
	var freqs;
	var wet;
	var lag = \lag.kr(0);

	env = EnvGen.ar(Env.asr(atk,releaseTime: rel, curve: crv), gate, doneAction: done);
	freqs = \freq.kr(30,lag) * (1 - [detune,0,detune.sign]);
	sig = SinOsc.ar(freqs);
	wet = (sig * {PinkNoise.ar(0.1)}!3);
	wet = wet.frac*0.1;

	wet = LPF.ar(wet, EnvGen.ar(Env.perc(0.05, 0.05), Dust.kr(20)).linlin(0,1,locut, hicut));

	sig = (sig * (1 - mix))*0.1 + (wet * mix);
	sig = sig.mean;

	sig = Pan2.ar(sig, pan);
	Out.ar(out,sig * env * \amp.kr(0.5,lag));
}).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
SynthDef(\fracDustPerc, {|
	out=0, gate = 1, done = 2,
	freq = 30, amp = 0.3,
	detune = 0.001,
	atk = 0.001, rel = 4, crv = -4,
	pan = 0, mix|
	var sig,env;
	var freqs;
	var wet;

	env = EnvGen.ar(Env.perc(atk, releaseTime:rel, curve: crv), gate, doneAction: done);
	freqs = freq * (1 - [detune,0,detune.sign]);
	sig = SinOsc.ar(freqs);

	wet = (sig * PinkNoise.ar(0.1)!3);
	wet = wet.frac*0.1;

	wet = LPF.ar(wet, 1000 * EnvGen.ar(Env.perc(0.05, 0.05), Dust.kr(20)) + 800);

	sig = (sig * 0.0) + (wet * 0.75);
	sig = sig.mean;

	sig = Pan2.ar(sig, pan);
	Out.ar(out,sig * env * amp);
}).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
// EFFECTS