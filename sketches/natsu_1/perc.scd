//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
/* SYNTHDEFS */
SynthDef(\clap, {|
	out=0,
	freq=0,
	gate=1,amp=0.5|
	var sig;
	var source;
	var trig;
	var env;
	var masterEnv;

	masterEnv = EnvGen.kr(Env.asr(0.001,1,0.01),gate,doneAction:2);
	env = EnvGen.kr(Env([0,1,0],[0.001,0.999]*0.35,[1,-7]),gate,doneAction:0);	

	source = WhiteNoise.ar*env;
	source = BPF.ar(source,{rrand(500,1000)}!5+freq,0.75);

	sig = source*env;

	sig = AllpassC.ar(sig,0.1,0.05,0.1);

	sig = Pan2.ar(sig,{LFNoise1.kr(rrand((-1.0),1.0))}!10);
	sig = sig*amp;
	Out.ar(out,sig);
}).add;

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
//pFrags

p[\drums]=();

p.drums[\rhythms] = [
	Pseq([0.5,0.25,0.25]),
	Pseq([0.5,0.25,0.25]*0.5),
	Pseq(0.5!4)
	];

(
	var r;
p.drums.push;
	r = ~rhythms;
	~complex = [
		Pseq([r[1]!6,r[2]*0.5].flatten);
	];

currentEnvironment.pop;
)

p.drums[\clap] = Pdef(\clap,
	Pbind(
		\instrument,\clap,
		\amp,Pwhite(0.4,0.5),
		\dur,Prand(p.drums[\rhythms]++p.drums[\complex],inf)
	)
	);

a=();
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
a[\drums]=NodeProxy(s);
a[\drums].play;
a[\drums].clock=t;
a[\drums].quant=4;
a[\drums][0] = p.drums[\clap];

s.meter;
a[\drums].stop;
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------










