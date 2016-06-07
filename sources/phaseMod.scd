

SynthDef(\phaseMod, {|out=0|
	var sig;
	var mod;

	sig = SinOsc.ar();
	Out.ar(out,sig);
}).play;