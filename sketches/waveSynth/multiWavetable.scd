//ALLOC
~waveBuf = {Buffer.alloc(s,2.pow(11))}!8;
~waveBuf.do{|buf,i|buf.sine1({gauss(0.0,1.0).clip(rrand(-1.0,0.0),rrand(0.0,1.0))}!rrand(1,20))};
// ~waveBuf.do(_.free);
//---------------------------------------------------------------
//---------------------------------------------------------------
SynthDef(\waveOscNoise, {|
	out=0,gate=1,dur=1,legato=1,
	freq=262|
	var env,sig;
	var = \lag.kr(0.1);
	var freqs = freq+([0,-1,1]*\detune.kr(0.1,dur*legato));

	// env = EnvGen.ar(Env.adsr(0.01,0.1,0.1,0.1),gate,doneAction:2);
	env = EnvGen.ar(Env.perc(0.01,dur*legato),gate);

	sig = VOsc3.ar(
		LFNoise1.kr(\rate.kr(7.5,4)).linlin(-1,1,\first.kr(0),\last.kr(0)),
		*freqs);

	sig = Splay.ar(sig);
	sig=sig*env*\amp.kr(0.3,0.1);

	Out.ar(out,sig);
}).add;
//---------------------------------------------------------------
//---------------------------------------------------------------

Pdef(\rLong,
		Pbind(
		\type, \note,
		\instrument, \waveOscNoise,
		\first,~waveBuf.first.bufnum,
		\last,~waveBuf.last.bufnum,
		\rate, Pwhite(0.01,5.0),
		// \amp,Pwhite(0.1,0.5),
		\detune, Pwhite(0.0,1.0),
		\midinote, 24,
		\legato, Prand([1,0.75]),
		\dur, Pseq([4,8])
	);
);

Pdef(\rRep,
	Pbind(
		\type, \note,
		\instrument, \waveOscNoise,
		\first,~waveBuf.first.bufnum,
		\last,~waveBuf.last.bufnum,
		\rate, Pwhite(0.01,5.0),
		\detune, Pwhite(0.0,1.0),
		\amp,Pwhite(0.3,0.7),
		\midinote, 24+[0,12],
		\legato, Pseq([2,2,2,3]),
		\dur, Pseq([0.25,0.25,0.25,Prand([1.25,1.25+2])])
	);
);

s.plotTree;
//---------------------------------------------------------------
//---------------------------------------------------------------
Pdef(\waveOscBass,
	Pwrand([
		Pdef(\rLong),Pdef(\rRep)
	],[0.2,0.8],
	inf);
);

Pdef(\waveOscBass).clock = t;
Pdef(\waveOscBass).quant=0;
Pdef(\waveOscBass).play(t);
Pdef(\waveOscBass).stop;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
Ndef(\waveOscBass, Pdef(\waveOscBass));

n[\knob0].cc={|val| Ndef(\waveOscBass).smap(\amp,val*val)};
n[\fader0].cc={|val| Ndef(\waveOscBass).smap(\first,val*7+~waveBuf.first.bufnum)};
n[\fader1].cc={|val| Ndef(\waveOscBass).smap(\last,val*7+~waveBuf.first.bufnum)};

n[\knob1].cc={|val| Ndef(\waveOscBass).smap(\rate,val*val *5 +0.1)};
n[\knob2].cc={|val| Ndef(\waveOscBass).smap(\detune,val*val*1.0 +0.01)};
// n[\fader6].cc={|val| Ndef(\waveOscBass).smap(\midinote,val*40+24)};
// n[\fader7].cc={|val| Ndef(\waveOscBass).smap(\index,val*val +0.01)};

n[\s0].on={1.postln; Ndef(\waveOscBass).play};
n[\s0].off={ Ndef(\waveOscBass).stop};

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------

//keyboard




