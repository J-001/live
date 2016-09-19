
SynthDef(\c1m18, {|
	out=0,
	freq=220,
	carRatio=1,modRatio=24,index=18,
	rel=0.2,
	amp=1,gate=1,
	lforate=2|
	var car,mod;
	var sustainCar;
	var attackEnv;
	var mainEnv;
	var freqs;
	var lfo1;

	freqs = freq*[0.99,1,1.01];

	attackEnv = EnvGen.kr(Env([0,1,0],[0.01,0.99]*0.1,[2,-2]),doneAction:0);
	mod=SinOsc.ar(freqs*modRatio,0,freqs*index);
	car = SinOsc.ar((freqs*carRatio) + mod);
	
	car = car*attackEnv*0.5;

	/*Adds sustained tone after attack*/
	// sustainCar = SinOsc.ar(freqs);
	// car=car+sustainCar;

	mainEnv = EnvGen.kr(Env.asr(0.01,1,rel),gate,doneAction:0);
	car=car*mainEnv;

	lfo1 = SinOsc.kr(lforate);
	car = car*lfo1;

	Out.ar(out,car*amp);
}).add;


Pdef(\harmonics,
	Pbind(
	// \type, Prand([\note],inf),
	\instrument,\c1m18,
	\amp,1,
	\midinote, 20,
	\dur,Prand((1..4)*0.125,inf)
	)
);


Pdef(\harmonics).stop;
Pdef(\harmonics).play;




~qwe=1
