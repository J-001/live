//IMPULSE
Instr(\fmPulse, {|
	gate = 1, done = 0,
	freq = 220,	amp = 0.1,
	cRatio = 2,
	mRatio = 5, ind = 1,
	trigrate = 300, vib = 20, detune = 0.01,
	env, atk = 0.2, rel = 0.2, 
	pan = 0, lag = 0|
	var carSig,modSig;
	var impulse, egen;
	var freqs;
	var vibmod;

	vibmod = SinOsc.kr(vib);

	freqs = Lag.kr(freq,lag)*(1-[detune, 0,detune.sign]);
	impulse = EnvGen.kr(Env([0,1,0],[0.5,0.5]*0.2,[2,-2]),Impulse.kr(trigrate));
	modSig = SinOsc.ar(freqs*mRatio,0, freqs*ind);
	carSig = SinOsc.ar((freqs*cRatio) + modSig);	
	// egen = EnvGen.ar(Env.asr(atk,releaseTime:rel), gate, doneAction:done);
	egen = EnvGen.ar(env, gate, doneAction: done);

	carSig = Splay.ar(carSig);
	// carSig = Pan2.ar(carSig, pan);
	carSig  = carSig * amp * egen * vibmod * impulse;
}, [
	nil, ScalarSpec(0,2),
	nil, nil, 
	[0.01, 20000], 
	[0.01, 20000], [0.01, 20000],
	[0.001,1000], [0.001, 20, 'linear', 0, 0.001], [0,1],
	EnvSpec(Env([0,1,1,0],[0.01,0.5,0.01],[4,4,4])),
	[0.001, 10], [0.001, 10],
	nil, [0,10],
]).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
Instr('fmPulse.asr', {|
	gate = 1, done = 0,
	freq = 220,	amp = 0.1,
	cRatio = 2,
	mRatio = 5, ind = 1,
	trigrate = 300, vib = 20, detune = 0.01,
	env, atk = 0.2, rel = 0.2, 
	pan = 0, lag = 0|
	var carSig,modSig;
	var impulse, egen;
	var freqs;
	var vibmod;

	vibmod = SinOsc.kr(vib);

	freqs = Lag.kr(freq,lag)*(1-[detune, 0,detune.sign]);
	impulse = EnvGen.kr(Env([0,1,0],[0.5,0.5]*0.2,[2,-2]),Impulse.kr(trigrate));
	modSig = SinOsc.ar(freqs*mRatio,0, freqs*ind);
	carSig = SinOsc.ar((freqs*cRatio) + modSig);	
	egen = EnvGen.ar(Env.asr(atk,releaseTime:rel), gate, doneAction:done);

	carSig = Splay.ar(carSig);
	carSig = Pan2.ar(carSig, pan);
	carSig  = carSig * amp * egen * vibmod * impulse;
}, [
	nil, ScalarSpec(0,2),
	nil, nil, 
	[0.01, 20000], 
	[0.01, 20000], [0.01, 20000],
	[0.001,1000], [0.001, 20, 'linear', 0, 0.001], [0,1],
	[0.001, 10], [0.001, 10],
	nil, [0,10],
]).add;

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
Instr('fmPulse.perc', {|
	gate = 1, done = 0,
	freq = 220,	amp = 0.1,
	cRatio = 2,
	mRatio = 5, ind = 1,
	trigrate = 300, vib = 20, detune = 0.01,
	atk = 0.2, rel = 0.2, 
	pan = 0, lag = 0|
	var carSig,modSig;
	var impulse, egen;
	var freqs;
	var vibmod;

	vibmod = SinOsc.kr(vib);

	freqs = Lag.kr(freq,lag)*(1-[detune, 0,detune.sign]);
	impulse = EnvGen.kr(Env([0,1,0],[0.5,0.5]*0.2,[2,-2]),Impulse.kr(trigrate));
	modSig = SinOsc.ar(freqs*mRatio,0, freqs*ind);
	carSig = SinOsc.ar((freqs*cRatio) + modSig);	
	egen = EnvGen.ar(Env.perc(atk,releaseTime:rel), gate, doneAction:done);

	carSig = Splay.ar(carSig);
	carSig = Pan2.ar(carSig, pan);
	carSig  = carSig * amp * egen * vibmod * impulse;
}, [
	nil, ScalarSpec(0,2),
	nil, nil, 
	[0.01, 20000], 
	[0.01, 20000], [0.01, 20000],
	[0.001,1000], [0.001, 20, 'linear', 0, 0.001], [0,1],
	[0.001, 10], [0.001, 10],
	nil, [0,10],
]).add;
