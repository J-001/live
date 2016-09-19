//SPACE_VERB
Ndef(\spaceverb, {|
	gate = 1, 
	mix = 0.1,
	len = 3.0, hicut = 5000,
	amp=0.9|
	var in, sig;
	var numAllpass, numComb, z, wet;
	var env = Linen.kr(gate, 0.1, 1, 0.1, 2);

	numComb = 10; // number of comb delays
	numAllpass = 10; // number of allpass delays

	in = \pre_in.ar([0,0]);
	// reverb predelay:
	z = DelayN.ar(in, 0.01,0.01);

	y = Mix.arFill(numComb, {CombL.ar(z,0.2,rrand(0.01, 0.2),len)});

	// chain of 4 allpass delays on each of two channels (8 total) :
	numAllpass.do{ 
		wet = AllpassN.ar(wet, 0.051, [rrand(0.001, 0.03),rrand(0.001, 0.032)], 1) 
	};

	wet = HPF.ar(wet, hicut);
	sig = (in * (1-mix))+(y*mix);
	sig*env*amp;
});
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------

