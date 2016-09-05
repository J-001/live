t=TempoClock(90/60).permanent_(true);

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
SynthDef(\fmPiano, { |
	out=0,
	freq=220,
	carRatio=1,modRatio=14,index=18,
	detune=0.1,
	rel=0.2,
	gate=1,vib=2,dur=0.5,legato=0.8|

	var sig;
	var car,mod;
	var sustainCar;
	var attackEnv, mainEnv;
	var freqs;
	var lfo;
	var lag;

	lag = \lag.kr(0.1);
	freqs = freq*(1-[detune,0,detune.sign]);

	attackEnv = EnvGen.ar(Env([0,1,0],[0.01,0.99]*0.1,[2,-2]),doneAction:0);

	mod=SinOsc.ar(freq*modRatio,0,freq*index);
	car = SinOsc.ar((freqs*carRatio) + mod);
	car = car*attackEnv*0.5;

	sustainCar = SinOsc.ar(freqs);

	sig=car+sustainCar;

	mainEnv = EnvGen.ar(Env.perc(0.01,dur*legato*rel,1),gate,doneAction:0);

	sig=sig*mainEnv;

	lfo = SinOsc.kr(vib).linlin(-1,1,0,1);
	sig = sig*lfo;

	sig = sig*\amp.kr(0.1,lag);

	Out.ar(out,sig);
},variants:(
	basic:[modRatio:24,index:18,detune:0.1]),
).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
SynthDef(\fmPianoASR, { |
	out=0,
	freq=220,
	carRatio=1,modRatio=14,index=18,
	detune=0.1,
	rel=0.2,
	amp=0.1,
	gate=1,vib=2,dur=0.5,legato=0.8|

	var sig;
	var car,mod;
	var sustainCar;
	var attackEnv, mainEnv;
	var freqs;
	var lfo;
	var lag;

	lag = \lag.kr(0.1);
	freqs = freq*(1-[detune,0,detune.sign]);
	mainEnv = EnvGen.ar(Env.asr(0.01,1,0.1),gate,doneAction:2);
	attackEnv = EnvGen.ar(Env([0,1,0],[0.01,0.99]*0.1,[2,-2]),doneAction:0);

	mod=SinOsc.ar(freq*modRatio,0,freq*index);
	car = SinOsc.ar((freqs*carRatio) + mod);
	car = car*0.5;

	sustainCar = SinOsc.ar(freqs);

	sig=car+sustainCar;

	// mainEnv = EnvGen.ar(Env.asr(0.01,1,0.1),gate,doneAction:2);

	sig=sig*mainEnv;

	lfo = SinOsc.kr(vib).linlin(-1,1,0,1);
	sig = sig*lfo;

	sig = sig*amp;

	Out.ar(out,sig);
},variants:(
	basic:[modRatio:24,index:18,detune:0.1]),
).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
// k=();
// k[\fmKeys]=();
k.fmKeys[\amp] = NodeProxy().source_({|rate=4,mul=0.5,add=0.1|LFNoise1.kr(rate,mul,add)});
k.fmKeys[\amp] = NodeProxy().source_({|dur=32,mul=0.5,add=0|
	EnvGen.kr(Env.perc(dur,0,1))*mul;
});
k.fmKeys[\amp].set(\rate,1);

k.fmKeys[\amp].source={|dur=32,mul=0.5,add=0|
	XLine.kr(0.001,1,dur,doneAction:2);

};

Pdef(\fmKeys,
	Pbind(
		\instrument,\fmPianoASR,
		\scale,Scale.aeolian.degrees,
		\degree,Pseq([
			Pseq((-2..4),16),
			Pseq((-3..3),16),
			],inf),
		// \amp,k.fmKeys.amp,
		\amp,0.1,
		\dur,0.125
		)
);

Pdef(\fmKeys).reset;
Pdef(\fmKeys).clock=t;
Pdef(\fmKeys).quant=16;
Pdef(\fmKeys).play;
Pdef(\fmKeys).stop;


{var main;
	EnvGen.ar(Env.perc(16,0,0),doneAction:2);
	SinOsc.ar(220)*EnvGen.ar(Env.perc(),Impulse.kr(1),doneAction:2)
}.play
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
//MIDIKEY


MIDIClient.init;

MIDIIn.connect(0);

~fmPianoKeys=nil!128;

MIDIdef.noteOn(\test,{|vel,num,chan,uid|
	uid.postln;
})

MIDIdef.noteOn(\fmKeyOn,{|vel,num,chan,uid|

	~fmPianoKeys[num] = Synth(\fmPianoASR,[
		\freq,num.midicps,
		\rel,0.1,
		\atk,0.5.rand,
		\detune,1.0.rand,
		\amp,(vel/128)*0.5,
		\carRatio,1.0.rand,
		\modRatio,10.0.rand,
		\index,10.0.rand,
		\vib,0.5.rand]);

	~fmPianoKeys[num].size.postln

}).permanent_(true);

MIDIdef.noteOff(\fmKeyOff,{|vel,num,chan,uid|
	~fmPianoKeys[num].release;

}).permanent_(true);



//-----------------------------------------------------------------------
//-----------------------------------------------------------------------