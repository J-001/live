/*
Sketch: 25_05_16
*/
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
//SETUP STUFF
t = TempoClock(72/60).permanent_(true)

t.tempo

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
SynthDef(\wobble, {|
	out=0,
	freq=262,
	gate=1,
	lag=0,
	amp=0.1|

	var car1;

	var mod;
	var env;
	var sig;
	var grainEnv;

	env = EnvGen.kr(Env.asr(0.05,1,0.5),gate,doneAction:2);

	grainEnv = EnvGen.kr(Env.perc(0.1,0.1,1),Impulse.kr(3),doneAction:0);

	sig = Saw.ar(Lag.kr(freq,lag)*[1,1.01,0.99,0.98,1.02]);
	sig = Splay.ar(sig);
	sig = RLPF.ar(sig,Lag.kr(freq,lag),0.01);
	// sig = BPF.ar(sig,Lag.kr(freq,lag),0.1);
	sig =  sig*env*amp*grainEnv;

	// sig = sig*SinOsc.kr(5,0,0.5,0.5);
	Out.ar(out,sig);

}).add;



Pdef(\wobble,
Pbind(
	\instrument,\wobble,
	\midinote,Pseq({Scale.aeolian.degrees.choose}!10,inf)+[28,40],
	\legato,1,
	\dur,Prand(((0..2)*0.25),inf)
	)
)

Pdef(\wobble,
	Pmono(
	\wobble,
	// \midinote,Prand({Scale.aeolian.degrees.choose}!10,inf)+[28,40],
	\midinote,Prand({Scale.aeolian.degrees.choose}!10,inf)+48+[0,3,10],
	\lag,0.1,
	\amp,0.025,
	\dur,Prand(((0..2)*0.5),inf)
	)
)


Pdef(\wobble).quant=4;
Pdef(\wobble).play(t);
Pdef(\wobble).stop
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

SynthDef(\fmBass, {|out=0,freq=20, carPartial=1,modPartial=1,modInd=1,gate=1,amp=0.3|
	var car;
	var mod;
	var env;
	var sig;
	var car2;
// *[0.99,1,1.01]
	env = EnvGen.kr(Env.adsr(0.05,0.01,0.75,0.1),gate,doneAction:2);
	mod = SinOsc.ar(freq*modPartial,0,modInd*freq);
	car = SinOsc.ar((freq*carPartial*[0.99,1,1.01])+mod);

	sig = car*env*amp;

	sig = sig*SinOsc.kr(30).range(0.9,1);

	sig = Splay.ar(sig);

	Out.ar(out,sig);
}).add;


~bassRhythms = [1,0.25,0.75,1,1,1,1,1,1];
~bassRhythms = [1,0.25,0.75,1,1,1,1,1,1];

~bassArtic = [0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,1]*1;

Pdef(\fmBass,
	Pbind(
	\instrument,\fmBass,
	// \freq,10000,
	\freq,[8,4,2,1],
	\legato,Pseq(~bassArtic,inf),
	// \legato,1,
	\dur,Pseq(~bassRhythms*0.5,inf),
	\dur,1,
	\carPartial,15,
	\modPartial,12,
	\modInd,75,
	\amp,0.3
	)
);


Pdef(\fmBass).quant=0;
Pdef(\fmBass).play(t);
Pdef(\fmBass).stop


k=nil!10;

k=k.collect{|k|k=NodeProxy(s)}

k[0].source={LFNoise1.kr(0.1,100,500)};
k[1].source={LFNoise1.kr(0.1,1,5)};
k[2].source={LFNoise1.kr(0.1,100,500)};
k[3].source={LFNoise1.kr(0.1,1,500)};

~fmb=NodeProxy.audio(s,2);
~fmb.play;
~fmb[0] = Pbind(
	\instrument,\fmBass,
	\freq,k[0],
	// \freq,[8,4,2,1],
	\legato,Pseq(~bassArtic,inf),
	// \legato,1,
	\dur,Pseq(~bassRhythms*0.5,inf),
	\dur,1,
	\carPartial,k[1],
	\modPartial,k[2],
	\modInd,k[3],
	\amp,0.3
	)

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
