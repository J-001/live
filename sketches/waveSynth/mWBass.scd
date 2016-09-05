//---------------------------------------------------------------
//---------------------------------------------------------------

Pdef(\noiseWave,
	Pbind(
		\instrument,Prand([
			\wwvhpf,
			\wwvlpf,
			\wwvhpfsin,
			\wwvlpfsin,
			\wwvhpflf1,
			\wwvlpflf1],inf),
		\lfoSpeed,Pwhite(0.1,2.0),
		\legato,Pwhite(2,4),
		\dur,Prand(2*(1..4),inf),
		)
	)

Pdef(\noiseWave).clock = t;
Pdef(\noiseWave).quant=;
Pdef(\noiseWave).play;
Pdef(\noiseWave).stop;


n[\play].on_({1.postln},false);
n[\play].removeFromTree


n[\fader1].cc_({1.postln},false);
n[\fader1].removeFromTree;
n[\fader1].name;

ServerTree.dict.postln;





