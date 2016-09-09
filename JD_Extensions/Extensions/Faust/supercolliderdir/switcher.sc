FaustSwitch : MultiOutUGen
{
  *ar { | in1, in2, in3, in4, source_0_source_1(0.0) |
      ^this.multiNew('audio', in1, in2, in3, in4, source_0_source_1)
  }

  *kr { | in1, in2, in3, in4, source_0_source_1(0.0) |
      ^this.multiNew('control', in1, in2, in3, in4, source_0_source_1)
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
      ^this.initOutputs(2, rate)
  }
  
/*
  SynthDef - Typical usage in SuperCollider:

["  in1Bus = Bus.audio(s,1);\n", "  in2Bus = Bus.audio(s,1);\n", "  in3Bus = Bus.audio(s,1);\n", "  in4Bus = Bus.audio(s,1);\n"]
  ~synth = Synth(\faustSwitch, 
	[	in1:in1Bus.asMap,
		in2:in2Bus.asMap,
		in3:in3Bus.asMap,
		in4:in4Bus.asMap,
		source_0_source_1:source_0_source_1Var
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustSwitch,
      SynthDef(\faustSwitch,
        { |out=0|
          Out.ar(out, 
            FaustSwitch.ar(
              \in1.ar(0), \in2.ar(0), \in3.ar(0), \in4.ar(0), \source_0_source_1.kr(0.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \source_0_source_1:[0.0, 1.0, 0, 1.0, 0.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustSwitch" }
}

