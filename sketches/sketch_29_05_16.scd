/*
Sketch: 29_05_16

Nice!!
√√√
*/

t=TempoClock(140/60).permanent_(true);

SynthDef(\ping, {|out=0,freq=262,rq=0.01,gate=1,amp=0.5,dur,pan,legato|
	var sig;
	var env;
	var sourceEnv;

	env = EnvGen.ar(Env.triangle(dur*legato),gate,doneAction:2);
	sig = PinkNoise.ar;
	sig = RLPF.ar(sig,freq,rq);
	sig = BPF.ar(sig,freq,0.001);
	sig = sig*amp*env;

	sig = Pan2.ar(sig,SinOsc.kr(rrand(0.1,2)).range(-1,1));

	Out.ar(out,sig*Line.kr(1,0,64));	
}).add;


//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

Pdef(\pingA,
	Pbind(
		\instrument,\ping,
		\amp,0.2,
		\out,Pseq([
			[0,0],
			[0,0,0,0,0],
			[0,0],
			[0,0,0,0,0]
			].flat,inf),
		// \midinote,Pseq([\,72!3])+[0,3,7,10,14],
		\midinote,Pseq([
			[0,0],
			[0,0,0,0,0],
			[0,0],
			[0,0,0,0,0]
			].flat,inf)+60+[0,3,7,10,14],
		\rq,0.001,
		\legato,Pseq([
			[2,2],
			[1,1,1,1,2],
			[1,1],
			[1,1,1,1,1]
			].flat,inf)*0.5,
		\pan,rrand(-1.0,1.0),
		\dur,Pseq([
			// [6,0.5,0.5,8],
			// [[8,8,8,8]/3,2].flat*0.5
			[2,2]*1,
			[0.5!4,2].flatten,
			[2,2],
			[0.5,0.5,1,1,1]
			].flat,inf)
	)
);
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
Pdef(\pingB,
	Pbind(
		\instrument,\ping,
		\amp,0.5,
		\out,Pseq([
			[5,5],
			[5,5],
			[5,5],
			[5,5]
			].flat,inf),
		// \midinote,Pseq([\,72!3])+[0,3,7,10,14],
		\midinote,Pseq([
			[2,3],
			[-2,0],
			[-5,-2],
			[0,5]
			].flat,inf)+60+[0,3,7,10,14],
		\rq,0.001,
		\legato,Pseq([
			[1,1],
			[1,1],
			[1,1],
			[1,1]
			].flat,inf)*0.25,
		\pan,rrand(-1.0,1.0),
		\dur,Pseq([
			[2,2]!4
			].flat,1)
	)
);

Pdef(\ping,
	Ppar([
		// Pseq([
		// Pdef(\pingA)
		// ],inf),
		Pseq([
			Pdef(\pingB)
			])
		],1);
);

Pdef(\ping).clock=t;
Pdef(\ping).quant=16;
Pdef(\ping).play;
Pdef(\ping).stop;

//WRITING PHRASES - concept
/*
1. Notate pitches, rhtyhm, articulation, tone specifically

2. Automate some elements using Pwhite etc.

Prhythm object:
1. Stores sub-arrays used for rhythms 
2. rhythm.accessMethod([1,2,1,3,4]) : 
recieves list of indexes for rhyhtms
reterns a flattened array
*/

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

SynthDef(\delay, {|out=0,in=5|
	var sig;

	sig = In.ar(in,2);

	sig = AllpassC.ar(sig,1/3,1/3,8);

	Out.ar(out,sig);

}).add;


~delay=Synth(\delay);
~delay.free;


ServerTree.add({~delay=Synth(\delay)})


//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
//REVERB


//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
//SYNTH 1
SynthDef(\synth1, {|out=0,freq=262,amp=0.2,gate=1,detune=0.0,dur|
	var sig;
	var env;
	var maxDetune;
	
	env = EnvGen.ar(Env.asr(0.1,1,0.1),gate,doneAction:0);
	
	maxDetune=0.01;
	maxDetune = detune*maxDetune*Line.kr(0,1,dur)*SinOsc.kr(100,0,0.5,0.5);
	
	sig = LFTri.ar(Lag.kr(freq,0.5)*[1-maxDetune,1,1+maxDetune]*0.5,[0,pi*0.5,pi]);
	sig = Splay.ar(sig);
	sig = sig.distort;
	sig = RLPF.ar(sig,freq,0.1);
	sig = sig*env*Lag.kr(amp,0.1);
	Out.ar(out,sig);
}).add;



