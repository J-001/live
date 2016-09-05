/*
Sketch: 04_06_16
*/

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

t = TempoClock(147.5/120).permanent_(true);

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

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


Pdef(\bassLoop,
	Pbind(
		\instrument,\play,
		\out, ~delay,
		\bufnum,b[\suzuki][0],
		\cut,Pseq([300,15000],inf),
		\startPos,Prand([0,0.125,0.25],inf),
		\amp,0.05,
		\rate,Pwrand([4,16,32,100],[0.75,0.1,0.1,0.05],inf),
		\legato,1.00,
		\break,Prand([0.25,0.125,0.0625],inf),
		\dur,Pfunc{|e|e[\bufnum].duration*e[\break]*e[\rate].reciprocal},
	)
);

Pdef(\bassLoop,
	Pbind(
		\instrument,\play,
		\out, ~reverb,
		\bufnum,b[\suzuki][0],
		\startPos,Prand([0,0.125,0.25],inf),
		\amp,0.05,
		\rate,Pwrand([4,16,32,100],[0.75,0.1,0.1,0.05],inf),
		\legato,1.00,
		\break,Prand([0.25,0.125,0.0625],inf),
		\dur,Pfunc{|e|e[\bufnum].duration*e[\break]*e[\rate].reciprocal},
	)
);

Pdef(\bassLoop,
	Pbind(
		\instrument,\play,
		\out, 0,
		\bufnum,b[\suzuki][0],
		\startPos,Prand([0],inf),
		\amp,0.2,
		\rate,1,
		\legato,1.00,
		\break,Prand([1],inf),
		\dur,Pfunc{|e|e[\bufnum].duration},
	)
);

Pdef(\bassLoop).clock=t;
Pdef(\bassLoop).quant=4;
Pdef(\bassLoop).play;
Pdef(\bassLoop).stop;

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
Pdef(\chopLoop_1,
	Pbind(
		\instrument,\play,
		\out, 0,
		\bufnum,b[\suzuki][0],
		\startPos,Pseq([0.0],Pwhite(8,16)),
		\amp,Pwhite(0.0,0.25),
		\rate,1,
		\legato,1.00,
		\break,Prand([1],inf),
		// \dur,Pfunc{|e|e[\bufnum].duration/64},
		\dur,Pfunc{|e|e[\bufnum].duration*(1/128)},
	)
);

Pdef(\chopLoop_2,
	Pbind(
		\instrument,\play,
		\out, 0,
		\bufnum,b[\suzuki][0],
		\startPos,Prand([
			[Pseq([0.0],32),Pseq([0.1],32)],
			[Pseq([0.0],24),Pseq([0.2],8)],
			[Pseq([0.0],16),Pseq([0.1],24)]
			].flatten
					,inf),
		\amp,Pwhite(0.0,0.25),
		\rate,1,
		\legato,1.00,
		\break,Prand([1],inf),
		// \dur,Pfunc{|e|e[\bufnum].duration/64},
		\dur,Pfunc{|e|e[\bufnum].duration*(1/128)},
	)
);

Pdef(\chopLoop_3,
	Pbind(
		\instrument,\play,
		\out, 0,
		\bufnum,b[\suzuki][0],
		\startPos,Prand((0..3)/10,Pwhite(1,4)),
		\amp,Pwhite(0.0,0.25),
		\rate,1,
		\legato,1.00,
		\break,Prand([1],inf),
		// \dur,Pfunc{|e|e[\bufnum].duration/64},
		\dur,Pfunc{|e|e[\bufnum].duration*(1/128)},
	)
);

