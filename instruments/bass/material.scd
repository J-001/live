//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
// syncBass
//roo
Pdef(\syncBassRoot,
	PbindProxy(
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
	PbindProxy(
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
);
//main
Pdef(\syncBass.postln, 
	Pseq([
		Pdef(\syncBassRoot),
		Pdef(\syncBassEnd)
	],inf)
);
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
Pdef(\r_drive_f,
	Pwrand([0.5,Pseq([0.75,0.25]*0.5),Pseq([0.25,0.25])],[0.7,0.2,0.1])
);
Pdef(\bass_drive,
	 PbindProxy(
		\instrument, \bass,
		\freq, [70],
		\detune, 0.01,
		\amp, 0.5,
		\legato, 2,
		\dur, \r_drive_f,
		\rel, Pfunc{|e| e.dur * e.legato}
	)
)