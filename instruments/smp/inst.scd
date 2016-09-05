//-------------Samplers--------------------------------------------------
//-----------------------------------------------------------------------
/*Instr('smp', {|
	gate = 1, done = 2,
	chan, bufnum, rate = 1, loop = 1, startPos = 0.0,
	dur = 1, legato = 1,
	atk = 0.01, sus = 1, rel = 0.1,
	pan = 0, amp = 1,
	lag = 0|
	var sig;
	var env;
	env = EnvGen.kr(Env.asr(attackTime:atk, releaseTime:rel), gate, doneAction: 2);
	sig = PlayBuf.ar(chan,bufnum,rate:Lag.kr(rate, lag) * BufRateScale.kr(bufnum), startPos:startPos * BufSamples.kr(bufnum),loop:loop,doneAction:0);
	sig = Pan2.ar(sig,Lag.kr(pan, lag), 2, -1);
	sig * env * Lag.kr(amp, lag);
}, [
	nil, [0,2],
	StaticIntegerSpec(1,8,1), BufferProxySpec(), \rate, [0,1], [0,1],
	\dur, \legato,
	[0,60,\linear,0,0.01],[0,60,\linear,0,0.1],[0,60,\linear,0,0.01],
	nil, nil,
	[0,60]
]);
*/
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
[1,2].do{|chan|
	SynthDef('smp'++chan.asSymbol, {|
		out = 0, gate = 1, done = 2,
		bufnum, rate = 1, loop = 1, startPos = 0.0,
		dur = 1, legato = 1,
		atk = 0.01, sus = 1, rel = 0.1,
		pan = 0, amp = 1,
		lag = 0|
		var sig;
		var env;
		env = EnvGen.kr(Env.asr(attackTime:atk, releaseTime:rel), gate, doneAction: 2);
		sig = PlayBuf.ar(chan,bufnum,rate: Lag.kr(rate, lag) * BufRateScale.kr(bufnum), startPos:startPos * BufSamples.kr(bufnum),loop:loop,doneAction:0);
		sig = Pan2.ar(sig,Lag.kr(pan, lag), 2, -1);
		sig = sig * env * Lag.kr(amp, lag);
		Out.ar(out, sig);
	}).add
};
//-----------------PERC?-----------------------------------------------
//-----------------------------------------------------------------------
i[\smpN] = {|self|
	Pfunc{|e| 
		'smp'.asSymbol++e.bufnum.numChannels.asSymbol
	}
};
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
/*
	\instrument, Pfunc{|e|
			var nChan = e.bufnum.numChannels;
			var name = 'smp'++nChan.asSymbol;
			if(SynthDescLib.global.match(name).isNil) {
				~makeSmp.(nChan);
			}; 
			name;
			},
*/
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------