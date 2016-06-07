
//COMPRESSION

SynthDef(\compressor, {|out=0,in=0|

	
}).add;


//MASTER VOLUME
SynthDef(\master, {|out=0,in=0,amp=0.1|
	var sig;
	sig = In.ar(in,1);
	sig = sig*amp;
	ReplaceOut.ar(out,sig);
}).add;


~master = Synth.after(s,\master,[\in,0,\out,0]);
~master.set(\amp,0.05)
~master.free;


MasterEQ()