Pdef(\chopLoop_4,
	Pbind(
		\instrument,\play,
		\out, 0,
		\bufnum,b[\suzuki][0],
		\startPos,Prand([
			[Pseq([0.0],32),Pseq([0.1],32)],
			[Pseq([0.0],24),Pseq([0.2],8)],
			[Pseq([0.0],16),Pseq([0.1],24)]
			].flatten
					,inf),
		\amp,Pwhite(0.0,0.25),
		\rate,1,
		\legato,1.00,
		\break,Prand([1],inf),
		// \dur,Pfunc{|e|e[\bufnum].duration/64},
		\dur,Pfunc{|e|e[\bufnum].duration*(1/128)},
	)
);



Pdef(\chopLoop,
	Pwrand([
		Pdef(\chopLoop_1),
		Pdef(\chopLoop_3)
		],
		[0.7,0.3],
		inf)
);

Pdef(\chopLoop).clock=t;
Pdef(\chopLoop).quant=4;
Pdef(\chopLoop).play;
Pdef(\chopLoop).stop;


//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

SynthDef(\break, {|
	out=0,bufnum,rate=1,startPos=0.0,
	amp=1,gate=1,
	atk=0.01,sus=1,rel=0.1,
	loop=0,done=2,pan=0,cut=3000,break=0.0001|
	var sig;
	var env;
	env = EnvGen.kr(Env.asr(releaseTime:0,sustainLevel:sus,releaseTime:0),gate,doneAction:2);
	sig = PlayBuf.ar(2,bufnum,rate:rate*BufRateScale.kr(bufnum),trigger:Impulse.kr(break),startPos:startPos*BufSamples.kr(bufnum),loop:loop,doneAction:0);
	sig = Pan2.ar(sig,pan,2,-1);

	// sig = BPF.ar(sig,cut,0.1);
	// sig = SinOsc.ar(SinOsc.ar(100,0,100)+100)*sig;
	sig = sig*amp;
	Out.ar(out,sig*env);
}).add;


Pdef(\breakLoop,
	Pbind(
		\instrument,\break,
		\out, 0,
		\bufnum,b.choose.choose,
		\startPos,Pseq([0.1,0.25,0.5],inf),
		\amp,0.1,
		\rate,Pseq([0.5,1,1.5,2,4],inf),
		\legato,1.01,
		\break,Prand([1,0.5,2,4,16],inf),
		// \dur,Pfunc{|e|e[\bufnum].duration/64},
		\dur,Prand([0.25,0.5],inf),
	)
);


Pdef(\breakLoop).clock=t;
Pdef(\breakLoop).quant=0;
Pdef(\breakLoop).play;
Pdef(\breakLoop).stop;

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

j=();

j[\1]=NodeProxy.audio(s,2);
j[\1].play;
j[\1][0] = Pdef(\breakLoop);
j['1'][1]=\filter->{|sig|
	sig*SinOsc.kr(2.5,SinOsc.kr(10))
};
j['1'][2]=\filter->{|sig|
	Pan2.ar(Mix(sig[0]),LFNoise1.kr(3))
};



//
j[\chop2]=NodeProxy.audio(s,2);
j[\chop2].play;
j[\chop2][0] = Pdef(\breakLoop);
j['chop2'][1]=\filter->{|sig|
	sig*SinOsc.kr(2.5,SinOsc.kr(10))
};
j['chop2'][2]=\filter->{|sig|
	Pan2.ar(Mix(sig[0]),LFNoise1.kr(3))
};


//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
SynthDef(\playXY, {|
	out=0,bufnum,rate=1,startPos=0.0,
	amp=1,gate=1,
	atk=0.01,sus=1,rel=0.1,
	loop=0,done=2,pan=0,cut=3000|
	var sig;
	var env;
	env = EnvGen.kr(Env.asr(releaseTime:0,sustainLevel:sus,releaseTime:0),gate,doneAction:2);
	sig = PlayBuf.ar(2,bufnum,rate:rate*BufRateScale.kr(bufnum)*MouseY.kr*100,startPos:startPos*BufSamples.kr(bufnum),loop:loop,doneAction:0);
	sig = Pan2.ar(sig,pan,2,-1);

	sig = BPF.ar(sig,cut,0.1);
	// sig = SinOsc.ar(SinOsc.ar(100,0,100)+100)*sig;
	Out.ar(out,sig*env);
}).add;

