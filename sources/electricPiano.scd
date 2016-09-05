
SynthDef("electricPiano", { |
	out=0,
	freq=220,
	carRatio=1,modRatio=24,index=18,
	rel=0.2,
	amp=1,gate=1|
	var car,mod;
	var sustainCar;
	var attackEnv;
	var mainEnv;
	var freqs;
	var lfo1;

	freqs = freq*[0.99,1,1.01];

	attackEnv = EnvGen.kr(Env([0,1,0],[0.01,0.99]*0.1,[2,-2]),doneAction:0);
	// mainEnv = EnvGen.kr(Env([0,1,0],[0.01,0.99]*1,[2,-2]),doneAction:0);
	mod=SinOsc.ar(freqs*modRatio,0,freqs*index);
	car = SinOsc.ar((freqs*carRatio) + mod);
	sustainCar = SinOsc.ar(freqs);
	car = car*attackEnv*0.5;
	car=car+sustainCar;


	mainEnv = EnvGen.kr(Env.asr(0.01,1,rel),gate,doneAction:0);
	car=car*mainEnv;

	lfo1 = SinOsc.kr(2);
	car = car*lfo1;

	car = FreeVerb.ar(car,0.5,0.8,0.95);
	// car = Pan2.ar(car,SinOsc.kr(4).range(-1,1));
	car = car*amp;
	DetectSilence.ar(car,doneAction:2);

	Out.ar(out,car);
}).add;

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

MIDIClient.init;
MIDIIn.connectAll;
MIDIdef.all;

~keys = nil!127;

MIDIdef.noteOn(\noteOn, {|val, num, chan, src|

	var timbre;
	var vol;
	var release;

	timbre = val.linexp(1,127,1,100);
	vol = val.linexp(1,127,0.01,1);
	release = val.linlin(1, 127, 0.1, 0.7);

	~keys[num-1] = Synth(\electricPiano, [\freq, num.midicps,\index,timbre,\amp,vol]);
	
});

MIDIdef.noteOff(\noteOff, {|val, num, chan, src|

	~keys[num-1].release;
	
})

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

~scale = Scale.harmonicMinor;
~scale.tuning_(\partsch);
~octaves = 5;
~fundOct = 2;

~arp=({|oct| 
	var currentOctave;
	currentOctave = ((oct+~fundOct)*12);

	currentOctave+~scale.degrees;

	}!~octaves).flat;



Pdef(\sweep,
	Pbind(\instrument,\electricPiano,
	\amp,Pwhite(0.0,0.5),
	\midinote, Pxrand(~arp,inf),
	\dur,0.125);
);

Pdef(\sweep).quant=0.5;
Pdef(\sweep).play;
Pdef(\sweep).stop;

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

Pdef(\harmonics,
	Pbind(
	\instrument,\electricPiano,
	\amp,Pwhite(0.1,0.5),
	\midinote, 
	Pseq(
		[Pseq([Pseq(~arp[0]!16),Pseq(~arp[1..4])],3),
				Pseq({\rest}!20,1)],10
		),
	\dur,Prand((1..2)*0.125,inf)
	)
);

Pdef(\harmonics).quant=4;
Pdef(\harmonics).play;
Pdef(\harmonics).stop;


~currentNote = ~arp.size.rand;
~melodicStream = Pfunc{~arp[~currentNote];~currentNote=~currentNote+[-1,1].choose}.asStream;
~melodicPhrase =~melodicStream.nextN(8);
~phraseDur=(Prand((1..4),inf)).asStream.nextN(10.rand)*0.125;
~phraseDur.sum;

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
Pdef(\fmMel,
	Pbind(
	\instrument,\electricPiano,
	\amp,1,
	\midinote,Pseq(~melodicPhrase,inf)+48,
	\legato,0.7,
	\dur,Pseq(~phraseDur,inf)
	)
);

Pdef(\fmMel).quant=~phraseDur.sum;
Pdef(\fmMel).play;
Pdef(\fmMel).stop;

~currentNote = ~arp.size.rand;
~melodicStream = Pfunc{~arp[~currentNote];~currentNote=~currentNote+[-1,1].choose}.asStream;
~melodicPhrase =~melodicStream.nextN(8);
~phraseDur=(Prand((1..4),inf)).asStream.nextN(10.rand)*0.125;
~phraseDur.sum;

Pdef(\fmMel2,
	Pbind(
	\instrument,\electricPiano,
	\amp,1,
	\midinote,Pseq(~melodicPhrase,inf)+48,
	\legato,0.2,
	\dur,Pseq(~phraseDur,inf)
	)
);

Pdef(\fmMel2).quant=~phraseDur.sum;
Pdef(\fmMel2).play;

~temp =4!nil;

r{
	3.do{|i|
		var scale = Scale.aeolian.degrees;
		var degs = [0,2,4,6];
		Synth(\electricPiano, [\freq, (scale[degs[i]]+60).midicps,\index,1,\modRatio,18,\amp,0.3]);
		(0.3.rand+0.5).wait;
	}
	}.play;


	r{

	Scale.aeolian.degrees.do{|deg|
		var degs = [0,2,4,6];
		Synth(\electricPiano, [\freq, (deg+60).midicps,\index,1,\modRatio,18,\amp,0.1]);
		(0.4.rand+005).wait;
	}
	}.play;

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------


























	s.plotTree;