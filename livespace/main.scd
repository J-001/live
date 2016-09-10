"~/Live/setup/init.scd".loadPaths;
n.clearSettings
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
TempoClock.default.tempo = b.loops.romance[0].tempo/120;
b.load;

Pdef(\romance,	
	Pbind(
		\bufnum, b.loops.romance[0], 
		\instrument, i.smpN,
		\rate, 1,
		\startPos, 0,
		\loop, 0,
		\amp, 0.3,
		\pan, 0,
		\legato,  1.01,
		\dur, Pfunc{|e|e[\rate] * e.bufnum.duration * TempoClock.default.tempo}
		// \dur,4
	)
).play;

n[\m4].on_({ Pdef(\romance).quant_(0).play});
n[\m4].off_({ Pdef(\romance).pause});

\romance@\romance.pd

\romance.nd.set(\spd,1,\amp,1)

f.load(\cutmix);

(\cutmix.play<<\romance)
\cutNoise@.1{SinOsc.ar*0.05}
\cutNoise@{ LFNoise1.kr(2)* WhiteNoise.ar*0.05}
\cutTrig@{Pulse.kr(0.5)*0.5+0.5}
"asdsadasdsda"
var ndakjsn;
Ndef(\pan,{
var sig = Mix(\in.ar([0,0]));
Pan2.ar(sig,\pan.kr(0))

	})
\panlvl@{SinOsc.kr(10)}
\panlvl.c
\cutmix.stop
\pan.p<<\cutmix<<.pan\panlvl

\pan.c
\panlvl.nd.source
~assadds = 
f.load(\dreamverb)

\romance.stop

\dreamverb.p<<\romance.s

\delay<<\saw

Ndef.all

\delay.nd.inProxy.play

p = ProxySpace.new(s,'NSpace').push;

~bleep={SinOsc.ar!2*EnvGen.ar(Env.perc,Impulse.kr(~bleeprate))}
~bleep.stop
~ksh={PinkNoise.ar*0.5!2*EnvGen.ar(Env.perc(0.01,0.01),Impulse.kr(~bleeprate))}
~saw= {RLPF.ar(Saw.ar,280)*1.0!2*EnvGen.ar(Env.perc,Impulse.kr(0.5))};
~delay={AllpassN.ar(\in.ar([0,0]))};
~vib={SinOsc.kr(\lfo.kr(1))*\in.ar([0,0])};

~delay.play<<>~bleep
~delay.pd

~bleep.stop

~delay <<> ~ksh
~romance = Pdef(\romance).stop
~delay.stop
~vib.set(\lfo,10)

n[\s0].on_({ ~vib.play});
n[\s0].off_({ ~vib.stop});
n[\m0].on_({ ~smp.play});
n[\m0].off_({ ~smp.stop});
s.meter

n[\fader0].cc_({|val| ~vib.set(\lfo, val.linlin(0,1,0.5,20))});
~smp.play
~vib.play<<>~smp
b.load
i.load(\smp)
var 

n[\r0].on_({ ~delay.play});
n[\r0].off_({ ~delay.stop});

n[\knob0].cc_({|val| ~noise = val.linlin(0,1,0.5,20)});
n[\knob1].cc_({|val| ~bleeprate = val.linlin(0,1,0.01,20)});

\romance.pd.play

"//-----------------------------------------------------------------------";
"//-----------------------------------------------------------------------";
"~/Live/setup/init.scd".loadPaths;
TempoClock.default.tempo = 1.25;
b.load
i.load(\smp)
p = ProxySpace(s, 'chillSpace').push;
i.mat(\smp)

~romance.pd.p
p.pop

\romance.pd.set(\rate,-1)

Pdef(\romance).play
Pbindef(\romance).source.set(\dur,Pfunc{|e|e[\rate] * e[\bufnum].duration * e[\legato] * TempoClock.tempo})
Pbindef(\romance).source.set(\dur,)
Pbindef(\romance,\rate,1);

~romance.sm(\rate, ~romanceRate,\lfo.asSpec);
~romance.unset(\rate)
~rom?@'rate amp, freq'

~rom@!.rate Pseq([2,1])
set
~romanceRate@{|val|val}
n[\knob0].cc_({|val| ~romance.sm(\rate, val,\lfo)});

n[\s0].on_({ ~romance.play});
n[\s0].off_({ ~romance.stop});


~romanceOsc = {|val=1|val}

localSpec
\romance+>\romanceChop

Pdef(\romanceChop, 	
	Pbind(
		\type,Prand([\rest,\note],inf),
		\legato,Prand([1.0],inf),
		\loop,0,
		\rate, Prand([0.5,100,10],inf),
		\startPos,Pwhite(0.0,1.0),
		\rel,0.2,
		\amp,0.5,
		\dur,Prand([0.0625,0.5,1.0,4],inf)
		)
		<>Pdef(\romance)
).envir;

~romanceChop.pd.envir

~dreamverb<<~romanceChop.s
~dreamverb='dreamverb.dream'

\romance.pd.envir

'a'++'b'

a = Pbind(\instrument, \default, \freq, Prand([1, 1.2, 2, 2.5, 3, 4], inf) * 200, \dur, 0.1);

