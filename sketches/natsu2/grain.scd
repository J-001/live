SynthDef(\sampler1, {|
	out=0,bufnum,rate=1,startPos=0.0,
	amp=1,gate=1,
	atk=0.01,sus=1,rel=0.1,
	loop=1,done=2,pan=0,dur=0.5,legato=1|
	var sig;
	var env;
	// env = EnvGen.kr(Env.asr(releaseTime:atk,sustainLevel:sus,releaseTime:rel),gate,doneAction:2);
	env = EnvGen.ar(Env.triangle(dur*legato),gate:gate,doneAction:2);
	sig = PlayBuf.ar(1,bufnum,rate:rate*BufRateScale.kr(bufnum),startPos:startPos*BufSamples.kr(bufnum),loop:loop,doneAction:0);
	sig = Pan2.ar(sig,pan,2,-1);
	Out.ar(out,sig*env);
}).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
SynthDef(\sampler2, {|
	out=0,bufnum,rate=1,startPos=0.0,
	amp=1,gate=1,
	atk=0.01,sus=1,rel=0.1,
	loop=1,done=2,pan=0|
	var sig;
	var env;
	env = EnvGen.kr(Env.asr(releaseTime:atk,sustainLevel:sus,releaseTime:rel),gate,doneAction:2);
	sig = PlayBuf.ar(2,bufnum,rate:rate*BufRateScale.kr(bufnum),startPos:startPos*BufSamples.kr(bufnum),loop:loop,doneAction:0);
	sig = Pan2.ar(sig,pan,2,-1);
	Out.ar(out,sig*env);
}).add;


b.piano[1].play;
(b.piano[0].numFrames/s.sampleRate)



Pdef(\gran,
    Pbind(
    	\bufnum,b.piano[0],
        \instrument, \sampler2,
        \rate,[1,9/8].sign,
        \legato,2,
        \dur, Pseq([0.0125],160),
        \startPos,Pseq([
			Pseq([0.0],160),
			Pseq([0.15],160)],inf)
        	+Pwhite(0.01,0.05)
    )
);

Pdef(\pno,
	Pseq([Pdef(\gran)],inf)
	)

Pdef(\gran).play;
Pdef(\pno).play;
Pdef(\pno).stop;


Pdef(\spos,Pbind(
	\startPos, Pseq([
			Pseq([0.0],160*6),
			Pseq([0.15],160*2)
			],inf)
        	+Pwhite(0.01,0.05)
	));

Pdef(\main,Pdef(\spos)<>Pdef(\pno))
Pdef(\main).play(t,quant:8);

// Pdef(\main2,Pdef(\spos)<>Pdef(\pno))
// Pdef(\main2).play(t);
Pdef(\main).stop;

Pdef(\pno).play
Pdef(\spos)<>Pdef(\pno)
Pdef(\pno)<>Pdef(\spos)

s.plotTree;
Pdef(\gran).play;

2/0.0125