SynthDef(\rhodes,{|out= 0 freq = 440 amp = 0.1 gate=1 lforate = 4.85 lfowidth= 0.5 cutoff= 2000 rq=0.5 pan = 0.0|
	var pulse, filter, env;
	pulse = Pulse.ar(freq*[1,33.5.midiratio],[0.2,0.1],[0.7,0.3]);
	env = EnvGen.ar(Env.adsr(0.01 ,1.0,0.8,3.0),gate,doneAction:2);
	//keyboard tracking filter cutoff
	filter = BLowPass4.ar(pulse,(cutoff*(env.squared))+200+freq,rq);
	Out.ar(out,Pan2.ar(Mix(filter)*env*amp,pan));

}).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
//chorus and compression
SynthDef(\choruscompress, {|out =0, gate= 1|
	var source = \in.ar(0!2);
	var chorus;
	var env = Linen.kr(gate, 0.1, 1, 0.1, 2);

	chorus= Splay.ar(Array.fill(4,{
		var maxdelaytime= rrand(0.005,0.02);

		DelayC.ar(source[0], maxdelaytime,LFNoise1.kr(Rand(0.1,0.6),0.25*maxdelaytime,0.75*maxdelaytime) )
	}));
	chorus = Compander.ar(4*(source + chorus),source,0.4,1,4) * \amp.kr(0.1);
	XOut.ar(out,env,chorus);
}).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
Pdef(\rhodes,
	Pbind(
		\instrument,\rhodes,
		\midinote,Pseq([12,8,7,0,[-12,4,12],[-12,4,12],[-12,4,12],[-11,5,12],[-11,5,12],[-9,7,12],7]+48,inf),
		\dur,Pseq([
			0.5,0.5,0.5,0.5,
			1.5,1.0,1.0,
			1.0,1.0,2.0,0.5],inf),
		// \cutoff,Pstutter(11,Pn(Pseries(500,500,4),inf)),
		\cutoff, Pkey(\midinote) + 700
	)
);
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
SynthDef(\tremolo, {|out|
	var mix = \mix.kr(0.25);
	var dry = \in.ar([0,0]);
	var wet = dry * SinOsc.kr(\lforate.kr(1)).linlin(-1,1,\lfodepth.kr(0),1);
	(dry * (1 - mix)) + (wet * mix) * \amp.kr(0.1);
	Out.ar(out,(dry * (1 - mix)) + (wet * mix) )
	}, metadata: (
		\specs : (
			mix: [0,1].asSpec,
			lforate: [0.001, 20].asSpec,
			lfodepth: [1,0].asSpec )
		)
).add;