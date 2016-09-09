FaustPitchShifter : UGen
{
  *ar { | in1, shift(0.0), window(1000.0), xfade(10.0) |
      ^this.multiNew('audio', in1, shift, window, xfade)
  }

  *kr { | in1, shift(0.0), window(1000.0), xfade(10.0) |
      ^this.multiNew('control', in1, shift, window, xfade)
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
  ~synth = Synth(\faustPitchShifter, 
	[	in1:in1Bus.asMap,
		shift:shiftVar,
		window:windowVar,
		xfade:xfadeVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustPitchShifter,
      SynthDef(\faustPitchShifter,
        { |out=0|
          Out.ar(out, 
            FaustPitchShifter.ar(
              \in1.ar(0), \shift.kr(0.0), \window.kr(1000.0), \xfade.kr(10.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \shift:[-12.0, 12.0, 0, 0.1, 0.0].asSpec,
          \window:[50.0, 10000.0, 0, 1.0, 1000.0].asSpec,
          \xfade:[1.0, 10000.0, 0, 1.0, 10.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustPitchShifter" }
}

