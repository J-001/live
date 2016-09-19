SynthDef(\fmMMCAsr, {|
	out = 0, 
	gate = 1,
	done = 0,
	carRatio = 1,
	modRatio1 = 1, 
	modRatio2 = 1,
	index1 = 1,
	index2 = 1,
	amp = 0.3,
	lag = 0.05|

	var car, mod1, mod2;
	var freq = \freq.kr(262, lag);
	mod1 = SinOsc.ar(freq * modRatio1, 0,freq * index1);
	mod2 = SinOsc.ar(freq * modRatio2, 0,freq * index2)  *  SinOsc.kr(10,0,0.5,0.5);
	car = SinOsc.ar((freq * carRatio) + mod1 + mod2);
	car = car * EnvGen.kr(Env.asr(0.01, 1, 0.01), gate, doneAction: done);
	Out.ar(out,Splay.ar(car) * amp.lag(lag) * 0.15);
}).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
