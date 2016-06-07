/*
Based on materials from RE::CITY and Trip No.1 to Uncity

//Samples
-\piano
-speech

//Synths
- sin wave
- sine crackle

- set sound samples to use
- pattern control
	- dur,legato
	(Kr.Proxy??)

- Allocate samples to instrument
- Allocate synths to controller

METHODS OF INTERACTION:
- SET TO MANUAL CONTROL
- USE CODE w/ snippets
*/


//Make new Node Proxy 

//SET SOURCE(from snip)+env(from snip)

//NODE-PROXY CHAINS - j
j=();

j[\1]=NodeProxy.audio(s,2);
j[\1].play;
j[\1].quant=1;
j[\1].stop;

j[\1][0] = {|
	bufnum,rate=1,startPos=0.0,
	amp=1,gate=1,
	atk=0.01,sus=1,rel=0.1,
	loop=1,done=2,lag=0,channels=1,pan=0|
	var sig;
	var env;
	var speed;
	env = EnvGen.kr(Env.asr(releaseTime:atk,sustainLevel:sus,releaseTime:rel),gate,doneAction:2);
	sig = PlayBuf.ar(bufnum.numChannels,bufnum,rate:rate*BufRateScale.kr(bufnum),startPos:startPos*BufSamples.kr(bufnum),loop:loop,doneAction:0);
	sig = Pan2.ar(sig,Lag.kr(pan,lag),2,-1);
	sig*env*Lag.kr(amp,lag);
};




OSCdef(\pgliss1, {|msg, time, addr, recvPort|
	msg.postln;
}, '/jd/pgliss/1/rate');

Pbind(
	\instrument,\player,
	\bufnum,b[\piano].choose,
	\rate,Pseq([1,-1],inf),
	\dur,Pfunc{|e|e[\bufnum].duration}
	).play

Pbind(
	\type,\on,
		\instrument,\grain,
		\bufnum,b[\chinatown][0],
		\rate,[1,3/2],
		\startPos,0.1+Pwhite(0,0.1),
		\amp,Pwhite(0.5,1.0),
		\pan,{2.0.rand-1.0}!4,
		\sustain,1,
		\dur,0.00625
		).play;



SynthDef(\grain, {|out=0,bufnum,rate=1|
	var trig;
	var genv;
	var sig;

	trig  =Dust.kr(10);
	genv = EnvGen.kr(Env([0,1,0],[0.5,0.5]*0.05),trig);
	sig = PlayBuf.ar(1,bufnum,rate,trig,startPos:rrand(0.0,1.0)*BufSamples.kr(bufnum));
	Out.ar(out,sig*genv);
}).add;


Synth(\grain, [\bufnum, b[\piano][0]]);



SynthDef(\bass, {|out=0,freq=200,amp=0.1,gate=1,cut=200|
	var sig;
	var env;

	env=EnvGen.kr(Env.asr(),gate,doneAction:2);
	sig=SinOsc.ar(freq*[0.999,1.0,1.001],0,amp);
	sig = Mix(VarSaw.ar(80*[0.99,1,1.01]))*amp;
	sig = Mix(RLPF.ar(sig,freq,0.01));
	sig = sig*env;

	Out.ar(out,sig!2);
}).add;

200.cpsmidi

Synth(\bass, [\freq, 48.midicps]);

MIDIClient.init;
MIDIIn.connectAll;

SynthDef(\sine, {|out=0,freq=200|
	Out.ar(out,
		SinOsc.ar(freq)*Env([0,1,0],[0.5,0.5]*0.2).kr(1,2)*0.2;
	);
}).add;

MIDIdef.noteOn(\noteOn, {|val, num, chan, src|
num.midicps.postln;
	Synth(\sine, [\freq, num.midicps]);

});


Synth(\bass, [\freq, 80]);

Pdef(\bass,Pbind(\instrument,\bass,
	\amp,0.3,
	\midinote,Pseq((55!6),inf),
	\legato,0.1,
	\dur,Pseq([3,3,3,3,2,2]*0.125,inf)
	)
)

Pdef(\bass).quant=4;
Pdef(\bass).play
Pdef(\bass).play


Pbind(\instrument,\bass,
	\amp,0.3,
	\midinote,Pseq((55!6),inf),
	\legato,0.1,
	\dur,Pseq([3,3,3,3,2,2]*0.125,inf)
	).play



SynthDef("fm1", { arg out = 0, freq = 440, carPartial = 1, modPartial = 1, index = 3, mul = 0.05,gate=1;

	// index values usually are between 0 and 24
	// carPartial :: modPartial => car/mod ratio
	
	var mod;
	var car;
	
	mod = SinOsc.ar(
		freq * modPartial, 
		0, 
		freq * index 
	);
		
	car = SinOsc.ar(
		(freq * carPartial) + mod, 
		0, 
		mul
	);
	
	// car = RLPF.ar(car,200,0.01);

	Out.ar(
		out,
		car*EnvGen.kr(Env.asr(0.1,1,0.1),gate)
	)
}).add;



