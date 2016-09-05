

SynthDef(\fmPno, {|out=0,carRatio=1,modRatio=1,index=1|
var car,mod;
	var atkEnv;

	var susEnv;
	var sig;
	var freq;

	freq = \freq.kr(262)*[0.99,1,1.01];

	atkEnv = EnvGen.ar(Env.perc(0.01,0.075,1,-2));

	mod = SinOsc.ar(modRatio*freq)*(index*freq);
	
	car = SinOsc.ar((carRatio*freq) + mod);

	car = car*atkEnv*0.5;

	susEnv = EnvGen.ar(Env.asr(0.01,1,0.5));
	sig = SinOsc.ar(freq);

	sig = sig+car;

	sig = sig;

	sig = Splay.ar(sig);

	// sig = Pan2.ar(sig,SinOsc.kr(2));

	sig = sig*SinOsc.kr(4).linlin(-1,1,0.0,1);

	Out.ar(out,sig*susEnv);

}).play(s,[modRatio:12,index:18])

