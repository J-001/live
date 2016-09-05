
SynthDef(\samplerM, {|
	out=0,bufnum,rate=1,startPos=0.0,
	amp=1,gate=1,
	atk=0.01,sus=1,rel=0.1,
	loop=1,done=2,pan=0,dur=1,legato=1|
	var sig;
	var env;
	env = EnvGen.kr(Env.asr(attackTime:atk,releaseTime:rel),gate,doneAction:2);
	sig = PlayBuf.ar(1,bufnum,rate:rate*BufRateScale.kr(bufnum),startPos:startPos*BufSamples.kr(bufnum),loop:loop,doneAction:0);
	sig = Pan2.ar(sig,pan,2,-1);
	Out.ar(out,sig*env*amp);
}).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
SynthDef(\samplerS, {|
	out=0,bufnum,rate=1,startPos=0.0,
	amp=1,gate=1,
	atk=0.01,sus=1,rel=0.1,
	loop=1,done=2,pan=0,dur=1,legato=1|

	var sig;
	var env;
	env = EnvGen.kr(Env.asr(attackTime:atk,releaseTime:rel),gate,doneAction:2);
	sig = PlayBuf.ar(2,bufnum,rate:rate*BufRateScale.kr(bufnum),startPos:startPos*BufSamples.kr(bufnum),loop:loop,doneAction:0);
	sig = Pan2.ar(sig,pan,2,-1);
	Out.ar(out,sig*env*amp);
}).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------


Pdef(\rev,
	Pbind(
		\bufnum,Prand(b.choose.choose,inf),
		\instrument,Pfunc{|e|
			var synth;
			synth  = if (e[\bufnum].numChannels==1, 
			{\samplerM},
			{\samplerS});
			synth.postln;
			synth;
		},
		\loop,1,
		\amp,0.5,
		\rate,1,
		\legato,1.5,
		\dur,Pfunc{|e|
			e[\bufnum].duration
		}
	)	
)

Pdef(\rev).stop
Pdef(\rev).play


Pdef(\klangPn,
	Pbind(
		\bufnum,b.kit.ride[1],
		\instrument,Pfunc{|e|
			var synth;
			synth  = if (e[\bufnum].numChannels==1, 
			{\samplerM},
			{\samplerS});
			synth.postln;
			synth;
		},
		\loop,0,
		\amp,Pseq([Pwhite(0.1,0.2,1),Pwhite(0.05,0.15,2)],inf)+0.5,
		\rate,Pwhite(0.99,1.01)*Pseq([1,2.0,1.5,2.5],inf)*(10),
		\legato,1.1,
		\dur,Pseq([3,2,1],inf)/6
		)
	).play(t,quant:8)

Pdef(\klangPn).stop;

Pdef(\klangEndPn,
	Pbind(
		\bufnum,Prand(b.kit.ride20++b.kit.kik2++b.kit.choose,inf),
		\instrument,Pfunc{|e|
			var synth;
			synth  = if (e[\bufnum].numChannels==1, 
			{\samplerM},
			{\samplerS});
			synth.postln;
			synth;
		},
		\loop,1,
		\amp,Pseq([Pwhite(0.1,0.2,1),Pwhite(0.05,0.15,2)],inf)+0.75,
		\rate,Pwhite(0.99,1.01)*Prand([40,10.5,100,2.5],inf)*0.1,
		\legato,0.5,
		\dur,Pseq([
			Prand([1,1.5,2.0],3)/6
			],inf)*4
		)
	).play(t,quant:2)

Pdef(\klangEndPn).fadeTime = 8;
Pdef(\klangEndPn).play;
Pdef(\klangEndPn).stop;
Pdef(\klangEndPn).clear;




