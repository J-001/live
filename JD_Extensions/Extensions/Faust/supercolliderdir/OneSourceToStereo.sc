FaustOneSourceToStereo : MultiOutUGen
{
  *ar { | in1, angle(62831.0), radius(1.0) |
      ^this.multiNew('audio', in1, angle, radius)
  }

  *kr { | in1, angle(62831.0), radius(1.0) |
      ^this.multiNew('control', in1, angle, radius)
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
  ~synth = Synth(\faustOneSourceToStereo, 
	[	in1:in1Bus.asMap,
		angle:angleVar,
		radius:radiusVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustOneSourceToStereo,
      SynthDef(\faustOneSourceToStereo,
        { |out=0|
          Out.ar(out, 
            FaustOneSourceToStereo.ar(
              \in1.ar(0), \angle.kr(62831.0), \radius.kr(1.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \angle:[-6.2831855, 6.2831855, 0, 0.001, 62831.0].asSpec,
          \radius:[0.0, 5.0, 0, 0.001, 1.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustOneSourceToStereo" }
}

