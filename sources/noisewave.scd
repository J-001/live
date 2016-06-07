
//WAVES
/*
1.WHITE NOISE
2. PLAYER
*/
~ns=();
//FILTER
~ns.flt=[HPF,LPF];
//MODULATOR
~ns.lfo=[
	["",{1}],
	["sin",{SinOsc.kr(NamedControl.kr(\lfoSpd,0.1))}],
	["lf1",{LFNoise1.kr(NamedControl.kr(\lfoSpd,0.1))}]
	];


//WHITE NOISE WAVE (WWV)
~ns.lfo.do{|lfo|
	~ns.flt.do{|flt|
		SynthDef(\wwv++flt.asString.toLower++lfo.value[0],{|dur=3,amp=0.1,legato=1|
		//source
		var sig,env;
		var lvl,asr,crv;
		//Pan
		var penv,plvl;
		//Filter
		var fenv,flvl;

		//Source
		sig=WhiteNoise.ar(amp);
		lvl=[0,1,exprand(0.2,0.5),0];
		asr=[exprand(0.1,2.0),exprand(0.01,0.3),exprand(0.1,2.0)];
		asr=asr.normalizeSum*dur*legato;
		crv=[4,-4,-3];
		env=EnvGen.kr(Env(lvl,asr,crv),doneAction:2);
		sig=sig*env;
		//lfo
		sig = sig*lfo.value[1];
		//Pan Filter
		plvl=[-1,exprand(-0.8,-0.3),exprand(0.3,0.8),1];
		penv=EnvGen.kr(Env(plvl*[-1,1].choose,asr,crv),doneAction:0)*lfo.value[1];
		//Low/High Pass Filter
		flvl=[0,exprand(0.1,0.5),exprand(0.5,0.8),1]*exprand(500,19000);
		fenv=EnvGen.kr(Env(flvl*[-1,1].choose,asr,[1,1,1]),doneAction:0);
		fenv=Line.kr(exprand(500,19000),exprand(500,19000),dur);
		sig = flt.ar(sig,fenv);
		
		sig = Pan2.ar(sig,penv);
		Out.ar(0,sig);
		}).add;
	};
};
Synth(\wwvlpf,[\dur,rrand(1.0,8.0),\legato,rrand(1.5,2.0),\lfoSpd,0.5]);


//PLAYER
~ns.lfo.do{|lfo|
	~ns.flt.do{|flt|
		SynthDef(\pwv++flt.asString.toLower++lfo.value[0],{|dur=3,amp=1.1,legato=1,bufnum,spd=1,pos=0|
		//source
		var sig,env;
		var lvl,asr,crv;
		//Pan
		var penv,plvl;
		//Filter
		var fenv,flvl;

		//Source
		sig=PlayBuf.ar(2,bufnum,spd,pos*BufSamples.kr(bufnum));
		lvl=[0,1,exprand(0.2,0.5),0];
		asr=[exprand(0.1,2.0),exprand(0.01,0.3),exprand(0.1,2.0)];
		asr=asr.normalizeSum*dur*legato;
		crv=[4,-4,-3];
		env=EnvGen.kr(Env(lvl,asr,crv),doneAction:2);
		sig=sig*env;
		//lfo
		sig = sig*lfo.value[1];
		//Pan Filter
		plvl=[-1,exprand(-0.8,-0.3),exprand(0.3,0.8),1];
		penv=EnvGen.kr(Env(plvl*[-1,1].choose,asr,crv),doneAction:0)*lfo.value[1];
		//Low/High Pass Filter
		flvl=[0,exprand(0.1,0.5),exprand(0.5,0.8),1]*exprand(500,19000);
		fenv=EnvGen.kr(Env(flvl*[-1,1].choose,asr,[1,1,1]),doneAction:0);
		fenv=Line.kr(exprand(500,19000),exprand(500,19000),dur);
		sig = flt.ar(sig,fenv);
		
		sig = Pan2.ar(sig,penv);
		Out.ar(0,sig);
		}).add;
	};
};


// Synth(\wwvlpf, [\dur,0.5,\bufnum, b.[\sea].choose]);

















