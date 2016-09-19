//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
SynthDef(\chick, {|out=0, gate=1, pan=0, amp=0.1,rel=0.05|
	var sig, env;
	env = EnvGen.ar(Env.perc(0.001,rel),gate,doneAction:2);
	sig = HPF.ar(WhiteNoise.ar*0.1,{rrand(10000,15000)}!10).mean;
	sig = Pan2.ar(sig,pan);
	Out.ar(out, sig*env);
}).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
// SynthDef(\kick, {|
// 	out=0, gate=1, freq=1000,amp = 0.1, atk = 0.01, rel=0.25, pan=0|
// 	var sig, env;
// 	var lodriver, locutoffenv, hidriver, hicutoffenv, freqs, res, thwack;
	
// 	env = EnvGen.ar(Env.perc(atk, rel,-10), gate, doneAction:2);

// 	locutoffenv = EnvGen.ar(Env.perc(0.1, 0.5)) * 200 + 40;
// 	lodriver = LPF.ar(WhiteNoise.ar(0.1), locutoffenv);
	
// 	//DULLER ATTACK
// 	thwack = LPF.ar(WhiteNoise.ar*0.5*EnvGen.ar(Env.perc(0.001,0.1,1),200));
	
// 	freqs  = {rand(20, freq)}.dup(50);
// 	res    = Ringz.ar(lodriver + thwack, freqs).mean;
	
// 	sig = ((res * 1) + (lodriver * 2) + thwack) * amp * env;
// 	sig = Pan2.ar(sig,pan);

// 	Out.ar(out,sig);
// }).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
SynthDef(\clap, {|out=0,freq=0,gate=1,amp=0.2,rel=0.5|
	var sig;
	var source;
	var trig;
	var env;
	var masterEnv;
	masterEnv = EnvGen.kr(Env.asr(0.001,1,0.01),gate,doneAction:2);
	env = EnvGen.kr(Env([0,1,0],[0.01,0.999]*rel,[1,-7]),gate,doneAction:0);	
	source = WhiteNoise.ar*env;
	source = BPF.ar(source,{rrand(800,1200)}!20+freq,0.3);
	sig = Splay.ar(source)*env;
	sig = AllpassC.ar(sig,0.01,0.0075,0.08)*amp;
	Out.ar(out,sig!2);
}).add;