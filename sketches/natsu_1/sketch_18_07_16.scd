/*
Sketch: 18_07_16
*/
/*
Structure:

	Melody:

	Harmony:

	Percussion:

	Bass:

	SoundWorld/SoundElements: 
		- electronic percussion;
		- bass;
		- rich harmony;

Documentation:

	Starting Point - 

		- fmPiano - Made some pattern Fragments (pFrags) and kept them in an array;

	Developments - 

	Refinement - 

*/
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
/* SYNTHDEFS */
SynthDef(\fmPiano, { |
	out=0,
	freq=220,
	carRatio=1,modRatio=14,index=18,
	detune=0.1,
	rel=0.2,
	amp=1,gate=1,vibrato=2,dur=0.5,legato=0.8|

	var sig;
	var car,mod;
	var sustainCar;
	var attackEnv;
	var mainEnv;
	var freqs;
	var lfo;

	freqs = freq*(1-[detune,0,detune.sign]);

	attackEnv = EnvGen.ar(Env([0,1,0],[0.01,0.99]*0.1,[2,-2]),doneAction:0);

	mod=SinOsc.ar(freq*modRatio,0,freq*index);
	car = SinOsc.ar((freqs*carRatio) + mod);
	car = car*attackEnv*0.5;

	sustainCar = SinOsc.ar(freqs);

	sig=car+sustainCar;

	mainEnv = EnvGen.ar(Env.perc(0.01,dur*legato,1),gate,doneAction:2);

	sig=sig*mainEnv;

	lfo = SinOsc.kr(vibrato);
	sig = sig*lfo;

	sig = sig*amp;

	Out.ar(out,sig);
},variants:(
	basic:[modRatio:24,index:18,detune:0.1])
).add;
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
//pFrags

p[\fmPiano]=();

p.fmPiano[\stab] = Pdef(\stab,
	Pbind(
		\instrument,\fmPiano,
		\midinote,Prand(Scale.minorPentatonic.degrees,inf)+[72,84].choose,
		\amp,Pwhite(0.3,0.5),
		\dur,Pseq([0.75,Prand([0.25,0.75],inf)])
		)
	);


p.fmPiano[\held] = Pdef(\held,
	Pbind(
		\type,\on,
		\out,\reverb,
		\instrument,\fmPiano,
		\modRatio,7,
		\index,1,
		\numNotes,Pwhite(1,4),
		// \midinote,Pfunc{|e|{Scale.minorPentatonic.degrees.choose+[48,60].choose}!e[\numNotes]},
		\midinote,Pseq({Scale.majorPentatonic.degrees.choose}!6,inf)+[60,63,67,70],
		\amp,Pwhite(0.3,0.5),
		\legato,0.8,
		// \dur,Prand([2],inf)
		\dur,Pseq([0.75,0.75,0.75,0.75,0.5,0.5],inf)
		)
	);


p.fmPiano[\stab].clock=t;
p.fmPiano[\stab].quant=4;
p.fmPiano[\stab].play;
p.fmPiano[\stab].stop;

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
a[\fmPiano]=NodeProxy(s);
a[\fmPiano].playN();
// a[\fmPiano].stop;
a[\fmPiano].clock=t;
a[\fmPiano].quant=4;
a[\fmPiano].source = p.fmPiano[\held];

a[\fmPiano][1] = \filter->{|in| FreeVerb.ar(in,0.4,0.9,0.95);}
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------












