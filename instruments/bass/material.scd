//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
// syncBass
//root
Pdef(\syncBassRoot,
	Pbind(
		\instrument, \bass,
		\freq,Pseq([100, 100, 100, 200]),
		\amp,1,
		\dur,Pseq([1.5, 1.5, 1.5, 0.5]),
        \rel,1,
        \type, Pfunc{|e| if (e.rel<=1) {\on} {\note}};
	)
);
//end
Pdef(\syncBassEnd, 
	Pbind(
		 \instrument, \bass,
		 \rel,1,
		 \amp,1,
		 [\freq, \dur], Prand([
		 		//Phrases
			 	Pseq([[100,1/3]],3),
			 	Ptuple([Pseq([1,1.2,1.5]*100),1/3],3),
			 	Ptuple([Pseq([100,200,120],3), Pseq([1],3)])
		 	])
	)
)
//combos in Main
Pdef(\syncBass, 
	Pseq([
		Pdef(\syncBassRoot),
		Pdef(\syncBassEnd)
	],inf)
);
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------

n[\s3].on_({ Ndef(\name).play});
n[\s3].off_({ Ndef(\name).play});


