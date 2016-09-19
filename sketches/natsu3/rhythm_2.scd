SynthDef(\pulsehit, {|out=0,gate=1,freq=1000,width=0.5,rel=0.5,amp=0.1|
	var sig,env;
	var freqs, widths;
	var osc=();

	env = EnvGen.ar(Env.perc(0.01,rel),gate,doneAction:2);

	freqs = freq+[0.01,0,0.01.sign];
	widths = (width+[0.25.sign,0,0.25]).clip(0,1);
	osc[\pulse] = Splay.ar(Saw.ar(freqs).mean);

	sig = RLPF.ar(osc[\pulse],freqs,0.05);
	Out.ar(out,sig*env*amp);
}).add;

Pdef(\pulsehit,
    Pbind(
    	\type,\note,
        \instrument, \pulsehit,
        \midinote,Pseq([-24,Pseq({Scale.aeolian.degrees.choose}!5)],inf)+60,
        \amp,0.01,
        \dur, Pseq([
        	0.75,0.25,0.25,0.25,0.25,0.25
        	],inf)
    )
).play(quant:4);

Pdef(\pulsehit2,
    Pbind(
    	\type,\note,
        \instrument, \pulsehit,
        \midinote,Pseq([-24,Pseq({Scale.aeolian.degrees.choose}!5)],inf)+72,
        \amp,0.01,
        \dur, Pseq([
        	0.25,0.25,0.25,0.25,0.25,0.75,0.25
        	],inf)
    )
).play(quant:4);

Pdef(\pulsehit3,
    Pbind(
    	\type,\note,
        \instrument, \pulsehit,
        \midinote,Pseq([-24,Pseq({Scale.aeolian.degrees.choose}!5)],inf)+72,
        \amp,0.01,
        \dur, Pseq([
        	0.25,0.25,0.25,0.25,0.75,0.25
        	],inf)
    )
).play(quant:4);

Pdef(\pulsehit).clear;
Pdef(\pulsehit2).clear;
Pdef(\pulsehit3).clear;

Pdef(\pulsehit).fadeTime=32;
Pdef(\pulsehit2).fadeTime=32;
Pdef(\pulsehit3).fadeTime=32;

Pdef(\pulsescale,
    Pbind(
        \instrument, \pulsehit,
        \scale,Scale.aeolian.degrees,
        \degree,Prand((0..10),inf),
        \octave,7,
        \amp,0.01,
        \dur, Pseq([
        	1,
        	Pseq([0.125],3/0.125)
        	],inf)*2
    )
).play(quant:4);

Pdef(\pulsescale).stop;

s.plotTree;