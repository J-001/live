//ALLOC
~waveBuf = {Buffer.alloc(s,2.pow(11))}!8;
~waveBuf.do{|buf,i|buf.sine1({gauss(0.0,1.0).clip(rrand(-1.0,0.0),rrand(0.0,1.0))}!rrand(1,20))};
// ~waveBuf.do(_.free);
//---------------------------------------------------------------
//---------------------------------------------------------------
SynthDef(\wtOscPerc, {|
	out=0,gate=1,dur=1,legato=1,
	freq=262,
	atk=1,rel=1,curve=(-2)|
	var env,sig;

	var freqs = freq+([0,-1,1]*\detune.kr(0.1,dur*legato));

	// env = EnvGen.ar(Env.adsr(0.01,0.1,0.1,0.1),gate,doneAction:2);
	env = EnvGen.ar(Env.perc(attackTime:atk,releaseTime:rel,curve:curve),gate,doneAction:2);

	sig = VOsc3.ar(
		LFNoise1.kr(\rate.kr(7.5,4)).linlin(-1,1,\first.kr(0),\last.kr(0)),
		*freqs);

	sig = Splay.ar(sig);
	sig=sig*env*\amp.kr(0.3,0.1);

	Out.ar(out,sig);
}).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------

