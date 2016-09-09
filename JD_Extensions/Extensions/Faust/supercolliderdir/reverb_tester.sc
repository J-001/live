FaustReverbTester : MultiOutUGen
{
  *ar { | in1, in2, mute_ext_inputs(0.0), pink_noise(0.0), left(0.0), center(0.0), right(0.0) |
      ^this.multiNew('audio', in1, in2, mute_ext_inputs, pink_noise, left, center, right)
  }

  *kr { | in1, in2, mute_ext_inputs(0.0), pink_noise(0.0), left(0.0), center(0.0), right(0.0) |
      ^this.multiNew('control', in1, in2, mute_ext_inputs, pink_noise, left, center, right)
  } 

  checkInputs {
    if (rate == 'audio', {
      2.do({|i|
        if (inputs.at(i).rate != 'audio', {
          ^(" input at index " + i + "(" + inputs.at(i) + 
            ") is not audio rate");
        });
      });
    });
    ^this.checkValidInputs
  }

  init { | ... theInputs |
      inputs = theInputs
      ^this.initOutputs(2, rate)
  }
  
/*
  SynthDef - Typical usage in SuperCollider:

["  in1Bus = Bus.audio(s,1);\n", "  in2Bus = Bus.audio(s,1);\n"]
  ~synth = Synth(\faustReverbTester, 
	[	in1:in1Bus.asMap,
		in2:in2Bus.asMap,
		mute_ext_inputs:mute_ext_inputsVar,
		pink_noise:pink_noiseVar,
		left:leftVar,
		center:centerVar,
		right:rightVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustReverbTester,
      SynthDef(\faustReverbTester,
        { |out=0|
          Out.ar(out, 
            FaustReverbTester.ar(
              \in1.ar(0), \in2.ar(0), \mute_ext_inputs.kr(0.0), \pink_noise.kr(0.0), \left.kr(0.0), \center.kr(0.0), \right.kr(0.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \mute_ext_inputs:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \pink_noise:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \left:[0.0, 1, 0, 1, 0.0].asSpec,
          \center:[0.0, 1, 0, 1, 0.0].asSpec,
          \right:[0.0, 1, 0, 1, 0.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustReverbTester" }
}

