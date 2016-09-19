SynthDef(\fmPiano, { |
	out=0,
	freq=220,
	carRatio=1,modRatio=14,index=18,
	detune=0.1,
	rel=0.2,
	gate=1,vib=2,dur=0.5,legato=0.8|

	var sig;
	var car,mod;
	var sustainCar;
	var attackEnv, mainEnv;
	var freqs;
	var lfo;
	var lag;

	lag = \lag.kr(0.1);
	freqs = freq*(1-[detune,0,detune.sign]);

	attackEnv = EnvGen.ar(Env([0,1,0],[0.01,0.99]*0.1,[2,-2]),doneAction:0);

	mod=SinOsc.ar(freq*modRatio,0,freq*index);
	car = SinOsc.ar((freqs*carRatio) + mod);
	car = car*attackEnv*0.5;

	sustainCar = SinOsc.ar(freqs);

	sig=car+sustainCar;

	mainEnv = EnvGen.ar(Env.perc(0.01,dur*legato*rel,1),gate,doneAction:0);

	sig=sig*mainEnv;

	lfo = SinOsc.kr(vib).linlin(-1,1,0,1);
	sig = sig*lfo;

	sig = sig*\amp.kr(0.1,lag);

	Out.ar(out,sig);
},variants:(
	basic:[modRatio:24,index:18,detune:0.1]),
).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
SynthDef(\fmPianoASR, { |
	out=0,
	freq=220,
	carRatio=1,modRatio=14,index=18,
	detune=0.1,
	rel=0.2,
	amp=0.1,
	gate=1,vib=2,dur=0.5,legato=0.8|

	var sig;
	var car,mod;
	var sustainCar;
	var attackEnv, mainEnv;
	var freqs;
	var lfo;
	var lag;

	lag = \lag.kr(0.1);
	freqs = freq*(1-[detune,0,detune.sign]);
	mainEnv = EnvGen.ar(Env.asr(0.01,1,0.1),gate,doneAction:2);
	attackEnv = EnvGen.ar(Env([0,1,0],[0.01,0.99]*0.1,[2,-2]),doneAction:0);

	mod=SinOsc.ar(freq*modRatio,0,freq*index);
	car = SinOsc.ar((freqs*carRatio) + mod);
	car = car*0.5;

	sustainCar = SinOsc.ar(freqs);

	sig=car+sustainCar;

	// mainEnv = EnvGen.ar(Env.asr(0.01,1,0.1),gate,doneAction:2);

	sig=sig*mainEnv;

	lfo = SinOsc.kr(vib).linlin(-1,1,0,1);
	sig = sig*lfo;

	sig = sig*amp;

	Out.ar(out,sig);
},variants:(
	basic:[modRatio:24,index:18,detune:0.1]),
).add;