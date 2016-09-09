FaustCapture : UGen
{
  *ar { | in1, capture(0.0), level(0.0) |
      ^this.multiNew('audio', in1, capture, level)
  }

  *kr { | in1, capture(0.0), level(0.0) |
      ^this.multiNew('control', in1, capture, level)
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
  ~synth = Synth(\faustCapture, 
	[	in1:in1Bus.asMap,
		capture:captureVar,
		level:levelVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustCapture,
      SynthDef(\faustCapture,
        { |out=0|
          Out.ar(out, 
            FaustCapture.ar(
              \in1.ar(0), \capture.kr(0.0), \level.kr(0.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \capture:[0.0, 1, 0, 1, 0.0].asSpec,
          \level:[-96.0, 4.0, 0, 0.1, 0.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustCapture" }
}