Pdef(\chirp,
	Ppar({|i|Pbind(
			\instrument,\playXY,
			\out, [0,0,Pwrand([0,~reverb],[0.85,0.15])][i],
			\bufnum,b[\suzuki][0],
			\startPos,Prand([0],inf),
			\amp,0.5,
			\cut,Pwhite(300,15000),
			\rate,Prand([4,16,32],inf),
			\legato,0.2,
			\break,Prand([1],inf),
			// \dur,Prand([0.125,0.25,0.5]),
			\dur,Prand([0.25]),
		)}!3,inf)
);

Pdef(\chirp,
	Ppar({|i|Pbind(
			\instrument,\playXY,
			\out, [0,0,Pwrand([0,~reverb],[0.85,0.15])][i],
			\bufnum,b[\suzuki][0],
			\startPos,Prand([0],inf),
			\amp,0.5,
			\cut,Pwhite(300,15000),
			\rate,Prand([4,16,32],inf),
			\legato,0.25,
			\break,Prand([1],inf),
			\dur,Prand([0.125,0.25,0.5])
		)}!3,inf)
);

Pdef(\chirp).clock=t;
Pdef(\chirp).quant=4;
Pdef(\chirp).play;
Pdef(\chirp).stop;
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

~delay = Bus.audio(s,2);
// ~g0 = Group();

SynthDef(\delay, {|out=0,in=5,rate=(1/3),length=8|
	var sig;
	sig = In.ar(in,2);
	sig = AllpassC.ar(sig,rate,rate,length);
	Out.ar(out,sig);
}).add;

~delaySynth=Synth.tail(s,\delay,[\in,~delay,\rate,1,\length]);
~delaySynth.free;
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

~reverb = Bus.audio(s,2);
// ~g0 = Group();

SynthDef(\reverb, {|out=0,in=0,mix=0.5,cutoff=5000|
	var sig;
	var local;

	sig =In.ar(in,2);
	local = LocalIn.ar(2)+sig;

	15.do{
		local = AllpassC.ar(local,0.06,rrand(0.01,0.06),3)};

	LocalOut.ar(local*0.4);

	sig = LPF.ar(local,cutoff);
	sig=  (sig*(mix))+(local*(1-mix));

	Out.ar(out,sig+local);
}).add;

~reverbSynth=Synth.tail(s,\reverb,[\in,~reverb,\rate,0.1,\length,4,\mix,0.1,\cutoff,1000]);
~reverbSynth.free;

s.plotTree

PdefAllGui

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

SynthDef(\resAir, {|out=0,freq=262,atk=0.01,rel=0.1,gate=1,dur,legato|
	var sig;
	var scale;
	var env;
	env = EnvGen.ar(Env([0,1,0],[atk,dur].normalizeSum*dur*legato,-4),gate,doneAction:2);
	scale = Scale.aeolian.degrees;
	sig = RLPF.ar(PinkNoise.ar*0.1,scale.midicps+freq,0.01);
	sig = Splay.ar(sig)*env;

	Out.ar(out,sig);
}).add;

Pdef(\resAir,
	Pbind(
		\out,~reverb,
		\instrument,\resAir,
		\legato,0.25,
		\atk,0.01,
		\midinote,96+[12,0,-12,-24]+
		Pseq([
			Pseq(
			[7!3,
			0!3,
			5!3,
			2!3
			].flat),
			Pseq([7!3,
				0!3,7!3,7].flat)

		],inf),

		\dur,Pseq([Pseq([0.5,0.5,1],7),2],inf)
	)
);


Pdef(\resAir).clock=t;
Pdef(\resAir).quant=4;
Pdef(\resAir).play;
Pdef(\resAir).stop; 