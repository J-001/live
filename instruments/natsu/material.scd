//Components
"pattern_component : natsu_ride".postln;
Pdef(\natsu_ride,
	PbindProxy(
		\midinote,Pseq([0,-2,0,3,5],inf) + 60 + [0,3,7,10],
		\dur,Pseq([3,5+2,2,2,2],inf)*0.25
	)Ï
);

"pattern_component : natsu_trill".postln;
Pdef(\natsu_trill,
	PbindProxy(
		\midinote, Pseq(([0, -2]!7).flatten++[2,3], inf) + [60] ,
		\dur, 0.5
	)
);

"pattern_component : natsu_long".postln;
Pdef(\natsu_long,
	PbindProxy(
		\midinote, Pseq([0,Pwhite(-12,12,1)]) + 60,
		\dur,Pseq([7.5,0.5])
	)
);
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
Pdef(\natsu_modseq,
	PbindProxy(
		\carRatio1,Pseq((0..10), inf),
		\modRatio1,Pseq((0..10), inf),
		\modRatio2,Pseq((0..10), inf),
		\index1,Pseq((0..10), inf),
		\index2,Pseq((0..10), inf)
	)
);
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
"pattern : natsu".postln;
Pdef(\natsu,
		Pmono(
		\fmMMCAsr,
		\amp,0.3,
		\carRatio1,Pwhite(1,10),
		\modRatio1,Pwhite(1,10),
		\modRatio2,Pwhite(1,10),
		\index1,Pwhite(1,10),
		\index2,Pwhite(1,10),
		\lag,0.1,
	)
		<> 'natsu_ride'
	
);
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
"mat::natsu loaded";
