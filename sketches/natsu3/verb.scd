b.piano[1].play

(
Ndef(\verb, {	
	var input, output, delrd, sig, deltimes;
	
	// Choose which sort of input you want by (un)commenting these lines:
	input = Pan2.ar(PlayBuf.ar(1, b.bell.hand[0],rate:1, loop: 0), -0.5); // buffer playback, panned halfway left
	//input = SoundIn.ar([0,1]); // TAKE CARE of feedback - use headphones
	//input = Dust2.ar([0.1, 0.01]); // Occasional clicks
	
	// Read our 4-channel delayed signals back from the feedback loop
	delrd = LocalIn.ar(4);
	
	// This will be our eventual output, which will also be recirculated
	output = input + delrd[[0,1]];
	
	sig = output ++ delrd[[2,3]];
	// Cross-fertilise the four delay lines with each other:
	sig = ([ [1,  1,  1,  1],
	         [1, -1,  1, -1],
	         [1,  1, -1, -1],
	         [1, -1, -1,  1]] * sig).sum;
	// Attenutate the delayed signals so they decay:
	sig = sig * ([0.4, 0.37, 0.333, 0.3]*1.2);
	
	// Here we give delay times in milliseconds, convert to seconds,
	// then compensate with ControlDur for the one-block delay 
	// which is always introduced when using the LocalIn/Out fdbk loop
	deltimes = [101, 143, 165, 177] * 0.001 - ControlDur.ir;
	
	// Apply the delays and send the signals into the feedback loop
	LocalOut.ar(DelayC.ar(sig, deltimes, deltimes));
	
	// Now let's hear it:
	Out.ar(0, output);
	
}).play
)



//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
Ndef(\spaceverb,{|
	out = 0, gate = 1, threshold = 0.1 amp=0.9 mix = 0.1, len = 3|

	var a,c,z,y,in,sig;
	var env = Linen.kr(gate, 0.1, 1, 0.1, 2);

	c = 10; // number of comb delays
	a = 10; // number of allpass delays

	in = \in.ar([0,0]);
	// reverb predelay:
	z = DelayN.ar(in, 0.01,0.01);

	y=Mix.arFill(c,{CombL.ar(z,0.2,rrand(0.01, 0.2),len)});

	// chain of 4 allpass delays on each of two channels (8 total) :
	a.do({ y = AllpassN.ar(y, 0.051, [rrand(0.001, 0.03),rrand(0.001, 0.032)], 1) });

	sig = (in*(1-mix))+(y*mix);

	Out.ar(out,Pan2.ar(sig,0)*env*amp);

});

Ndef(\spacereverb).set(\amp,0.1);
Ndef(\spacereverb).set(\mix,1);
Ndef(\spacereverb).play
Ndef(\spacereverb).stop

s.plotTree;



{
	var sig, decay;
	sig = SinOsc.ar*EnvGen.ar(Env.perc)*0.1;
	decay  =0.5;
	sig = sig+(DelayN.ar(sig,2,2)*decay);
	sig = sig+(DelayN.ar(sig,2,2)*decay);

}.play;
b.bell.hand[0].play
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------

Ndef(\x,x).play;

Ndef(\y,{|t_trig=1|
	var lodriver, locutoffenv, hidriver, hicutoffenv, freqs, res, thwack;
	var trig=Dust.kr(1);
	locutoffenv = EnvGen.ar(Env.perc(0.1, 1,curve:-1),trig) * 20000 + 100;
	lodriver = LPF.ar(WhiteNoise.ar(0.1), locutoffenv);
	
	hicutoffenv = 20001 - (EnvGen.ar(Env.perc(1, 10),trig) * 10000);
	hidriver = HPF.ar(WhiteNoise.ar(0.1), hicutoffenv);
	hidriver = hidriver * EnvGen.ar(Env.perc(1, 2, 0.25),trig);
	
	//DULLER ATTACK
	// thwack = LPF.ar(WhiteNoise.ar*0.5*EnvGen.ar(Env.perc(0.001,0.1,1),200));
	//BRIGHTER ATTAK
	thwack = HPF.ar(WhiteNoise.ar*0.5*EnvGen.ar(Env.perc(0.01,0.1,1),10000),trig);
	
	freqs  = {exprand(300, 20000)}.dup(100);
	res    = Ringz.ar(lodriver + hidriver + thwack, freqs).mean;
	
	((res * 1) + (lodriver * 2)  + thwack).dup*EnvGen.ar(Env.perc(0.01,10 , 0.25,-20),trig,doneAction:2);
});

Ndef(\spacereverb)<<>.in Ndef(\x).play;
Ndef(\spacereverb)<<>.in Ndef(\y).play;

Ndef(\spacereverb).play;

s.plotTree;


{EnvGen.ar(Env.perc(0.1, 1,curve:-1),levelScale:20000,levelBias:100)}.plot(4);





