//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
SynthDef(\lagSamplerM, {|
	out=0,bufnum,rate=1,startPos=0.0,
	amp=1,gate=1,
	atk=0.01,sus=1,rel=0.1,
	loop=1,done=2,lag=0,channels=1,pan=0|
	var sig;
	var env;
	var speed;
	env = EnvGen.kr(Env.asr(releaseTime:atk,sustainLevel:sus,releaseTime:rel),gate,doneAction:2);
	sig = PlayBuf.ar(1,bufnum,rate:rate*BufRateScale.kr(bufnum),startPos:startPos*BufSamples.kr(bufnum),loop:loop,doneAction:0);
	sig = Pan2.ar(sig,Lag.kr(pan,lag),2,-1);
	Out.ar(out,sig*env*Lag.kr(amp,lag));
}).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
SynthDef(\samplerM, {|
	out=0,bufnum,rate=1,startPos=0.0,
	amp=1,gate=1,
	atk=0.01,sus=1,rel=0.1,
	loop=1,done=2,pan=0|
	var sig;
	var env;
	env = EnvGen.kr(Env.asr(releaseTime:atk,sustainLevel:sus,releaseTime:rel),gate,doneAction:2);
	sig = PlayBuf.ar(1,bufnum,rate:rate*BufRateScale.kr(bufnum),startPos:startPos*BufSamples.kr(bufnum),loop:loop,doneAction:0);
	sig = Pan2.ar(sig,pan,2,-1);
	Out.ar(out,sig*env*amp);
}).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
SynthDef(\lagSamplerS, {|
	out=0,bufnum,rate=1,startPos=0.0,
	amp=1,gate=1,
	atk=0.01,sus=1,rel=0.1,
	loop=1,done=2,lag=0,channels=1,pan=0|
	var sig;
	var env;
	var speed;
	env = EnvGen.kr(Env.asr(releaseTime:atk,sustainLevel:sus,releaseTime:rel),gate,doneAction:2);
	sig = PlayBuf.ar(2,bufnum,rate:rate*BufRateScale.kr(bufnum),startPos:startPos*BufSamples.kr(bufnum),loop:loop,doneAction:0);
	sig = Pan2.ar(sig,Lag.kr(pan,lag),2,-1);
	Out.ar(out,sig*env*Lag.kr(amp,lag));
}).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
SynthDef(\samplerS, {|
	out=0,bufnum,rate=1,startPos=0.0,
	amp=1,gate=1,
	atk=0.01,sus=1,rel=0.1,
	loop=1,done=2,pan=0|
	var sig;
	var env;
	env = EnvGen.kr(Env.asr(releaseTime:atk,sustainLevel:sus,releaseTime:rel),gate,doneAction:2);
	sig = PlayBuf.ar(2,bufnum,rate:rate*BufRateScale.kr(bufnum),startPos:startPos*BufSamples.kr(bufnum),loop:loop,doneAction:0);
	sig = Pan2.ar(sig,pan,2,-1);
	Out.ar(out,sig*env*amp);
}).add;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
// k[\bassdrum]=();
k.bassdrum[\rate]= NodeProxy.control(s,2).source_({|val|val*[1,1.5]*(-1)});
k.bassdrum[\lag]= NodeProxy.control(s).source_({|val|val});

Pdef(\bassDrum,
    Pmono(
        // \instrument, \lagSamplerS,
        \lagSamplerM,
        \bufnum,Pseq([Prand(b.kitchen,20)],inf),
        \loop,0,
        \lag,2,
        \startPos,Pwhite(0.0,1.0),
        \rate,Pwhite(-10.0,10.0)*0.1,
        \dur, Pseq([
        	Pseq([1/3],3),
        	Pseq([1/2],2)
        	],inf)*0.25
    )
);

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
//ONE-SHOT PATTERNS
// Pdef(\bassDrum).clock_(t);
// Pdef(\bassDrum).quant=2;

// n[\knob0].cc={|val| k.bassdrum.rate.set(\val,val*99+0.25)}
// n[\knob1].cc={|val| k.bassdrum.lag.set(\val,val*2+0.1)}
// n[\m0].on = {Pdef(\bassDrum).play};
// n[\m0].off = {Pdef(\bassDrum).stop};

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
// a.bassDrum=nil;

a[\bassDrum]=NodeProxy.audio(s,2);
a.bassDrum.clock_(t);
a.bassDrum.quant=0;
a[\bassDrum].play;
a[\bassDrum][0] = Pdef(\bassDrum);
a[\bassDrum][1] = \filter->{|sig,cut=84|
	var new;
	new = RLPF.ar(sig,(Scale.dorian.degrees+cut).midicps,0.01);
	new = Splay.ar(new);
	new;
};

n[\knob0].cc={|val| a.bassDrum.set(\rate,val*10+0.25)};
n[\knob1].cc={|val| a.bassDrum.set(\lag,val*2+0.1)};
n[\knob1].cc={|val| a.bassDrum.set(\amp,val*1)};
n[\fader1].cc={|val| a.bassDrum.set(\cut,val*48+60)};

n[\m0].on = {a.bassDrum.play};
n[\m0].off = {a.bassDrum.stop};


n[\m0].on = {a.bassDrum.playN(outs:FX(\reverb).outs,group:FX(\reverb).group,addAction:\addToHead)}



















