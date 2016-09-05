SynthDef(\richbass, {|out=0,gate=1,freq=262,detune=0.01,vib=0,amp=0.1,dur=1,legato=1|
	var sig,env;
	var freqs, phases;

	env = EnvGen.ar(Env.perc(0.001,dur*legato,curve:-3),gate,doneAction:2);

	freqs = freq*(1-[detune.sign,0,detune]);
	phases = rrand(-1*pi,pi);
	sig = SinOsc.ar(freqs,phases);
	sig =  Splay.ar(sig)*env*amp;
	Out.ar(out,sig);
}).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
SynthDef(\wavybass, {|out=0,gate=1,freq=262,detune=0.01,lag=0,vib=0,amp=0.1,dur=1,legato=1,done=0,rel=1|
	var sig,env;
	var freqs, phases;

	env = EnvGen.ar(Env.asr(0.001,rel:rel,curve:-3),gate,doneAction:done);
	freqs = Lag.kr(freq,lag)*(1-[detune.sign,0,detune]);
	phases = rrand(-1*pi,pi);
	sig = SinOsc.ar(freqs,phases);
	sig = sig * SinOsc.kr(vib*(1-[detune.sign,0,detune])*1.5).linlin(-1,1,0.5,1);
	sig =  Splay.ar(sig)*env*amp;
	Out.ar(out,sig);
}).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
Pdef(\bass,
	Pbind(
		\instrument,\richbass,
		\midinote,[24,36,48]+Pseq((0..3).scramble++(0..2),inf),
		\detune,0.01,
		\amp,0.4,
		// \dur,Pseq([
		// 	1+0.5,0.5+1,1+
		// 	0.5,0.5+1,1,1
		// 	],inf)*0.5
		// )
		\legato,Pwhite(0.1,0.8),
		\dur,Pseq([
			Pseq([1.5,1.5,0.5,0.5]),
			Pseq([1.5,1.5,1])],inf)
	)
).play(t,quant:8)

Pdef(\bass).stop;

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
Pdef(\wavybass,
	Pmono(
		\wavybass,
		\midinote, 36,
		\detune,0.01,
		\amp,0.2,
		\lag,4,
		\legato,1,
		\dur, 4
	)
).play(t,quant:4)







