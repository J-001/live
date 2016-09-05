/*
Sketch: 02_07_16
*/

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
SynthDef(\fm,
 {|out=0,freq=262,carRatio=1,modRatio1=1,modRatio2=1,index1=1,index2=1,amp=0.3,gate=1|
	var car;
	var mod1;
	var mod2;

	var newFreq;

	// newFreq = (Lag.kr(freq,0.5)+[0,3,7,10]).midicps;
	newFreq = Lag.kr(freq,\lag.kr(0.5));

	mod1 = SinOsc.ar(newFreq*modRatio1,0,newFreq*index1);

	mod2 = SinOsc.ar(newFreq*modRatio2,0,newFreq*index2)*SinOsc.kr(10,0,0.5,0.5);

	car = SinOsc.ar((newFreq*carRatio)+mod1+mod2);

	// car=car*EnvGen.ar(Env.asr(0.01,1,0.01),gate,doneAction:2);
	car=car*EnvGen.kr(Env.perc(),gate,doneAction:2);
	Out.ar(out,Splay.ar(car)*amp*0.15);

}).add;

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
~fd = [
	//[midinoteArr,durArr]
	[[0,-2,0,3,5],[3,5+2,2,2,2]],
	[[0,-2,0,-2,0,-2,0,-2,0,-2,0,-2,0,-2,0,-7],1!16]
];

p[\pad1] =  Pdef(\pad,
	Pmono(
		\fm,
		\midinote,Pseq([
			Pseq(~fd[0][0],4),
			// Pseq(~fd[1][0],4),
			],inf)+60+[0,3,7,10],
		\dur,Pseq([
				Pseq(~fd[0][1],4),
				// Pseq(~fd[1][1],4),
				],inf)*0.25,
		\modRatio1,1,
		\modRatio2,1,
		\index1,1,
		\index2,1,
		\lag,1
	)
);
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
a[\pad1] = NodeProxy.audio(s,2);
// e[0] ={|freq=200,amp=0.5,add=0| SinOsc.ar(freq+add)*amp};
a[\pad1][0]=\fm;

a[\pad1][1] = \set-> Pbind(
		\midinote,Pseq([
			Pseq(~fd[0][0],4),
			// Pseq(~fd[1][0],4),
			],inf)+60+[0,3,7,10],
		\dur,Pseq([
				Pseq(~fd[0][1],4),
				// Pseq(~fd[1][1],4),
				],inf)*0.25,
		\modRatio1,1,
		\modRatio2,1,
		\index1,1,
		\index2,1,
		\lag,1
);
a[\pad1][1]=nil;

a[\pad1][0] = p[\pad1];
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
SynthDef(\ping, {|out=0,freq=262,rq=0.01,gate=1,amp=0.5,dur=1,legato=1|
	var sig;
	var env;
	var sourceEnv;

	env = EnvGen.ar(Env.triangle(dur*legato),gate,doneAction:2);
	sig = PinkNoise.ar;
	sig = RLPF.ar(sig,freq,rq);
	sig = BPF.ar(sig,freq,0.001);
	sig = sig*amp*env;

	sig = Pan2.ar(sig,SinOsc.kr(\pan.kr(0)).range(-1,1));

	Out.ar(out,sig);	
}).add;
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
k[\pad1]=NodeProxy(s);

Pdef(\pingA,
	Pbind(
		\instrument,\ping,
		\amp,k,
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
a[\pad2] = NodeProxy.audio(s,2);
// e[0] ={|freq=200,amp=0.5,add=0| SinOsc.ar(freq+add)*amp};
a[\pad2][0]=Pdef(\pingA);
// a[\pad2][0]=\ping;

