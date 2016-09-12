\bassDrive2_P.asP_(
	Pseq([
			\bassDrive.asP.psq(32),
		[\bassDrive.asP.psq(14),2.rest].flatten.psq(1)
	],inf)
).q_(8).p

//--------------------------------------------------------------
Pdef(\summerGlide2.postln,
	Pmono(
		\fmMMCAsr,
		\amp,0.1,
		[\midinote,\dur], Ptuple([
			Pseq([0,Pwhite(-12,12,1)]) + 60,
			Pseq([7.5,0.5])
			], inf),
		// Ptuple([ 
		// 	Pseq([0,-2,3,5]) + 60 + [0,3,7,10], //FREQ
		// 	1 * 0.5], //DUR
		// 	inf ),
		// []
		\modRatio1,Pwhite(50.0,100.0) * 0.5,
		\modRatio2,Pwhite(50.0,100.0) * 0.5,
		\index1,Pwhite(50.0,100.0) * 0.5,
		\index2,Pwhite(50.0,100.0) * 0.5
	)
)
Pdef(\summerGlide3.postln,
	Pmono(
		\fmMMCAsr,
		\amp,0.1,
		[\midinote,\dur], Ptuple([
			Pseq(([0, -2]!7).flatten++[2,3]) + [72],
			Pseq([0.5],16)
			], inf),
		// Ptuple([ 
		// 	Pseq([0,-2,3,5]) + 60 + [0,3,7,10], //FREQ
		// 	1 * 0.5], //DUR
		// 	inf ),
		// []
		\modRatio1,Pwhite(50.0,100.0) * 0.5,
		\modRatio2,Pwhite(50.0,100.0) * 0.5,
		\index1,Pwhite(50.0,100.0) * 0.5,
		\index2,Pwhite(50.0,100.0) * 0.5
	)
)
//--------------------------------------------------------------
\bassDrive2.asP_(
	Pseq([
	Pseq([\bassDrive.asP],2.rand +1),
	Event.silent(10.rand+1)
	// \bassDrive.asP
	],inf)
).q_(8).p

//--------------------------------------------------------------

Pdef(\natsuFDP, 
	Pdef(\natsuFDP)
)

//--------------------------------------------------------------






Pdef(\natsu,
	Pmono(
		\fmMMCAsr,
		\amp,0.1,
		\modRatio1,Pwhite(1,10),
		\modRatio2,Pwhite(1,10),
		\index1,Pwhite(1,10),
		\index2,Pwhite(1,10)
	)
	>< ('natsu_trill' |? [\midinote, _ + [0,3,7,10] ])
).play;

//--------------------------------------------------------------
