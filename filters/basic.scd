Fdef(\plink, {
	|in, mix = 1|
	var dTrig = Dust.kr(4);
	var seq = Drand((0..7) + 55 + 12,inf);
	var freq = Demand.kr(dTrig,0,seq).midicps;
	var dry = in;
	var wet = RLPF.ar(in,freq, 0.01) * 0.5;

	(dry * (1 - mix)) + (wet * mix)
});
//-----------------------------------------------------------------------
//----------------Delaysapf--------------------------------------------------
Fdef(\apfN, {|in, delMax = 0.5, delT = 0.5, len = 4| 
	AllpassN.ar(in, delMax, delT, len)
	});
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
Fdef(\apfC, {|in, delMax = 0.5, delT = 0.5, len = 4| 
	AllpassN.ar(in, delMax, delT, len)
	});

