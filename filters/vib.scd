SynthDef(\vibN, 
	{|out|
	var mix = \mix.kr(1.0);
	var dry = \pre_in.ar([0,0]);
	var wet = dry * SinOsc.kr(\lforate.kr(1)).linlin(-1, 1, \lfodep.kr(0), 1);
	(dry * (1 - mix)) + (wet * mix) * \amp.kr(0.1);
	Out.ar(out,(dry * (1 - mix)) + (wet * mix) )
	}, 
	metadata: (
		\specs : (
			mix: [0,1].asSpec,
			lforate: [0.001, 20].asSpec,
			lfodep: [1,0].asSpec 
		)
	)
).add;

Ndef(\vib, \vib)