
SynthDef(\harmonics, {|out=0,freq=262,amp=0.5|
	var sig,env;
	var partials,lfos;

	env = EnvGen.ar(Env.perc, doneAction: 0);

	partials = {|n|
	var pFreq, pAmp;
	var normN = n.linlin(0,49,0,1);
	var fund = 65.40639132515;
	var maxOffset, rndOffset;

	maxOffset = 5;
	
	pFreq = fund*2.pow(n) ;

	[rand(fund,fund*10000),
	rrand(fund,fund*10000),
	exprand(fund,fund*10000),
	pFreq
	].choose
	}!50;

	lfos = {LFNoise0.kr(100,10)}!50;


	// sig = SinOsc.ar(partials,{rrand(0,pi)}!50).mean;
	sig = Ringz.ar(
		BPF.ar(LFNoise1.ar(1000)*0.05),
		partials).mean;
	sig = Splay.ar(sig)*amp;

	Out.ar(out,sig);
}).play;

//HARMONIC
{|n|
	var pFreq, pAmp;
	var normN = n.linlin(0,49,0,1);
	var fund = 65.40639132515;
	var maxOffset, rndOffset;

	maxOffset = 5;
	
	pFreq = fund*2.pow(n) ;

	rndOffset = pFreq * rrand(0,maxOffset) * normN;
	pFreq = pFreq + rndOffset;

	}!50;
//RANDOM
	{|n|
	var pFreq, pAmp;
	var normN = n.linlin(0,49,0,1);
	var fund = 65.40639132515;
	var maxOffset, rndOffset;

	maxOffset = 5;
	
	pFreq = fund*2.pow(n) ;

	rndOffset = pFreq * rrand(0,maxOffset) * normN;
	pFreq = pFreq + rndOffset;

	}!50;

s.meter