p
Pmono
Pbindef(\new2,\instrument,\default,\freq,200).p
a = Pbindef(\new2).pattern.source.source.patternpairs[3].source
proxyClass

l = LazyEnvir.new.push

l.proxyClass = \PbindProxy
a = ();

l.envir.keysValuesDo{|key, val|
	a[key] = val
}

a.b
~new.set_(\new2,\instrument,\default,\freq,200)
Pdef.clear
"~/Live/setup/init.scd".loadPaths
~a


b.load
q = Pdef(\a,p)
q.play
q = PbindProxy
q.source.(\rate,2)
q.source.rst(\rate)
PbindProxy

d = ()
p.pairs.reshape(2,2).do{|pair|
	d[pair[0]] = pair[1];
}	
(
	\sym
).play()
x = nil

d.keysValuesDo{|key, val|
	key.postln;
	val.source.postln;
	"".postln;
}

p.pairs.pairsDo {|key, val|
	key.postln;
	val.source.postln;
}


\rom.asp@@.rate 3
aP.


\rom_n.


"~/Live/setup/init.scd".loadPaths;
TempoClock.default.tempo = 1.25;
b.load
i.load(\smp)
p = ProxySpace(s, 'chillSpace').push;
i.mat(\smp)


p.set(\dur,1).play
p.play

p@@[\rate, ]


p.play
p.originalArgs
p@![\dur,1,\amp,Pseq([0.1,0.2],inf)]


\romance_P.rate_( {|arg1 = 1| Lfo.kr(arg1)} );
\romance_P.ktr=();
\romance_P.ktr[\rate] = NodeProxy.control(s).source_(func);
\romance_P.rate_(\romance_P.ktr[\rate]);

\romance.k_rate.set(\arg1, someOtherValue)

\romance_P.rate_()

//CUSTOM GLOBAL FUNCS
/*
(
	this.addUniqueMethod(\setup, {"~/Live/setup/init.scd".loadPaths});
	this.removeUniqueMethod(\setup);

	preProcessor = {|code|
		code
	};
	this.addCodeMap('__', "this\.".quote);
	this.removeCodeMap("this.".quote);
)
*/
p = ProxySpace.push
"~/Live/setup/init.scd".loadPaths
i.load(\bass)
p.pop
Ndef(\k, {|val=0.01| val})

~k = {|val=0.01| SinOsc.kr(2).range(0.0,0.5)}
Ndef(\k , {|val=0.01| SinOsc.kr(20).range(0.0,0.5)})

Pdef(\b, PbindProxy(
	\instrument, \bass,
	\freq,[70,60],
	// \amp, {|spd =  0.5| SinOsc.kr(spd).range(0.0,0.1) },
	// \amp, Ndef(\k),
	\amp,0.5,
	\legato,2,
	\dur,Pwrand([0.5,Pseq([0.75,0.25]*0.5),Pseq([0.25,0.25])],[0.7,0.2,0.1],inf),
	\rel ,Pkey(\dur)*Pkey(\legato)
	)
).s.p

Pdef(\b).amp


\b.amp_({|spd =  0.5, max = 0.1| max.lag(0.2) })
\b.k_amp_(\max,0.5)

\b_p.p

\b.asP.source.kp.amp.set(\max, )

Pdef.clear

Pdef(\b).set(\amp, Ndef(\k))

Pdef(\b).source.kpr[6].source_({|val=0.01| val })

{ SinOsc.ar *  0.1}.play

Pdef(\a, PbindProxy()).play
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------

/*
	TO DO:
	Sublime plugin:
	Evaluate block without brackets expand to tabbed space and evaluate
	LiveInput:
	
	//REDESIGN MIDI
	AbstractMidiControl
	NKControl: AbstractMIDIControl
	NKTrig : NKControl
	
	AbstractMidiController
	NK2 : AbstractMIDIController

	NKConfig && 

	LiveInput:
	record
	retreiveRecording->
	setToChannel -> proxy

	UPDATE SNIPPETS

*/



{SinOsc.ar*0.1}.play;
"~/Live/setup/init.scd".loadPaths

s.meter
	
\sndIn@

{
	Pan2.ar(
		AllpassN.ar(
			Mix(RLPF.ar( SoundIn.ar(0),(Scale.aeolian.degrees + [92, 120]) .midicps,0.01)*0.01),
			),
		 LFNoise1.kr(0.1).range(-1,1)
		)
	}.play
s.meter
\sndIn.p


Fdef(\freq, {|freq = 60| freq})


"~/Live/setup/init.scd".loadPaths

LiveInput(\zeplin, 0);
\zeplin_L.play
\zeplin_N<<.amp{|freq = 10| SinOsc.kr(freq).linlin(-1,1,0,1)}

\zeplin_N.p@{|amp = 0.1|SinOsc.ar * amp}
\zeplin.amp_(0.1)
\zeplin.amp_(0.05)
Ndef(\zeplin).amp_(0.2)
LiveInput.all
Ndef(\zeplin, {|amp = 1|SoundIn.ar(0) * amp})
{SoundIn.ar(0)}.play


s.options.inDevice
l.name;


