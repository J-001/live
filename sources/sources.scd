a=();

a[\pgliss] = {|
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

