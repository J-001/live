FaustQuadecho : MultiOutUGen
{
  *ar { | in1, in2, in3, in4, feedback(0.0), millisecond(0.0) |
      ^this.multiNew('audio', in1, in2, in3, in4, feedback, millisecond)
  }

  *kr { | in1, in2, in3, in4, feedback(0.0), millisecond(0.0) |
      ^this.multiNew('control', in1, in2, in3, in4, feedback, millisecond)
  } 

  checkInputs {
    if (rate == 'audio', {
      4.do({|i|
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
      ^this.initOutputs(4, rate)
  }
  
/*
  SynthDef - Typical usage in SuperCollider:

["  in1Bus = Bus.audio(s,1);\n", "  in2Bus = Bus.audio(s,1);\n", "  in3Bus = Bus.audio(s,1);\n", "  in4Bus = Bus.audio(s,1);\n"]
  ~synth = Synth(\faustQuadecho, 
	[	in1:in1Bus.asMap,
		in2:in2Bus.asMap,
		in3:in3Bus.asMap,
		in4:in4Bus.asMap,
		feedback:feedbackVar,
		millisecond:millisecondVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustQuadecho,
      SynthDef(\faustQuadecho,
        { |out=0|
          Out.ar(out, 
            FaustQuadecho.ar(
              \in1.ar(0), \in2.ar(0), \in3.ar(0), \in4.ar(0), \feedback.kr(0.0), \millisecond.kr(0.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \feedback:[0.0, 100.0, 0, 0.1, 0.0].asSpec,
          \millisecond:[0.0, 1000.0, 0, 0.1, 0.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustQuadecho" }
}

