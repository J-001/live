FaustBandfilter : UGen
{
  *ar { | in1, q_factor(50.0), freq(1000.0), gain(0.0) |
      ^this.multiNew('audio', in1, q_factor, freq, gain)
  }

  *kr { | in1, q_factor(50.0), freq(1000.0), gain(0.0) |
      ^this.multiNew('control', in1, q_factor, freq, gain)
  } 

  checkInputs {
    if (rate == 'audio', {
      1.do({|i|
        if (inputs.at(i).rate != 'audio', {
          ^(" input at index " + i + "(" + inputs.at(i) + 
            ") is not audio rate");
        });
      });
    });
    ^this.checkValidInputs
  }
  
/*
  SynthDef - Typical usage in SuperCollider:

["  in1Bus = Bus.audio(s,1);\n"]
  ~synth = Synth(\faustBandfilter, 
	[	in1:in1Bus.asMap,
		q_factor:q_factorVar,
		freq:freqVar,
		gain:gainVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustBandfilter,
      SynthDef(\faustBandfilter,
        { |out=0|
          Out.ar(out, 
            FaustBandfilter.ar(
              \in1.ar(0), \q_factor.kr(50.0), \freq.kr(1000.0), \gain.kr(0.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \q_factor:[0.1, 100.0, 0, 0.1, 50.0].asSpec,
          \freq:[20.0, 20000.0, 0, 1.0, 1000.0].asSpec,
          \gain:[-50.0, 50.0, 0, 0.1, 0.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustBandfilter" }
}

