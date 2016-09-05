SynthDef(\clap, {|out=0,freq=0,gate=1|
	var sig;
	var source;
	var trig;
	var env;
	var masterEnv;
	masterEnv = EnvGen.kr(Env.asr(0.001,1,0.01),gate,doneAction:2);
	env = EnvGen.kr(Env([0,1,0],[0.001,0.999]*0.5,[1,-7]),gate,doneAction:0);	
	source = WhiteNoise.ar*env;
	source = BPF.ar(source,{rrand(800,1800)}!20+freq,{rrand(0.05,0.07)}!20);
	sig = Splay.ar(source)*env;
	sig = AllpassC.ar(sig,0.01,0.0075,0.08);
	Out.ar(out,sig!2);
}).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
SynthDef(\kick, {|out=0|
	var sig;
	Out.ar(out,sig);
}).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
Pdef(\clap,
	Pbind(
		\instrument,\clap,
		\freq,Pwhite(-100,100),
		\out,0,
		\amp,Pwhite(0.05,0.2)*(1/Pkey(\freq)),
		\dur,Pseq({|maxLength,vals|
			var currentLength=0;
			var arr = [];
			while({currentLength<maxLength}, {
				var nextVal=vals.choose;
				arr = arr++nextVal;
				currentLength=currentLength+nextVal;
			});
			arr.postln;
			}.(8,[2,1]),inf)
		)
	)

Pdef(\clap).play

Ndef(\clap,Pdef(\clap)).play;
Ndef(\clap)[1]=\filter->{|in|GVerb.ar(in,100,30,0.7)};Window