
t = TempoClock(90/60).permanent_(true);
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
SynthDef(\kick, {|
	out=0, gate=1, freq=1000,amp = 0.1, atk = 0.01, rel=0.25, pan=0|
	var sig, env;
	var lodriver, locutoffenv, hidriver, hicutoffenv, freqs, res, thwack;
	
	env = EnvGen.ar(Env.perc(atk, rel,-10), gate, doneAction:2);

	locutoffenv = EnvGen.ar(Env.perc(0.1, 0.5)) * 200 + 40;
	lodriver = LPF.ar(WhiteNoise.ar(0.1), locutoffenv);
	
	//DULLER ATTACK
	thwack = LPF.ar(WhiteNoise.ar*0.5*EnvGen.ar(Env.perc(0.001,0.1,1),200));
	
	freqs  = {rand(20, freq)}.dup(50);
	res    = Ringz.ar(lodriver + thwack, freqs).mean;
	
	sig = ((res * 1) + (lodriver * 2) + thwack)*amp*env;
	sig = Pan2.ar(sig,pan);
	Out.ar(out,sig);
}).play;
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
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
Pdef(\chick,
	Pbind(
		\type,\on,
		\instrument,\chick,
		\pan,Pwhite(-1.0,1.0),
		\dur,Pseq([
			0.25.rest,0.25,0.25,0.25,
			0.25.rest,0.25,0.25,0.25,
			0.25,0.25,0.25,0.25,
			0.25,0.25,0.25,0.25
			],inf)
		)
	);
Pdef(\kick,
	Pbind(
		\instrument,\kick,
		\rel,Pseq([1.75,0.5],inf),
		\amp,0.5,
		\freq,1000,
		// \pan,Prand([0],inf),
		\pan,[-0.5,0.5],
		// \dur,Pseq([
		// 	2,2,
		// 	],inf)
		\dur,Pseq([
			// Pseq([2,2],4),
			Pseq([1.75,0.25,0.5,1.5],3),
			Pseq([1.75,0.25,0.5!4].flatten,1),
			],inf)
		)
	);
Pdef(\clap,
	Pbind(
		\type,\note,
		\instrument,\clap,
		\amp,0.5,
		\dur,Pseq([
			2,2
			],inf)
		)
	)

Pdef(\chick).play(t,quant:4);
Pdef(\kick).play(t,quant:4);
Pdef(\chick).stop;
Pdef(\kick).stop;

Pdef(\clap).quant_([4,1]).play(t); 
Pdef(\clap).stop;
Pdef(\clap).clear;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
Ndef(\spacereverb,{|out =0 ,gate= 1 ,threshold= 0.1 ,amp=0.1,mix = 0.1,length=0.1|

	var a,c,z,y,in,sig;
	var env = Linen.kr(gate, 0.1, 1, 0.1, 2);

	c = 16; // number of comb delays
	a = 16; // number of allpass delays

	// in=Pan2.ar(PlayBuf.ar(2, b.piano[0],rate:1, loop: 0), -0.5);
	in = \in.ar([0,0]);
	in = LPF.ar(in,4000);
	// in = In.ar(out,2);
	// reverb predelay:
	z = DelayN.ar(in, 0.048,0.048);

	y=Mix.arFill(c,{CombL.ar(z,0.1,rrand(0.01, 0.1),5)});

	// chain of 4 allpass delays on each of two channels (8 total) :
	a.do({ y = AllpassN.ar(y, 0.051, [rrand(0.001, 0.02),rrand(0.001, 0.03)], length) });

	sig = (in*(1-mix))+(y*mix);

	sig = LPF.ar(sig,4000);

	Out.ar(out,Pan2.ar(sig,0)*env*amp);

});


Ndef(\pulsescale, 
	Ppar([
		Pdef(\bassloop),
		Pdef(\fill),
		Pdef(\chick)
		])
	);
Ndef(\pulsescale).stop;
Ndef(\spacereverb) <<> .in Ndef(\pulsescale);

Ndef(\spacereverb).quant=8;
Ndef(\spacereverb).play;

Ndef(\spacereverb).stop;
Ndef(\spacereverb).clear;
Ndef(\spacereverb).clock=t;
Ndef(\spacereverb).set(\mix,0.03);
Ndef(\spacereverb).set(\length,0.5);
Ndef(\spacereverb).set(\amp,0.7);

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
Ndef(\clap, Ppar([
		Pdef(\bassloop);
	]));

Ndef(\clap)[1]=\filter->{|in|
	AllpassN.ar(in,1,1/6,2);
}

Ndef(\clap).quant=[4,0]
Ndef(\clap).clock=t;
Ndef(\clap).play;

3
3000.cpsmidi.round

