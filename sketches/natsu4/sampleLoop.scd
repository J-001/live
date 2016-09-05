t=TempoClock(149.77/120).permanent_(true);

Pdef(\bassloop,
			Pbind(
				\bufnum,b.loops.romance[0],
				\instrument,Pfunc{|e|
					var synth;
					synth  = if (e[\bufnum].numChannels==1, 
					{\samplerM},
					{\samplerS});
					synth.postln;
					synth;
				},
				\loop,1,
				\amp,0.2,
				\rate, Pfunc{|e|(e[\bufnum].duration*t.tempo)/16},
				\legato,1.0,
				\dur,16
				)
	)

Pdef(\bassloop).play(t,quant:8)
Pdef(\bassloop).stop;
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------


Ndef(\bloop)
Ndef(\bloop).source = Pdef(\bassloop);
n = Ndef;

Ndef(\bloop).stop

Ndef(\spaceverb) <<>.in Ndef(\bloop);
Ndef(\spaceverb).play

s.meter

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
Pdef(\drumfill,
	Pbind(
		\bufnum,Prand(b.kit.choose,5),
		\instrument,Pfunc{|e|
			var synth;
			e[\bufnum].numChannels.postln;
			synth  = if (e[\bufnum].numChannels==1, 
			{\samplerM},
			{\samplerS});
			synth.postln;
			synth;
		},
		\loop,1,
		\rate,Pseq((0..4).reverse*1)+1,
		\legato,1.01,
		\amp,0.3,
		\dur,Prand([1,2,2,1,2])*0.5
		)
	)
Pdef(\drumfill).play(t)
Pdef(\drumfill).stop

Pdef(\fill,
	Pseq([3.5.rest,Pdef(\drumfill)],inf)
	).play(t,quant:8);

Pdef(\fill).stop

Pdef(\multibell,
    Ppar(Pbind(
         	\bufnum,b.bell.hand[0],
    		\instrument,Pfunc{|e|
    			var synth;
    			e[\bufnum].numChannels.postln;
    			synth  = if (e[\bufnum].numChannels==1, 
    			{\samplerM},
    			{\samplerS});
    			synth.postln;
    			synth;
    		},
    		\amp,0.1,
    		\pan,Pwhite(-1.0,1.0),
    		\loop,1,
    		\legato,1,
    		\rate,0.25* Pwhite(0.9,1.12)*[1,9/8,3/2,16/9,1+8/7],
    		// \rate,Prand([1,9/8,3/2,16/9,1+8/7],inf),
      //       \dur, Prand([0.25,0.5],inf)
      		\dur,4
        )!1)
);

Pdef(\multibell).play(t,quant:16);
Pdef(\multibell).set(\amp,0.001)
	

