//DISKOUT
SynthDef(\diskout, {arg bufnum;
    DiskOut.ar(bufnum, \in.ar([0,0]));
}).add;


b[\beginRec] = {
	"BEGINNING RECORDING".postln;
	b[\disk] = Buffer.alloc(s, 65536, 2);
	b.disk.write("~/Live/rec/chick.wav".standardizePath, "WAV", "int16", 0, 0, true);
	~diskout = Synth.tail(nil, \diskout, [\bufnum, b.disk]);
};

b[\stopRec] = {
	"BEGINNING ENDING".postln;
	~diskout.free;
	b.disk.close;
	b.disk.free;
};

n[\rec].on_({b[\beginRec]});
n[\rec].off_({b[\stopRec]});
