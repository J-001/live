SynthDef(\lagply, {|
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


~slide=Synth(\lagply, [\bufnum, b[\piano].choose,\rate,100]);


OSCdef(\lagplyspd, {|msg, time, addr, recvPort|
	msg[1].postln;
	~slide.set(\rate,msg[1]*0.5);
}, '/jd/play/rate');



SynthDef(\play, {|
	out=0,bufnum,rate=1,startPos=0.0,
	amp=1,gate=1,
	atk=0.01,sus=1,rel=0.1,
	loop=1,done=2,pan=0|
	var sig;
	var env;
	env = EnvGen.kr(Env.asr(releaseTime:atk,sustainLevel:sus,releaseTime:rel),gate,doneAction:2);
	sig = PlayBuf.ar(1,bufnum,rate:rate*BufRateScale.kr(bufnum),startPos:startPos*BufSamples.kr(bufnum),loop:loop,doneAction:0);
	sig = Pan2.ar(sig,pan,2,-1);
	Out.ar(out,sig*env);
}).add;


OSCdef(\play, {|msg, time, addr, recvPort|
	if(msg[1]>0,{
	Synth(\play,[\bufnum,b[\piano].choose,\loop,0]);
	});
}, '/jd/play/trig');





{|fdur=30,fleg=1,ctime=0|
	/*
	rushing water soundfile
	frequency filters with long envelopes + reverb
	*/
	var stop;
	var prx;
	var div;
	var shape;
	shape=(
		\asr:[0.2,0.8,0.2]*fdur*fleg,
		\crv:[(1..3).choose,(-1..-3).choose]);

	prx = NodeProxy.audio(s,2).fadeTime_(0);
	prx.play;
	prx[0] = {
		var sig;
		sig=SinOsc.ar(10000+LFNoise0.kr(MouseX.kr*100,5000))*0.005;
	};

	prx[1]=\filter->{|in|
	var mod;
	mod=[
	SinOsc.kr(0.5.rand,0,0.5,0.5),
	LFNoise1.kr(0.5.rand)].choose;
	in*mod;
	};
	prx[2]=\filter->{|in|
	Pan2.ar(in[0],LFNoise2.kr(2.0.rand,2,-1));
	};

	prx[3]=\filter->{|in|
	var env,lvl;
	lvl=[0,1,1,0];
	in*Env(lvl,[0.3,0.4,0.3].normalizeSum*fdur*fleg).kr;
	};

	stop ={
	prx.clear();
	};
	//
	r{
		(fdur*fleg).wait;
		stop.();
	}.play;
}.(5);