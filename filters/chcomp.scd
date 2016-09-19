//chorus and compression
SynthDef(\choruscompress, {|out =0, gate= 1|
	var source = \pre_in.ar(0!2);
	var chorus;
	var env = Linen.kr(gate, 0.1, 1, 0.1, 2);

	chorus= Splay.ar(Array.fill(4,{
		var maxdelaytime= rrand(0.005,0.02);

		DelayC.ar(source[0], maxdelaytime,LFNoise1.kr(Rand(0.1,0.6),0.25*maxdelaytime,0.75*maxdelaytime) )
	}));
	chorus = Compander.ar(4*(source + chorus),source,0.4,1,4) * \amp.kr(0.1);
	XOut.ar(out,env,chorus);
}).add;

