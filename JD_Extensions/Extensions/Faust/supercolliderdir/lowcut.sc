FaustLowcut : UGen
{
  *ar { | in1, attenuation(0.0), freq(100.0) |
      ^this.multiNew('audio', in1, attenuation, freq)
  }

  *kr { | in1, attenuation(0.0), freq(100.0) |
      ^this.multiNew('control', in1, attenuation, freq)
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
  ~synth = Synth(\faustLowcut, 
	[	in1:in1Bus.asMap,
		attenuation:attenuationVar,
		freq:freqVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustLowcut,
      SynthDef(\faustLowcut,
        { |out=0|
          Out.ar(out, 
            FaustLowcut.ar(
              \in1.ar(0), \attenuation.kr(0.0), \freq.kr(100.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \attenuation:[-96.0, 10.0, 0, 0.1, 0.0].asSpec,
          \freq:[20.0, 5000.0, 0, 1.0, 100.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustLowcut" }
}

