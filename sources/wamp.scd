

SynthDef(\bass, {|out=0,freq=440,amp=0.1,gate=1|
	var sig;
	var env;
	env = EnvGen.kr(Env.adsr(0.1,0.01,1,0.3),gate,doneAction:2);
	sig = Mix(SinOsc.ar(freq*[0.99,1,1.01]))*amp*env;
	Out.ar(out,sig!2);
}).add;


n=NodeProxy.audio(s,2).fadeTime_(0);
n.play;
n.quant=2;

n.stop;
n=nil
n[0]= Pbind(
	\instrument,\bass,
	\freq,80,
	\amp,0.4,
	\dur,Prand([
			Pseq([0.5,0.5])
			],inf),
	\legato,0.1
	)

n[1]=\filter->{|in|
	FreeVerb.ar(in,1,1);
};


OSCdef(\bassK, {|msg, time, addr, recvPort|
	msg[1].postln;

}, '/jd/bass/vib');