Pdef(\synth1,
		Pmono(
			\synth1,
			\legato,1,
			\detune,1,
			\amp,Pwhite(0.1,0.2),
			\midinote,Pseq([
				Prand({Scale.aeolian.degrees.choose}!2),
				[Scale.aeolian.degrees.choose!3,0,3],
				[-20,Prand([0,-10,20,40])],
				[0,0,0,-20,-20]
				].flat,inf)+40,
			\dur,Pseq([
				[1,1],
				[1,1,1,1,1],
				[1,1],
				[1,1,1,1,1]
				].flat*1,inf)
		)
);



Pdef(\synth1).clock=t;
Pdef(\synth1).quant=16;
Pdef(\synth1).play;
Pdef(\synth1).stop;
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

SynthDef(\synth2, {|out=0,freq=262,amp=0.2,gate=1,detune=0.0,dur|
	var sig;
	var env;
	var maxDetune;
	
	env = EnvGen.ar(Env.asr(0.1,1,0.1),gate,doneAction:0);
	
	maxDetune=0.01;
	maxDetune = detune*maxDetune*Line.kr(0,1,dur)*SinOsc.kr(100,0,0.5,0.5);
	
	sig = LFTri.ar(Lag.kr(freq,0.5)*[1-maxDetune,1,1+maxDetune]*0.5,[0,pi*0.5,pi]);
	sig = Splay.ar(sig);
	sig = sig.distort;
	sig = sig*env*Lag.kr(amp,0.1)*Line.kr(1,0,10);
	Out.ar(out,sig);
}).add;


Pdef(\synth2,
		Pseq([
			Pmono(
			\synth2,
			\legato,1,
			\detune,1,
			\amp,Pwhite(0.1,0.2),
			\midinote,Pseq([
				Prand({Scale.aeolian.degrees.choose}!2),
				[Scale.aeolian.degrees.choose!3,0,3],
				[-20,Prand([0,-10,20,40])],
				[{Scale.aeolian.degrees.choose+(12*[-2,-1,0,1,2].choose)}!4,0,0,5,-20,-20].flat
				].flat,inf)+140,
			\dur,Pseq([
				[1,1],
				[1,1,1,1,1],
				[1,1],
				[0.25,0.25,0.25,0.25,1,0.5,0.5,1,0.5,0.5]
				].flat,inf)
		),
			Pseq([Event.silent(16*[8,2,4].choose)])
			],inf)

);


Pdef(\synth2).clock=t;
Pdef(\synth2).quant=16;
Pdef(\synth2).play;
Pdef(\synth2).stop;
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

Pdef(\synth2_2,
	Pmono(
			\synth2,
			\legato,1,
			\detune,1,
			\amp,Pwhite(0.1,0.2),
			\midinote,
				Prand({Scale.aeolian.degrees.choose+(12*[-2,-1,0,1,2].choose)}!10,inf)+140,
			\dur,Pseq([
				Pwrand([0.25,0.5,1,2,4,8],[0.4,0.3,0.2,0.1,0.1],inf)
				].flat,inf)
			)
	)
Pdef(\synth2_2).clock=t;
Pdef(\synth2_2).quant=16;
Pdef(\synth2_2).play;
Pdef(\synth2_2).stop;
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
	// [
	// 	[0,0],
	// 	[0,0,0,0,0],
	// 	[0,0],
	// 	[0,0,0,0,0]
	// 	].flat
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
/*
KIT

CLAP;
B.Drum

*/
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
SynthDef(\clap, {|out=0,amp=0.1,freq=800,gate=1|
	var sig;
	var env;
	var percEnv;

	percEnv = EnvGen.ar(Env.perc(0.01,0.2,curve:-10),doneAction:0);
	sig = ClipNoise.ar();
	sig = Mix(BPF.ar(sig,{rrand(freq,freq+3000)}!8,1));
	sig = sig*0.1;
	
	sig = sig*percEnv;

	sig = AllpassC.ar(sig,0.025,0.025,0.075);

	sig = sig*amp;
	Out.ar(out,sig!2);
}).add;

