Pdef(\romance,	
	Pbind(
		\bufnum, b.loops.romance[0], 
		\instrument, i.smpN,
		\rate, 1,
		\startPos, 0,
		\loop, 1,
		\amp, 0.3,
		\pan, 0,
		\legato,  1,
		\dur, Pfunc{|e|e[\rate] * e[\bufnum].duration * e[\legato]},
	)
)


