FaustVumeter : MultiOutUGen
{
  *ar { | in1, in2 |
      ^this.multiNew('audio', in1, in2)
  }

  *kr { | in1, in2 |
      ^this.multiNew('control', in1, in2)
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
  ~synth = Synth(\faustVumeter, 
	[	in1:in1Bus.asMap,
		in2:in2Bus.asMap
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustVumeter,
      SynthDef(\faustVumeter,
        { |out=0|
          Out.ar(out, 
            FaustVumeter.ar(
              \in1.ar(0), \in2.ar(0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
      
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustVumeter" }
}

