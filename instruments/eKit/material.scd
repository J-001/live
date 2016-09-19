Pdef(\chick,
	Pbind(
		\type,\on,
		\instrument,\chick,
		\pan,Pwhite(-1.0,1.0),
		\amp,1,
		\dur,Pseq([
			0.25.rest,0.25,0.25,0.25,
			0.25.rest,0.25,0.25,0.25,
			0.25,0.25,0.25,0.25,
			0.25,0.25,0.25,0.25
			],inf)
		)
	).q_(4).p;
// Pdef(\kick,
// 	Pbind(
// 		\instrument,\kick,
// 		\rel,Pseq([1.75,0.5],inf),
// 		\amp,0.5,
// 		\freq,5000,
// 		// \pan,Prand([0],inf),
// 		\pan,[-0.5,0.5],
// 		// \dur,Pseq([
// 		// 	2,2,
// 		// 	],inf)
// 		\dur,Pseq([
// 			// Pseq([2,2],4),
// 			Pseq([1.75,0.25,0.5,1.5],3),
// 			Pseq([1.75,0.25,0.5!4].flatten,1),
// 			],inf)
// 		)
// 	).q_(4).p;
Pdef(\clap,
	Pbind(
		\type,\note,
		\instrument,\clap,
		\amp,0.5,
		\dur,Pseq([
			2,2
			],inf)
		)
	).q_(4).p

// TempoClock.default.tempo = 1.5;