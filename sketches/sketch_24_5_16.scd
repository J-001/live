/*
Sketch 24/5/16
*/

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
//PERCUSSION
SynthDef(\ratchet, {|
	out=0,
	freq=262,
	gate=1,
	rate=1,
	dur=1,
	legato=1|

	var sig;
	var source;
	var trig;
	var env;
	var masterEnv;

	masterEnv = EnvGen.kr(Env.asr(0.001,1,0.01),gate,doneAction:2);


	trig = EnvGen.kr(Env([30,0],[dur*legato],[-4]));
	// env = EnvGen.kr(Env([0,1,0],[0.01,0.9]*0.1),gate,doneAction:2);
	env = EnvGen.kr(Env([0,1,0],[0.001,0.999]*0.05),Impulse.kr(trig),doneAction:0);	

	source = DynKlank.ar(`[
			//FREQS
			[240, 2020, 3151],
			//AMPS
			[-9,0,-5].dbamp,
			//PHASES
			[0.8, 0.07, 0.08]
		],
		//EXCITER - enveloped noise
		BPF.ar(PinkNoise.ar, 6500, 0.01) *env*4
		);

	sig = source*env;

	sig = AllpassN.ar(sig,0.01,0.01,0.1);

	// sig = Pan2.ar(sig,LFNoise1.kr(10));

	Out.ar(out,sig!2);
}).add;

Pbind(
	\instrument,\ratchet,
	\dur,4,
	\legato,0.5
	).play(t,quant:2)

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
SynthDef(\clap, {|
	out=0,
	freq=0,
	gate=1|
§
	var sig;
	var source;
	var trig;
	var env;
	var masterEnv;

	masterEnv = EnvGen.kr(Env.asr(0.001,1,0.01),gate,doneAction:2);

	// env = EnvGen.kr(Env([0,1,0],[0.01,0.9]*0.1),gate,doneAction:2);
	env = EnvGen.kr(Env([0,1,0],[0.001,0.999]*0.5,[1,-7]),gate,doneAction:0);	

	source = WhiteNoise.ar*env;
	source = BPF.ar(source,{rrand(800,1800)}!20+freq,0.07);

	sig = source*env;


	sig = AllpassC.ar(sig,0.01,0.0075,0.08);

	sig = FreeVerb.ar(sig,0.1,0.1,0.99);

	// sig = Pan2.ar(sig,LFNoise1.kr(10));

	Out.ar(out,sig!2);
}).add;

d=nil!3;

d[0]=[1,1,1]/6;
d[1]=[1,1]*0.25;
d[2]=[Rest(1.5*0.25),[0.5,1,1]*0.25];

Pbind(\instrument,\clap,
	\freq, Pwhite(-100,100),
	\dur,Pseq([d[0],d[1],d[2]].flat,16)
	).play(t,quant:4)


//----------------------------------------------------------------------------
//----------------------------------------------------------------------------