Pdef(\clap,
	Pbind(
	\instrument,\clap,
	\gate,1,
	\freq,Pwhite(1000,2000),
	\amp,Pwhite(0.01,0.05)*0.06,
	\dur,Pseq([3,2,1]/12,inf)
	)
)
Pdef(\clap).clock=t;
Pdef(\clap).quant=4;
Pdef(\clap).play;
Pdef(\clap).stop;

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

SynthDef(\piano, {|out=0,amp=0.1,gate=1,trig=0,startPos=0,rate=1,panRate=1|
	var sig;
	var env;
	env = EnvGen.ar(Env.asr(0.1,1,0.1),gate,doneAction:2);
	sig = PlayBuf.ar(1,b[\piano][1],rate:rate,trigger:Impulse.kr(trig),startPos:startPos,doneAction:0);
	sig = Splay.ar(sig);
	sig = sig*amp*env;
	// sig = RLPF.ar(sig,1200,0.025);
	sig = sig*SinOsc.ar(262,0,0.15);
	sig = Pan2.ar(sig,SinOsc.kr(panRate).range(-1,1));
	Out.ar(out,sig);
}).add;

Pdef(\piano,
	Ppar([
		{Pbind(
				\instrument,\piano,
				\amp,1,
				\bufnum,b[\piano][0],
				\legato,1,
				// \rate,[1,1.5,4/3,15/9].choose*[1].choose*[0.25].choose,
				\rate,0.98+((1..5)*0.01),
				\startPos,Pwhite(0.0,1.0),
				\trig,0,
				\panRate,Pwhite(10,10),
				\dur,16
				)}!1
	].flat)
)
Pdef(\piano).clock=t;
Pdef(\piano).quant=16;
Pdef(\piano).play;
Pdef(\piano).stop;


//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

SynthDef(\softNoise, {|out=0,gate=1,amp=0.01,cut=10000,trigRate=1|
	var sig;
	var env;

	var trig;

	trig = LFNoise2.kr(trigRate).range(0,1)>0.5;

	env = EnvGen.ar(Env.asr(0.1,1,0.1),gate,doneAction:2);
	sig = WhiteNoise.ar();
	sig = HPF.ar(sig,cut);

	sig = sig*env*amp*trig;
	Out.ar(out,sig*Line.kr(1,0,64))
}).add;

Pdef(\softNoise,
	Pbind(
		\instrument,\softNoise,
		\amp,Pseq([Pwhite(0.005,0.2,30),{\}!rrand(10,20)],1),
		\cut,Pwhite(15000,20000),
		\trigRate,Pexprand(10,20.0),
		\dur,Pwrand([0.125,0.25,0.5,1,2],[0.35,0.2,0.15,0.2,0.1],inf)
	)
)

Pdef(\softNoise).clock=t;
Pdef(\softNoise).quant=16;
Pdef(\softNoise).play;
Pdef(\softNoise).stop;


//----------------------------------------------------------------------------
//----------------------------------------------------------------------------


SynthDef(\loop, {|out=0,amp=0.1,gate=1,trig=0,startPos=0,rate=1,panRate=1|
	var sig;
	var env;
	env = EnvGen.ar(Env.asr(0.1,1,0.1),gate,doneAction:2);
	sig = PlayBuf.ar(2,b[\suzuki][0],rate:rate*MouseX.kr(0.25,100,\exponential)*rrand(0.5,1.0),trigger:0,startPos:startPos,loop:1,doneAction:0);
	sig = sig*amp*env;
	// sig = sig*SinOsc.ar(262,0,0.15);
	sig = Pan2.ar(sig,SinOsc.kr(panRate).range(-1,1));
	Out.ar(out,sig);
}).add;


t=TempoClock(60/60).permanent_(true);

Pbind(
	\instrument,\loop,
	\bufnum,b[\suzuki][0],
	\legato,1,
	\amp,1,
	\dur,Pfunc{|e|e[\bufnum].duration-1.0}
	).play(t,quant:4)





