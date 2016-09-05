//fmPulse
Pdef(\fmPulse1,
	Pmono(
		Instr('fmPulse.asr').asDefName,
		\midinote,Pseq(Scale.aeolian.degrees,inf)+60+Pseq({(0..5).choose}!4,inf),
		\cRatio,2,
		\legato,1,
		\lag, 0.1,
		\amp, 0.3,
		\vib, 100,
		\trigrate, 20, 
		\dur,Pseq((0.125!6)++0.5,inf),
		)
);
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
Pdef(\fmPulse2,
	Pmono(
		Instr(\fmPulse).asDefName,
		\midinote, Pseq([Pseq(Scale.aeolian.degrees[0..5],1),Prand([0,3,7,10],1)],inf) + 60,
		\amp, Pwhite(0.5,1.0),
		\lag, 1,
		\trigrate, 5,
		\cRatio, Pwhite(0.01, 50),
		\mRatio, Pwhite(0.01, 50),
		\ind, Pwhite(0.01,50),
		\detune, Pwhite(0.001, 0.05),
		\legato, 1,
		\dur, Pseq((0.125!6)++0.5,inf)
		)
);
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
Pdef(\fmPulse3,
	Pmono(
		Instr(\fmPulse).asDefName,
		\midinote, Pseq(Scale.aeolian.degrees[0..1],inf) + [0,3,7,10] + 60,
		\amp, 0.3,
		\lag, 2,
		\trigrate, 5,
		\cRatio, 5,
		\mRatio, 5,
		\ind, 5,
		\detune, 0.01,
		\legato, 1,
		\dur, Pseq([10,2],inf)
		)
);
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
Pdef(\fmPulseBass,
	Pbind(
		// \type, \instr,
		// \instr, 'fmPulse.perc',
		\type, \on,
		\instrument, Instr('fmPulse.perc').asDefName,
		\done, 2,
		\midinote, Pseq([0,0,0,Pwhite(8,16,1),0],inf) + [60],
		\amp, 0.5,
		\lag, 2,
		\trigrate, 100,
		\cRatio, 1,
		\mRatio, 1,
		\ind, 1,
		\detune, 0.01,
		\atk,0.001,
		\release,0.01,

		\legato, 1,
		\dur, Pseq([1.5,1.5,1.5,0.5,3.0],inf)*0.5
	)
);
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