a= Synth(\fm1);

a.set(\gate,0);

OSCdef(\syn11, {|msg, time, addr, recvPort|
	msg[1].postln;
	a.set(\gate,msg[1]);
}, '/jd/syn/1/tog');

OSCdef(\syn11, {|msg, time, addr, recvPort|
	a.set(\carPartial, msg[1].linexp(0,1,1,1000));
}, '/jd/syn/2/flt/1');

OSCdef(\syn12, {|msg, time, addr, recvPort|
	a.set(\modPartial, msg[1].linexp(0,1,1,1000));
}, '/jd/syn/2/flt/2');

OSCdef(\syn13, {|msg, time, addr, recvPort|
	a.set(\index, msg[1].linexp(0,1,1,1000));
}, '/jd/syn/2/flt/3');



Pdef(\fm).quant=4;
Pdef(\fm2).quant=4;

Pdef(\fm,
	Pbind(
	\instrument,Prand([\fm1],inf),
	\carPartial,k,
	\modPartial,k,
	\index,k,
	\freq,k,
	\legato,Pwhite(0.05,1),
	\dur,Pseq([3,3,3,3,2,2]*0.125,inf)
	)

)


k = ();
k = NodeProxy(s)

k[0]={Line.kr(0,1000,300)};

Pdef(\fm2,
	Pbind(
	\instrument,Prand([\fm1],inf),
	\carPartial,1000,
	\modPartial,1000,
	\index,10000,
	\freq,Pseq(Scale.aeolian.degrees,inf),
	\legato,Pwhite(0.1,1),
	\dur,0.125
	)

)

Pdef(\fm).play;
Pdef(\fm).stop;

Pdef(\fm2).play;
Pdef(\fm2).stop;




SynthDef("fm2", { arg out = 0, freq = 440, carPartial = 1, modPartial = 1, modPartial2=1, index = 3, index2 = 3, mul = 0.05,gate=1;

	// index values usually are between 0 and 24
	// carPartial :: modPartial => car/mod ratio
	
	var mod;
	var mod2;
	var car;
	
	mod = SinOsc.ar(
		freq * modPartial, 
		0, 
		freq * index 
	);

	mod2 = SinOsc.ar(
		(freq * modPartial2) +  mod, 
		0, 
		freq * index2 
	);
		
	car = SinOsc.ar(
		(freq * carPartial) + mod2, 
		0, 
		mul
	);
	
	// car = RLPF.ar(car,200,0.01);

	Out.ar(
		out,
		car*EnvGen.kr(Env.asr(0.1,1,0.1),gate)
	)
}).add;


a= Synth(\fm2);

a.set(\gate,0);

OSCdef(\syn11, {|msg, time, addr, recvPort|
	msg[1].postln;
	a.set(\gate,msg[1]);
}, '/jd/syn/1/tog');

OSCdef(\syn11, {|msg, time, addr, recvPort|
	a.set(\carPartial, msg[1].linexp(0,1,1,1000));
}, '/jd/syn/1/flt/1');

OSCdef(\syn12, {|msg, time, addr, recvPort|
	a.set(\modPartial, msg[1].linexp(0,1,1,1000));
}, '/jd/syn/1/flt/2');

OSCdef(\syn13, {|msg, time, addr, recvPort|
	a.set(\index, msg[1].linexp(0,1,1,1000));
}, '/jd/syn/1/flt/3');

OSCdef(\syn11, {|msg, time, addr, recvPort|
	a.set(\modPartial2, msg[1].linexp(0,1,1,1000));
}, '/jd/syn/2/flt/1');

OSCdef(\syn12, {|msg, time, addr, recvPort|
	a.set(\index2, msg[1].linexp(0,1,1,1000));
}, '/jd/syn/2/flt/2');


SynthDef(\grunt, {|out=0,bufnum,rate=1,startPos=0,loop=0,dur=0.5,gate=1|
	var sig;
	var env;

	env = EnvGen.kr(Env([0,1,0],[0.99,0.01]*dur,gate));

	sig = PlayBuf.ar(2,bufnum,
		rate:rate*BufRateScale.kr(bufnum),
		startPos:startPos*BufSamples.kr(bufnum),
		loop:loop,
		doneAction:2);
	sig = sig*env;
	Out.ar(out,sig);

}).add;


j[\2]=Synth(\grunt,[\bufnum,b[\chinatown].choose,\startPos,1.0.rand,\dur,3,\rate,0.5]);

j[\3]=Synth(\grunt,[\bufnum,b[\].choose,\startPos,1.0.rand,\dur,3,\rate,0.25]);


j[\2]=NodeProxy.audio(s,2);
j[\2].play;
j[\2][0] = {
	source
	*env
};


~gvb = Synth(\gVerb, [\in, 0],addAction:'addAfter');
~gvb.free;
s.plotTree



