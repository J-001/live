SynthDef(\cymbal, {|out=0,rate=0.5|
	var sig;
	var lodriver, locutoffenv, hidriver, hicutoffenv, freqs, res, thwack;
	var trig = Impulse.kr(rate);
	
	locutoffenv = EnvGen.ar(Env.perc(0.1, 1,curve:-20),trig) * 20000 + 1000;
	lodriver = LPF.ar(WhiteNoise.ar(0.1), locutoffenv);
	
	hicutoffenv = 20001 - (EnvGen.ar(Env.perc(1, 10),trig) * 10000);
	hidriver = HPF.ar(WhiteNoise.ar(0.1), hicutoffenv);
	hidriver = hidriver * EnvGen.ar(Env.perc(1, 2, 0.25),trig);
	
	//DULLER ATTACK
	// thwack = LPF.ar(WhiteNoise.ar*0.5*EnvGen.ar(Env.perc(0.001,0.1,1),200));
	//BRIGHTER ATTAK
	thwack = HPF.ar(WhiteNoise.ar*0.5*EnvGen.ar(Env.perc(0.001,0.1,1),1000));
	
	freqs  = {exprand(300, 20000)}.dup(100);
	res    = Ringz.ar(lodriver + hidriver + thwack, freqs).mean;
	
	sig = ((res * 1) + (lodriver * 2) + thwack).dup*EnvGen.ar(Env.perc(0.01,30 , 0.25,-40),trig,doneAction:0);
	Out.ar(out,sig);

}).add;

Pdef(\cymbal,
		Pmono(\cymbal,
			\dur,Pseq([2],inf))
).play;


s.plotTree;

{
	Ringz.ar(
	PinkNoise.ar(0.1), 
	{exprand(300, 20000)}.dup(100)
	).mean 
}.play;

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
//Cymbal
y = {|t_trig=1|
	var lodriver, locutoffenv, hidriver, hicutoffenv, freqs, res, thwack;
	
	locutoffenv = EnvGen.ar(Env.perc(0.1, 10,curve:-10),t_trig) * 20000 + 1000;
	lodriver = LPF.ar(BrownNoise.ar(0.1), locutoffenv);
	
	hicutoffenv = 20001 - (EnvGen.ar(Env.perc(1, 10),t_trig) * 10000);
	hidriver = HPF.ar(BrownNoise.ar(0.1), hicutoffenv);
	hidriver = hidriver * EnvGen.ar(Env.perc(1, 2, 0.25),t_trig);
	
	//DULLER ATTACK
	// thwack = LPF.ar(WhiteNoise.ar*0.5*EnvGen.ar(Env.perc(0.001,0.1,1),200));
	//BRIGHTER ATTAK
	thwack = HPF.ar(WhiteNoise.ar*0.5*EnvGen.ar(Env.perc(0.001,0.1,1),1000),t_trig);
	
	freqs  = {exprand(300, 20000)}.dup(100);
	res    = Ringz.ar(lodriver + hidriver + thwack, freqs).mean;
	
	((res * 1) + (lodriver * 2)  + thwack).dup*EnvGen.ar(Env.perc(0.01,10 , 0.25,-20),t_trig,doneAction:2);
};

y.set(\t_trig,1)
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
//KICK
x ={
	var lodriver, locutoffenv, hidriver, hicutoffenv, freqs, res, thwack;
	
	locutoffenv = EnvGen.ar(Env.perc(0.1, 0.5)) * 200 + 40;
	lodriver = LPF.ar(WhiteNoise.ar(0.1), locutoffenv);
	
	//DULLER ATTACK
	thwack = LPF.ar(WhiteNoise.ar*0.5*EnvGen.ar(Env.perc(0.001,0.1,1),200));
	//BRIGHTER ATTAK
	
	freqs  = {rand(41, 1000)}.dup(50);
	res    = Ringz.ar(lodriver + thwack, freqs).mean;
	
	((res * 1) + (lodriver * 2) + thwack).dup*EnvGen.ar(Env.perc(0.01, 1, 0.25,-10),doneAction:2);
};
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------


//-----------------------------------------------------------------------
//-----------------------------------------------------------------------

//klang cymbal
c = {

	var freqs, amps,rings;
	
};


