FaustFreeverb : MultiOutUGen
{
  *ar { | in1, in2, damp(0.5), roomsize(0.5), wet(0.3333) |
      ^this.multiNew('audio', in1, in2, damp, roomsize, wet)
  }

  *kr { | in1, in2, damp(0.5), roomsize(0.5), wet(0.3333) |
      ^this.multiNew('control', in1, in2, damp, roomsize, wet)
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
  ~synth = Synth(\faustFreeverb, 
	[	in1:in1Bus.asMap,
		in2:in2Bus.asMap,
		damp:dampVar,
		roomsize:roomsizeVar,
		wet:wetVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustFreeverb,
      SynthDef(\faustFreeverb,
        { |out=0|
          Out.ar(out, 
            FaustFreeverb.ar(
              \in1.ar(0), \in2.ar(0), \damp.kr(0.5), \roomsize.kr(0.5), \wet.kr(0.3333)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \damp:[0.0, 1.0, 0, 0.025, 0.5].asSpec,
          \roomsize:[0.0, 1.0, 0, 0.025, 0.5].asSpec,
          \wet:[0.0, 1.0, 0, 0.025, 0.3333].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustFreeverb" }
}

