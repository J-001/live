FaustLowboost : UGen
{
  *ar { | in1, freq(1000.0), gain(0.0) |
      ^this.multiNew('audio', in1, freq, gain)
  }

  *kr { | in1, freq(1000.0), gain(0.0) |
      ^this.multiNew('control', in1, freq, gain)
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
  ~synth = Synth(\faustLowboost, 
	[	in1:in1Bus.asMap,
		freq:freqVar,
		gain:gainVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustLowboost,
      SynthDef(\faustLowboost,
        { |out=0|
          Out.ar(out, 
            FaustLowboost.ar(
              \in1.ar(0), \freq.kr(1000.0), \gain.kr(0.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \freq:[20.0, 20000.0, 0, 0.1, 1000.0].asSpec,
          \gain:[-20.0, 20.0, 0, 0.1, 0.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustLowboost" }
}

