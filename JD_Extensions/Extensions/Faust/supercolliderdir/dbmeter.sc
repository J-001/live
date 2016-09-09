FaustDbmeter : MultiOutUGen
{
  *ar { | in1, in2, in3, in4, in5, in6, in7, in8 |
      ^this.multiNew('audio', in1, in2, in3, in4, in5, in6, in7, in8)
  }

  *kr { | in1, in2, in3, in4, in5, in6, in7, in8 |
      ^this.multiNew('control', in1, in2, in3, in4, in5, in6, in7, in8)
  } 

  checkInputs {
    if (rate == 'audio', {
      8.do({|i|
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
      ^this.initOutputs(8, rate)
  }
  
/*
  SynthDef - Typical usage in SuperCollider:

["  in1Bus = Bus.audio(s,1);\n", "  in2Bus = Bus.audio(s,1);\n", "  in3Bus = Bus.audio(s,1);\n", "  in4Bus = Bus.audio(s,1);\n", "  in5Bus = Bus.audio(s,1);\n", "  in6Bus = Bus.audio(s,1);\n", "  in7Bus = Bus.audio(s,1);\n", "  in8Bus = Bus.audio(s,1);\n"]
  ~synth = Synth(\faustDbmeter, 
	[	in1:in1Bus.asMap,
		in2:in2Bus.asMap,
		in3:in3Bus.asMap,
		in4:in4Bus.asMap,
		in5:in5Bus.asMap,
		in6:in6Bus.asMap,
		in7:in7Bus.asMap,
		in8:in8Bus.asMap
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustDbmeter,
      SynthDef(\faustDbmeter,
        { |out=0|
          Out.ar(out, 
            FaustDbmeter.ar(
              \in1.ar(0), \in2.ar(0), \in3.ar(0), \in4.ar(0), \in5.ar(0), \in6.ar(0), \in7.ar(0), \in8.ar(0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
      
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustDbmeter" }
}

