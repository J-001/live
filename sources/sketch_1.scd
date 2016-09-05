
//SYNTH 1

SynthDef(\synth1, {|out=0,freq=262,carRatio=1,modRatio1=1,modRatio2=1,index1=1,index2=1,amp=0.3,gate=1|
	var car;
	var mod1;
	var mod2;

	var newFreq;

	newFreq = (freq+[0,3,7,10]).midicps;
	// newFreq = (freq+Scale.aeolian.degrees).midicps;

	mod1 = SinOsc.ar(newFreq*modRatio1,0,newFreq*index1);

	mod2 = SinOsc.ar(newFreq*modRatio2,0,newFreq*index2)*SinOsc.kr(10,0,0.5,0.5);

	car = SinOsc.ar((newFreq*carRatio)+mod1+mod2);

	// car=car*EnvGen.kr(Env.asr(0.01,1,0.01),gate,doneAction:2);
	car=car*EnvGen.kr(Env.perc(0.01,1,0.01),gate,doneAction:0);
	Out.ar(out,Splay.ar(car)*amp*0.15);
}).add;

Synth(\synth1, [\freq, 262,\carRatio,1,\modRatio1,4,\modRatio2,1,\index1,1,\index2,1]);


(
	var w;
	var slider1;
	var slider2;
	var slider3;
	var slider4;
	var slider5;

	var p;
	var fm = Synth(\synth1, [\freq, 60.midicps]);

	/*Slider Height*/
	h=60;


w = Window("fm3",Rect(200,200,400,500));

p = Point(10,10);


slider1 = Slider(w,Rect(10,(h+10)*1,300,h));
slider2 = Slider(w,Rect(10,(h+10)*2,300,h));
slider3 = Slider(w,Rect(10,(h+10)*3,300,h));
slider4 = Slider(w,Rect(10,(h+10)*4,300,h));
slider5 = Slider(w,Rect(10,(h+10)*5,300,h));

slider1.action_({
	fm.set(\freq,slider1.value.linlin(0,1,10,120))
	});
slider2.action_({
	fm.set(\modRatio1,slider2.value.linlin(0,1,1,10))
	});

slider3.action_({
	fm.set(\index1,slider3.value.linlin(0,1,1,100))
	});

slider4.action_({
	fm.set(\modRatio2,slider4.value.linlin(0,1,1,100))
	});

slider5.action_({
	fm.set(\index2,slider5.value.linlin(0,1,1,100))
	});

w.front
)


~fm3 = Synth(\synth1)

~fm3.release;


OSCdef(\synthFreq, {|msg, time, addr, recvPort|
	msg.postln;
	~fm3.set(\freq,msg[1].value.linlin(0,1,10,180))
}, '/jd/4/fade1');

OSCdef(\synthMod1, {|msg, time, addr, recvPort|
	msg.postln;
	~fm3.set(\modRatio1,msg[1].value.linlin(0,1,0.1,1200))
}, '/jd/4/fade2');

OSCdef(\synthInd1, {|msg, time, addr, recvPort|
	msg.postln;
	~fm3.set(\index1,msg[1].value.linlin(0,1,0.1,12))
}, '/jd/4/fade3');

OSCdef(\synthMod2, {|msg, time, addr, recvPort|
	msg.postln;
	~fm3.set(\modRatio2,msg[1].value.linlin(0,1,0.1,1200))
}, '/jd/4/fade4');

OSCdef(\synthInd2, {|msg, time, addr, recvPort|
	msg.postln;
	~fm3.set(\index2,msg[1].value.linlin(0,1,0.1,1200))
}, '/jd/4/fade5');


~fm4 = Synth(\synth1);

~fm4.set(\freq,10);
~fm4.set(\modRatio1,1200);
~fm4.set(\index1,12);
~fm4.set(\modRatio2,120);
~fm4.set(\index2,1200);

~fm4.release


k =NodeProxy(s);

k[0]={Line.kr(1,0,30)}

~fd = [
	//[midinoteArr,durArr]
	[[0,-2,0,3,5],[3,5+2,2,2,2]],
	[[0,-2,0,-2,0,-2,0,-2,0,-2,0,-2,0,-2,0,-7],1!16]
]

Pdef(\summer,
	Pmono(\synth1,
	\freq,Pseq([
		Pseq(~fd[0][0],4),
		Pseq(~fd[1][0],4),
		])+60,
\dur,Pseq([
		Pseq(~fd[0][1],4),
		Pseq(~fd[1][1],4),
		])*0.25,
// \modRatio1,Pwhite(1,10),
// \modRatio2,Pwhite(1,10),
// \index1,Pwhite(1,10),
// \index2,Pwhite(1,10),
\modRatio1,Pwhite(1,1),
\modRatio2,Pwhite(1,1),
\index1,Pwhite(1,1),
\index2,Pwhite(1,1),
	)
)



Pdef(\summer).play
Pdef(\summer).stop



(
	Pmono(
		\synth1,
	\freq,Pseq([0,-2,0,3,5],inf)+60,
\dur,Pseq([3,5+2,2,2,2],inf)*0.25,
// \modRatio1,Pwhite(1,10),
// \modRatio2,Pwhite(1,10),
// \index1,Pwhite(1,10),
// \index2,Pwhite(1,10),
\modRatio1,Pwhite(1,10),
\modRatio2,Pwhite(1,10),
\index1,Pwhite(1,10),
\index2,Pwhite(1,10),
\post,Pfunc{|e| 
	("modRatio1: "++e[\modRatio1]).postln;
		("modRatio2: "++e[\modRatio2]).postln;
		("index1: "++e[\index1]).postln;
		("index2: "++e[\index2]).postln;
		"".postln;}
	).play
}
)

Pdef(\summer).play
Pdef(\summer).stop

//-----------------------------------------------------------------------------------------
//SYNTH 

u.fmImpulse