FaustEcho : UGen
{
  *ar { | in1, feedback(0.0), millisecond(0.0) |
      ^this.multiNew('audio', in1, feedback, millisecond)
  }

  *kr { | in1, feedback(0.0), millisecond(0.0) |
      ^this.multiNew('control', in1, feedback, millisecond)
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
  ~synth = Synth(\faustEcho, 
	[	in1:in1Bus.asMap,
		feedback:feedbackVar,
		millisecond:millisecondVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustEcho,
      SynthDef(\faustEcho,
        { |out=0|
          Out.ar(out, 
            FaustEcho.ar(
              \in1.ar(0), \feedback.kr(0.0), \millisecond.kr(0.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \feedback:[0.0, 100.0, 0, 0.1, 0.0].asSpec,
          \millisecond:[0.0, 1000.0, 0, 0.1, 0.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustEcho" }
}

