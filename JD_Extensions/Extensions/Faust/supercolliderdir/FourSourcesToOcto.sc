FaustFourSourcesToOcto : MultiOutUGen
{
  *ar { | in1, in2, in3, in4, angle1(62831.0), angle2(62831.0), angle3(62831.0), angle4(62831.0), radius1(1.0), radius2(1.0), radius3(1.0), radius4(1.0) |
      ^this.multiNew('audio', in1, in2, in3, in4, angle1, angle2, angle3, angle4, radius1, radius2, radius3, radius4)
  }

  *kr { | in1, in2, in3, in4, angle1(62831.0), angle2(62831.0), angle3(62831.0), angle4(62831.0), radius1(1.0), radius2(1.0), radius3(1.0), radius4(1.0) |
      ^this.multiNew('control', in1, in2, in3, in4, angle1, angle2, angle3, angle4, radius1, radius2, radius3, radius4)
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
      ^this.initOutputs(8, rate)
  }
  
/*
  SynthDef - Typical usage in SuperCollider:

["  in1Bus = Bus.audio(s,1);\n", "  in2Bus = Bus.audio(s,1);\n", "  in3Bus = Bus.audio(s,1);\n", "  in4Bus = Bus.audio(s,1);\n"]
  ~synth = Synth(\faustFourSourcesToOcto, 
	[	in1:in1Bus.asMap,
		in2:in2Bus.asMap,
		in3:in3Bus.asMap,
		in4:in4Bus.asMap,
		angle1:angle1Var,
		angle2:angle2Var,
		angle3:angle3Var,
		angle4:angle4Var,
		radius1:radius1Var,
		radius2:radius2Var,
		radius3:radius3Var,
		radius4:radius4Var
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustFourSourcesToOcto,
      SynthDef(\faustFourSourcesToOcto,
        { |out=0|
          Out.ar(out, 
            FaustFourSourcesToOcto.ar(
              \in1.ar(0), \in2.ar(0), \in3.ar(0), \in4.ar(0), \angle1.kr(62831.0), \angle2.kr(62831.0), \angle3.kr(62831.0), \angle4.kr(62831.0), \radius1.kr(1.0), \radius2.kr(1.0), \radius3.kr(1.0), \radius4.kr(1.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \angle1:[-6.2831855, 6.2831855, 0, 0.001, 62831.0].asSpec,
          \angle2:[-6.2831855, 6.2831855, 0, 0.001, 62831.0].asSpec,
          \angle3:[-6.2831855, 6.2831855, 0, 0.001, 62831.0].asSpec,
          \angle4:[-6.2831855, 6.2831855, 0, 0.001, 62831.0].asSpec,
          \radius1:[0.0, 5.0, 0, 0.001, 1.0].asSpec,
          \radius2:[0.0, 5.0, 0, 0.001, 1.0].asSpec,
          \radius3:[0.0, 5.0, 0, 0.001, 1.0].asSpec,
          \radius4:[0.0, 5.0, 0, 0.001, 1.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustFourSourcesToOcto" }
}

