SynthDef(\play, {|
	out=0,bufnum,rate=1,startPos=0.0,
	amp=1,gate=1,
	atk=0.01,sus=1,rel=0.1,
	loop=0,done=2,pan=0,cut=3000|
	var sig;
	var env;
	env = EnvGen.kr(Env.asr(releaseTime:0,sustainLevel:sus,releaseTime:0),gate,doneAction:2);
	sig = PlayBuf.ar(2,bufnum,rate:rate*BufRateScale.kr(bufnum),startPos:startPos*BufSamples.kr(bufnum),loop:loop,doneAction:0);
	sig = Pan2.ar(sig,pan,2,-1);

	// sig = BPF.ar(sig,cut,0.1);
	// sig = SinOsc.ar(SinOsc.ar(100,0,100)+100)*sig;
	sig = sig*amp;
	Out.ar(out,sig*env);
}).add;
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
SynthDef(\break, {|
	out=0,bufnum,rate=1,startPos=0.0,
	amp=1,gate=1,
	atk=0.01,sus=1,rel=0.1,
	loop=0,done=2,pan=0,break=0|
	var sig;
	var env;
	env = EnvGen.kr(Env.asr(attackTime:0.5,sustainLevel:sus,releaseTime:0),gate,doneAction:2);
	sig = PlayBuf.ar(2,bufnum,
			rate:BufRateScale.kr(bufnum)*rate,
			startPos:startPos*BufSamples.kr(bufnum),
			trigger:Impulse.kr(break),
			loop:loop,
			doneAction:0);
	sig = Pan2.ar(sig,pan,2,-1);

	// sig = BPF.ar(sig,cut,0.1);
	// sig = SinOsc.ar(SinOsc.ar(100,0,100)+100)*sig;
	sig = sig*amp;
	Out.ar(out,sig*env);
}).add;
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
SynthDef(\lpfDrum, {|
	out=0,bufnum,rate=1,startPos=0.0,
	amp=1,gate=1,
	atk=0.01,sus=1,rel=0.1,
	loop=0,done=2,pan=0,break=0,freq=262,dur=1,legato=1|
	var sig;
	var env;
	// env = EnvGen.kr(Env.asr(attackTime:0.1,sustainLevel:sus,releaseTime:0),gate,doneAction:2);
	env = EnvGen.ar(Env.triangle(dur*legato),gate,doneAction:2);
	sig = PlayBuf.ar(2,bufnum,
			rate:BufRateScale.kr(bufnum)*rate,
			startPos:startPos*BufSamples.kr(bufnum),
			trigger:Impulse.kr(break),
			loop:loop,
			doneAction:0);
	sig = RLPF.ar(sig,freq,0.05);

	// sig = BPF.ar(sig,cut,0.1);
	// sig = SinOsc.ar(SinOsc.ar(100,0,100)+100)*sig;
	sig = sig*amp;
	Out.ar(out,sig*env);
}).add;
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
t=TempoClock(72/60);
~drums=();

~drums[\break] = Pdef(\break,{
	Pbind(
	\instrument,\break,
	\amp,0.05,
	\bufnum,b[\drums][0],
	\loop,1,
	\startPos,Pwhite(0.0,1.0),
	\rate,Pwhite(0.5,10.0),
	\break,Prand([1,2,4,16]),
	\dur,Pfunc{|e|e[\bufnum].duration})
	});

~drums[\groove] = Pdef(\groove,{
	Pbind(
	\instrument,\break,
	\amp,0.25,
	\bufnum,b[\drums][0],
	\loop,1,
	\dur,Pfunc{|e|e[\bufnum].duration})

});

~drums[\tom] = Pdef(\tom,{
	Pbind(
	\instrument,\break,
	\bufnum,b[\drums][1],
	\loop,1,
	\amp,0.05,
	\rate,Pwhite(0.5,2.0)*Prand([1,-1],inf),
	// \startPos,Pwhite(0.0,1.0),
	\startPos,0.0,
	\dur,Pwhite(0.25,1.0))
});

~drums[\bassTom] = Pdef(\bassTom,{
	Pbind(
	\instrument,\lpfDrum,
	\bufnum,b[\drums][1],
	\loop,0,
	\amp,Pwhite(0.3,0.75),
	\midinote,Pxrand(Scale.majorPentatonic.degrees,inf)+[48,60,53],
	\rate,0.5,
	\startPos,Pwhite(0.0,1.0),
	\legato,2,
	\startPos,0.0,
	\dur,Prand([0.25,0.75],inf))
});

(
	var base = 48;
	var octaves = 2;
	var degrees = Scale.aeolian.degrees; 

	~aeolian = {|oct|
		degrees.collect{|deg|
			(oct*12)+base+deg;
		};
	}!octaves;
)



~drums[\rev_tom] = Pdef(\rev_tom,{
	Pbind(
	\instrument,\break,
	\bufnum,b[\drums][1],
	\loop,1,
	\amp,1,
	\rate,Prand((1..2)/10,inf)*(-2),
	// \startPos,Pwhite(0.0,1.0),
	\startPos,0.0,
	\dur,1/8)
});

~drums[\hi_rev_tom] = Pdef(\hi_rev_tom,{
	Ppar(
		{|ind|
			Pbind(
		\type,Pwrand([\rest,\note],[0.4,0.6],inf),
	\instrument,\break,
	\bufnum,b[\drums][ind],
	\loop,1,
	\amp,0.2,
	// \rate,Prand((1..10)/1,inf)*(-1),
	\rate,rrand(0.5,10.9),
	// \startPos,Pwhite(0.0,1.0),
	\startPos,0.0,
	\dur,1)}!2,inf)
});

~drums[\tom].clock=t;
~drums[\hi_rev_tom].clock=t;
~drums[\bassTom].clock=t;
~drums[\rev_tom].clock=t;
~drums[\break].clock=t;

~drums[\tom].quant=4;
~drums[\hi_rev_tom].quant=4;
~drums[\bassTom].quant=4;
~drums[\rev_tom].quant=4;
~drums[\break].quant=4;

~drums[\tom].play;
~drums[\hi_rev_tom].play;
~drums[\bassTom].play;
~drums[\rev_tom].play;
~drums[\break].play;




~drums[\groove].play;

~drums[\groove].pause;

~drums[\tom].stop;
~drums[\rev_tom].stop;
~drums[\break].stop;
~drums[\groove].stop;

~drumSet = Pdef(\drumSet,
	Ppar(~drums)
	);


j=();
j[\drum]=NodeProxy.audio(s,2);
j[\drum].play;
j[\drum].stop;
j[\drum][0] = ~drumSet;

j['drum'][1]=\filter->{|sig|
	sig=sig*0.3;

	FreeVerb.ar(sig,0.3,0.8);
};





