SynthDef('osc.chord', {|out=0, gate=1, atk=0.01,rel=1, crv=2, freq=200, amp=0.2, pan= 0|
	var sig,env;
	env = EnvGen.ar(Env.perc(atk,rel,curve:crv), gate, doneAction: 2);
	sig = SinOsc.ar(freq)*amp*env;
	sig = Pan2.ar(sig, pan);
	Out.ar(out,sig);
}).add;

SynthDef('osc.saw', {|out=0, gate=1, atk=0.01,rel=1, crv=2, freq=200, amp=0.2, pan= 0|
	var sig,env;
	env = EnvGen.ar(Env.perc(atk,rel,curve:crv), gate, doneAction: 2);
	sig = RLPF.ar(BPF.ar(PinkNoise.ar,freq,0.1),freq,0.001)*amp*env;
	sig = Pan2.ar(sig, pan);
	Out.ar(out,sig);
}).add;

Pdef(\osc,
	Pbind(
		\type, \on,
		\instrument, 'osc.chord',
		\atk, 0.1,
		\rel,1,
		\midinote, [0,7,3,10] + 60 + Prand((0..12).scramble[0..4],inf),
		\dur, 1,
		\pan, Pwhite(-1.0,1.0)
		)
	);

Pdef(\osc2,
	Pbind(
		\type, \on,
		\instrument, 'osc.chord',
		\atk, 0.1,
		\rel,4,
		\midinote, [0,7,3,10] + 84 + Prand((0..12).scramble[0..4],inf),
		\dur, 4,
		\pan, Pwhite(-1.0,1.0)
		)
	);

SynthDef(\vib, {|out=0, gate=1|
	var sig,env;
	var in = In.ar(out, 2);
	env = EnvGen.ar(Env.linen(), doneAction: 0);
	sig = in * SinOsc.kr(10).range(0,1);
	Out.ar(out,sig);
}).add;

SynthDef(\delay, {|out=0, gate=1|
	var sig,env;
	var in = In.ar(out, 2);
	env = EnvGen.ar(Env.linen(), doneAction: 0);
	sig = AllpassN.ar(in, 1/6,1/6,);
	Out.ar(out,sig);
}).add;


