FaustSmoothDelay : MultiOutUGen
{
  *ar { | in1, in2, delay(0.0), feedback(0.0), interpolation(10.0) |
      ^this.multiNew('audio', in1, in2, delay, feedback, interpolation)
  }

  *kr { | in1, in2, delay(0.0), feedback(0.0), interpolation(10.0) |
      ^this.multiNew('control', in1, in2, delay, feedback, interpolation)
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
  ~synth = Synth(\faustSmoothDelay, 
	[	in1:in1Bus.asMap,
		in2:in2Bus.asMap,
		delay:delayVar,
		feedback:feedbackVar,
		interpolation:interpolationVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustSmoothDelay,
      SynthDef(\faustSmoothDelay,
        { |out=0|
          Out.ar(out, 
            FaustSmoothDelay.ar(
              \in1.ar(0), \in2.ar(0), \delay.kr(0.0), \feedback.kr(0.0), \interpolation.kr(10.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \delay:[0.0, 5000.0, 0, 0.1, 0.0].asSpec,
          \feedback:[0.0, 100.0, 0, 0.1, 0.0].asSpec,
          \interpolation:[1.0, 100.0, 0, 0.1, 10.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustSmoothDelay" }
}

