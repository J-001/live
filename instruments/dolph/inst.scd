
SynthDef(\dolph, {|
	out=0, gate = 1, done = 2,
	atk = 0.01, rel = 2,
	amp = 0.5,
	freq = 90|
	var sig,env;
	var freqEnv;
	var note;
	env = EnvGen.kr(Env([0,1,0],[atk,rel] ,[2,-5]), gate, doneAction: done);

	note = (freq);
	freqEnv = env.linlin(0, 1, note-4, note);
	sig = SinOsc.ar(freqEnv.midicps * [0.99, 1.0, 1.01]);
	sig = Splay.ar(sig);
	Out.ar(out,sig * env * amp * 0.5);

}).add;

SynthDef(\dolph_asr, {|
	out=0, gate = 1, done = 2,
	atk = 0.01, rel = 2,
	amp = 0.5,
	freq = 90|
	var sig,env;
	var freqEnv;
	var note;
	env = EnvGen.kr(Env.asr(atk,releaseTime: rel, curve: -4), gate, doneAction: done);

	note = (freq);
	freqEnv = env.linlin(0, 1, note - 4, note);
	sig = SinOsc.ar(freqEnv.midicps * [0.99, 1.0, 1.01]);
	sig = Splay.ar(sig);
	Out.ar(out,sig * env * amp * 0.5);

}).add;