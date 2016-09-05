u[\fmPulse] = {
	//fmPULSE SYNTH
	n[\knob0].cc_({|val| Pdef(\ah).set(\vib, val.linlin(0,1,0.001,200))});
	n[\knob1].cc_({|val| Pdef(\ah).set(\trigrate, val.linlin(0,1,0.001,200))});
	n[\knob2].cc_({|val| Pdef(\ah).set(\lag, val.linlin(0,1,0,4))});
	n[\knob3].cc_({|val| Pdef(\ah).set(\pan, val.linlin(0,1,-1,1))});

	n[\fader0].cc_({|val| Pdef(\ah).set(\amp, val.pow(4))});
	n[\fader1].cc_({|val| Pdef(\ah).set(\detune, val.linlin(0,1,0.0001,1))});
	n[\fader2].cc_({|val| Pdef(\ah).set(\cRatio, val.linlin(0,1,1000,0.0001).round)});
	n[\fader3].cc_({|val| Pdef(\ah).set(\mRatio, val.linlin(0,1,1000,0.0001).round)});

	n[\s0].on_({Pdef(\ah).quant_(0).play(t);});
	n[\s0].off_({Pdef(\ah).stop});
};
