FaustPanpot : MultiOutUGen
{
  *ar { | in1, pan(0.0) |
      ^this.multiNew('audio', in1, pan)
  }

  *kr { | in1, pan(0.0) |
      ^this.multiNew('control', in1, pan)
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

  init { | ... theInputs |
      inputs = theInputs
      ^this.initOutputs(2, rate)
  }
  
/*
  SynthDef - Typical usage in SuperCollider:

["  in1Bus = Bus.audio(s,1);\n"]
  ~synth = Synth(\faustPanpot, 
	[	in1:in1Bus.asMap,
		pan:panVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustPanpot,
      SynthDef(\faustPanpot,
        { |out=0|
          Out.ar(out, 
            FaustPanpot.ar(
              \in1.ar(0), \pan.kr(0.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \pan:[-90.0, 90.0, 0, 1.0, 0.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustPanpot" }
}

