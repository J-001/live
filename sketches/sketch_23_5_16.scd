/*
Sketch 23/5/16

*/


SynthDef(\fm,
 {|out=0,freq=262,carRatio=1,modRatio1=1,modRatio2=1,index1=1,index2=1,amp=0.3,gate=1|
	var car;
	var mod1;
	var mod2;

	var newFreq;

	newFreq = (Lag.kr(freq,0.5)+[0,3,7,10]).midicps;
	// newFreq = (freq+Scale.aeolian.degrees).midicps;

	mod1 = SinOsc.ar(newFreq*modRatio1,0,newFreq*index1);

	mod2 = SinOsc.ar(newFreq*modRatio2,0,newFreq*index2)*SinOsc.kr(10,0,0.5,0.5);

	car = SinOsc.ar((newFreq*carRatio)+mod1+mod2);

	car=car*EnvGen.kr(Env.asr(0.01,1,0.01),gate,doneAction:2);
	// car=car*EnvGen.kr(Env.perc(0.01,1,0.01),gate,doneAction:2);

	Out.ar(out,Splay.ar(car)*amp*0.15);
}).add;


~fd = [
	//[midinoteArr,durArr]
	[[0,-2,0,3,5],[3,5+2,2,2,2]],
	[[0,-2,0,-2,0,-2,0,-2,0,-2,0,-2,0,-2,0,-7],1!16]
]


~warm=NodeProxy.audio(s,2);
~warm.play;
~warm[0] = Pmono(
		\fm,
	\freq,Pseq([
		Pseq(~fd[0][0],4),
		Pseq(~fd[1][0],4),
		],inf)+60,
\dur,Pseq([
		Pseq(~fd[0][1],4),
		Pseq(~fd[1][1],4),
		],inf)*0.25,
\modRatio1,Pwhite(3,3),
\modRatio2,Pwhite(3,3),
\index1,Pwhite(3,3),
\index2,Pwhite(3,3),
\post,Pfunc{|e| 
	("modRatio1: "++e[\modRatio1]).postln;
		("modRatio2: "++e[\modRatio2]).postln;
		("index1: "++e[\index1]).postln;
		("index2: "++e[\index2]).postln;
		"".postln;}
	)



//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

SynthDef(\glass, {|
	out=0,
	freq=262,
	gate=1|

	var sig;
	var source;
	var trig;
	var env;

	trig = EnvGen.kr(Env([30,0],[3],[-4]));

	// env = EnvGen.kr(Env([0,1,0],[0.01,0.9]*0.1),gate,doneAction:2);
	env = EnvGen.kr(Env([0,1,0],[0.001,0.999]*0.05),Impulse.kr(trig),doneAction:0);	
	source = DynKlank.ar(`[
			//FREQS
			[240*ExpRand(0.97, 1.02), 2020*ExpRand(0.97, 1.02), 3151*ExpRand(0.97, 1.02)]+(),
			//AMPS
			[-9,0,-5].dbamp,
			//PHASES
			[0.8, 0.07, 0.08]
		],
		//EXCITER - enveloped noise
		BPF.ar(PinkNoise.ar, 6500, 0.1) * env
		)*1;

	sig = source*env;

	// sig = AllpassN.ar(sig,0.05,0.05,1);

	// sig = Pan2.ar(sig,LFNoise1.kr(10));

	Out.ar(out,sig!2);
}).play;




//----------------------------------------------------------------------------
//----------------------------------------------------------------------------


// Wine Glass Sound
(
SynthDef("AM", { arg freq = 523.25, modfreq = 1, amp = 0.5, attack = 2, dur = 5, pos = 0;
	var carrier, modulator, env;
	modulator = SinOsc.kr(modfreq).range(0, 1);
	carrier = SinOsc.ar(freq: freq, mul: modulator);
	env = Env.perc(attackTime: attack, releaseTime: dur - attack, level: amp).kr(2);
	carrier = carrier * env;
	Out.ar(0, Pan2.ar(carrier, pos))
}).add;
)




{
var amp;
var sig;
sig = SinOsc.kr(200,0,0.5);

// sig = Line.kr(0,1,10);
amp = Amplitude.kr(sig);
	SendReply.kr(Impulse.kr(10),'/reply',amp);
	}.play;


OSCdef(\foo, {|msg, time, addr, recvPort|
	msg[3].postln;
}, '/reply');








