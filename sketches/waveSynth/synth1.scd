//
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
SynthDef(\fmPiano, { |
	out=0,
	freq=220,
	carRatio=1,modRatio=14,index=18,
	detune=0.1,
	rel=0.2,
	gate=1,vibrato=2,dur=0.5,legato=0.8|

	var sig;
	var car,mod;
	var sustainCar;
	var attackEnv;
	var mainEnv;
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

	mainEnv = EnvGen.ar(Env.perc(0.01,dur*legato*rel,1),gate,doneAction:2);

	sig=sig*mainEnv;

	lfo = SinOsc.kr(vibrato).linlin(-1,1,0,1);
	sig = sig*lfo;

	sig = sig*\amp.kr(0.1,lag);

	Out.ar(out,sig);
},variants:(
	basic:[modRatio:24,index:18,detune:0.1])
).add;

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------

Pdef(\calm,
	Pbind(
		\type,\note,
		\instrument,\fmPiano,
		\midinote,Pseq(
				{Pseq([Prand(Scale.dorian.degrees,2),7])}!5
			,inf)+48,
		\amp,0.5,
		\legato,1,
		\rel,5,
		\dur,0.5

		)
)

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
a.calm=nil;
a[\calm]=NodeProxy.audio(s,2);
a[\calm].play;
a[\calm].playN(outs:FX(\reverb).outs,group:FX(\reverb).group,addAction:\addToHead);
a.calm.clock=t;
a.calm.quant = 0;
a[\calm].source_(Pdef(\calm));


n[\knob4].cc={|val|a.calm.set(\amp,val*val)};
n[\knob5].cc={|val|a.calm.set(\vibrato,val*val*10+0.01)};
n[\knob6].cc={|val|
	var newVal;
	newVal = val*val*2+0.05;

	a.calm.fadeTime=newVal;
	a.calm.set(\rel,newVal)};
// n[\knob7].cc={|val|a.calm.set(\midinote,val*24+24)};



n[\fader4].cc=nil;
n[\fader5].cc=nil;
n[\fader6].cc=nil;
n[\fader7].cc=nil;

n[\s4].on=nil;
n[\s4].off=




n[\knob4].cc={|val|a.calm.set(\amp,val*val)};
n[\knob5].cc={|val|a.calm.set(\vibrato,val*val*10+0.01)};
n[\knob6].cc={|val|
	var newVal;
	newVal = val*val*2+0.05;

	a.calm.fadeTime=newVal;
	a.calm.set(\rel,newVal)};
// n[\knob7].cc={|val|a.calm.set(\midinote,val*24+24)};



n[\fader4].cc={|val|a.calm.set(\detune,val*val*10+0.01)};
n[\fader5].cc={|val|a.calm.set(\carRatio,val*val*100+0.01)};
n[\fader6].cc={|val|a.calm.set(\modRatio,val*val*100+0.01)};
n[\fader7].cc={|val|a.calm.set(\index,val*val*100+0.01)};

n[\s4].on={
	a.calm.play};
n[\s4].off={
	a.calm.stop};


































