SynthDef(\cutmix, {|out=0, amp=0.1|
	var sig, in, noise;
	var env;
	var trig;

	trig = \trig.kr(0);
	trig = trig.clip(0,1).round(1);

	in = \in.ar([0,0]);
	noise = \noise.ar([0,0]);

	sig = (in * trig) + (noise * (1 - trig));

	Out.ar(out, sig);
}).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
Ndef(\cutmix, \cutmix);

Ndef(\cutTrig,{ LFNoise0.kr(0.4) });

Ndef(\cutNoise, {
	ClipNoise.ar(\amp.kr(0.1))!2
});

(
	\cutmix
	<<.trig \cutTrig
	<<.noise\cutNoise
);
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
SynthDef(\cutmixN, {|out=0, amp=0.1|
	var sig, in, noise;
	var env;
	var trig;

	trig = \trig.kr(0);
	trig = trig.clip(0,1).round(1);

	in = \pre_in.ar([0,0]);
	noise = \noise.ar([0,0]);

	sig = (in * trig) + (noise * (1 - trig));

	Out.ar(out, sig);
}).add;