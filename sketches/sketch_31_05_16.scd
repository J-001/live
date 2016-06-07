/*
Sketch: 31_05_16
*/
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

t = TempoClock(60/60).permanent_(true);
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
SynthDef(\ground, {|out=0,freq=100,amp=0.1,gate=1|
	var trig;
	var env;
	var sig;

	var freqs;
	var amps;
	var phases;

	trig = EnvGen.ar(Env([0,1,0.5,0],[0.2,0.01,0.4],[1,2,-2]));
	env = EnvGen.ar(Env.adsr(0.2,0.1,0.5,0.4,1,-4),gate,doneAction:2);

	freqs = {rrand(20,225)}!20;
	amps = (225-freqs)/225;
	amps = amps*0.2;
	phases = 0;

	sig = LPF.ar(PinkNoise.ar,freqs)*env;
	sig = Splay.ar(sig);
	Out.ar(out,sig!2);

}).add;

Pbind(\instrument,\ground,\dur,1,\legato,0.5).play(t)
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

SynthDef(\mel, {|out=0,freq=262,amp=0.3,gate=1,lag=0.5|
	
	var atk=();
	var sig;
	var env;

	env = EnvGen.ar(Env.asr(0.2),gate,doneAction:2);
	// atk[\env] = EnvGen.ar(Env.perc(0.01,));

	sig = SinOsc.ar(Lag.kr(freq,lag)*[0.999,1,1.001],SinOsc.kr(5+[0,-1,-2],0.5,0.5)*pi*2);
	sig = Splay.ar(sig);
	sig = sig*amp*env;
	Out.ar(out,sig);
}).add;

Pdef(\wobble,
	Pmono(
		// \instrument,\mel,
		\mel,
		\midinote,Prand(Scale.choose.degrees,inf)+(12*Pwhite(6,6))+[0,14],
		\dur,0.5,
		\amp,0.03,
		\lag,0.5,
		\legato,0.2
	)
	)
Pdef(\wobble).clock=t
Pdef(\wobble).quant=4;
Pdef(\wobble).play
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------







