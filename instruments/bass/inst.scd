SynthDef(\kik, {|
	out=0, gate = 1, done = 2,
	freq = 30, amp = 0.3,
	detune = 0.001,
	atk = 0.001, rel = 4, crv = -4,
	pan = 0, mix = 0.8|
	var sig,env;
	var freqs;
	var wet;

	env = EnvGen.ar(Env.perc(atk, rel, curve: crv), gate, doneAction: done);
	freqs = freq * (1 - [detune,0,detune.sign]);
	sig = SinOsc.ar(freqs * env).mean;

	sig = Pan2.ar(sig, pan);
	Out.ar(out,sig * env * amp);
}).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------

//-----------------------------------------------------------------------
//-------------------


// ( \dur , Pseq([1.5,1.5,1].flatten ) )
// ( \dur , Pseq([1.rest,0.5!6].flatten ) )
// ( \dur , Pseq([5.rest, 1.5, 0.125!8].flatten ) )

