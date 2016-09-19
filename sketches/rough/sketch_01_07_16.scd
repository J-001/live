/*
Sketch: 01_07_16
*/

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
SynthDef(\pmSynth, {|out=0,gate=1|
	var sig;
	var env;
	var mod;
	var lfo;
	var lag = Lag.kr(\lag.kr(0.1));

	// env = EnvGen.ar(Env.perc(0.01,0.05),gate,doneAction:0);
	env = EnvGen.ar(Env.asr(0.01,1,0.1),gate,doneAction:0);

	lfo = SinOsc.kr(\lfoRate.kr(1,lag));
	mod = SinOsc.kr(\modRate.kr(1,lag))*pi*2;
	sig = SinOsc.ar(\freq.kr(262,lag),mod)*\amp.kr(0.4,lag)*env;
	sig = Pan2.ar(sig,LFNoise1.kr(10.5,2,-1));
	Out.ar(out,sig);
}).add;


k=();
k[\pm]=NodeProxy(s)!4;

k.pm[0].source={SinOsc.kr().linlin(-1,1,0.1,1)};

k.pm[1].source={SinOsc.kr(4).linlin(-1,1,0.5,1)*0.2};


Pdef(\pmSynth,
	Ppar(
		{
			Pmono(
				// \instrument,\pmSynth,
				\pmSynth,
				\gate,0,
				\out,[0,1],
				\lag,Prand((0..4)/8,inf),
				\lfoRate,Pwhite(4.5,10.0),
				\modRate,Pwhite(0.5,1.0),
				\amp, 0.1,
				\midinote, Prand(Scale.majorPentatonic.degrees,inf)+60+(12*rrand(-3,-3))+
					Scale.aeolian.degrees+36,
				\legato,1,
				\dur,Prand([1,2,4,0.5]*0.25,inf)
				)
		}!1
	)
);

Pdef(\pmSynth).clock=t;
Pdef(\pmSynth).quant=0;
Pdef(\pmSynth).play;
Pdef(\pmSynth).stop;

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------