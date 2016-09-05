/*
Sketch: 10_07_16
*/




~aeolianRatios={|i|((Scale.aeolian.degrees[i].midicps*2.5)/8.1757989156437)}!7


{
	var sig;

	sig=SoundIn.ar(0);
	Out.ar([0,1],sig);

}.play;


{
	var sig;
	var mod;
	var modAmp;

	// modAmp = Amplitude.kr(sig);
	mod = SinOsc.ar((Scale.aeolian.degrees).midicps*0.25);

	mod = SinOsc.kr(
		// SinOsc.kr(1).linlin(-0.5,0.5,0,1)*1
		4
		,0,0.5,0.5).linlin(0,1,-1,1);
	sig=SoundIn.ar(0);

	sig = sig*mod;

	sig = Splay.ar(sig);

	Out.ar([0,1],sig);
 
}.play;

SynthDef(\guitarChords, {|out|
	var sig;
	var ps;

	sig=SoundIn.ar(0);

	ps = ({|i|((Scale.aeolian.degrees.midicps[0..3]*1.5)/8.1757989156437)}!7).collect{|ratio|PitchShift.ar(sig,0.1,ratio)};

	sig = [ps].flatten;

	sig = Splay.ar(sig);

	Out.ar(out,sig);

}).add;

Pmono(\guitarChords).play

~eg2 = {
	
	Out.ar([0,1],sig);

};



SynthDef(\guitarRLPF, {|out=0,distort=0.9|
	var sig;
	var mod;
	var modAmp;

	sig=SoundIn.ar(0);
	// sig = RLPF.ar(sig,cut,0.05);
	sig = sig.fold(0,distort);
	sig = PitchShift.ar(sig,0.01,0.5);

	Out.ar([0,1],sig);

}).add;

Pdef(\guitarRLPF,
	Pbind(
	\instrument,\guitarRLPF,
	// \cut,Prand([200,400,800],inf),
	\distort,0.5,
	\dur,1
	)
)



Pdef(\guitarRLPF).stop;
Pdef(\guitarRLPF).play;

s.meter


{
	var sig;
	var mod;
	var modAmp;
	var local;

	sig=SoundIn.ar(0);
	local = LocalIn.ar(2)+sig.dup;

	15.do{
		local = AllpassN.ar(local,0.06,rrand(0.001,0.03),MouseY.kr(0.5,4));
	};

	LocalOut.ar(local*MouseX.kr(0,1));

	sig = sig+local;

	sig = LPF.ar(sig,4000);

	Out.ar([0,1],sig);

}.play;


SynthDef(\guitarFX, {|out|
	var sig;
	var mod;
	var modAmp;
	var fb;
	var local;

	sig=SoundIn.ar(0);
	local = LocalIn.ar(2)+sig.dup;

	15.do{|i|
		local = PitchShift.ar(local,0.1,2);
		// local = SinOsc.kr(i*10,0,0.5,0.5)*sig;
		local = AllpassN.ar(local,0.06,rrand(0.001,0.2),MouseY.kr(0.5,4));
	};

	LocalOut.ar(local*MouseX.kr(0,1));

	Out.ar(out,sig+local);

}).add;

Pmono(\guitarChords,\out,[0,1]).play;
Pmono(\guitarFX).play;

Pmono(\default).play

{
	var sig;

	sig=SinOsc.ar*0.5;
	Out.ar([0,1],sig);

}.play;


s.options.inDevice_("UMC404HD 192k");
s.options.outDevice_("UMC404HD 192k");
s.reboot;

s.options.sampleRate_(96000);

s.plotTree

s.meter



//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

r{
~buffer = Buffer.alloc(s, s.sampleRate.nextPowerOfTwo,2);
4.do{
	{SinOsc.ar*EnvGen.ar(Env.perc(0.01,1,0.1),doneAction:2)}.play;
	1.wait;
};
~buffer.write("~/Live/rec/guitarP_1.wav".standardizePath,"wav",leaveOpen:true);
p = Pmono('guitarFX',\out,[0,1]).play;
d = Synth(\diskOut,[\bufnum,~buffer]);

4.wait;

d.free;
p.stop;
~buffer.close;
~buffer.free;


	
}.play;
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

~buffer=Buffer.read(s,"~/Live/rec/guitarChords.wav".standardizePath);

~buffer.play;

x = Synth(\diskIn,[\bufnum,~buffer]);

m = {|buf| x.addToHeadMsg(nil,[\bufnum,buf])};
~buffer = Buffer.cueSoundFile(s,"~/Live/rec/guitarChords.wav".standardizePath,0,2,completionMessage:m)

Buffer.freeAll
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------


~record = {
		var in;
			
			in = In.ar(0,2);
			EnvGen.ar(Env([1,1],[30]),doneAction:2);
			RecordBuf.ar(in,r[\eg]);

		}

~record.play;
~eg2.play;



s.meter;
s.plotTree;




SynthDef(\diskOut, {|out=0,bufnum,in|
	DiskOut.ar(bufnum, In.ar(0,2));
}).add;

SynthDef(\diskIn, {|out=0,bufnum,in|
	DiskIn.ar(2,bufnum);
}).add;















