FaustMixer : MultiOutUGen
{
  *ar { | in1, in2, in3, in4, in5, in6, in7, in8, pan_0(0.0), vslider_0x7ffc90634660_0(0.0), mute_0(0.0), pan_1(0.0), vslider_0x7ffc9063d6a0_1(0.0), mute_1(0.0), pan_2(0.0), vslider_0x7ffc90645600_2(0.0), mute_2(0.0), pan_3(0.0), vslider_0x7ffc9064d560_3(0.0), mute_3(0.0), pan_4(0.0), vslider_0x7ffc906554b0_4(0.0), mute_4(0.0), pan_5(0.0), vslider_0x7ffc9065d400_5(0.0), mute_5(0.0), pan_6(0.0), vslider_0x7ffc90665340_6(0.0), mute_6(0.0), pan_7(0.0), vslider_0x7ffc9066d180_7(0.0), mute_7(0.0), vslider_0x7ffc90675260_7(0.0) |
      ^this.multiNew('audio', in1, in2, in3, in4, in5, in6, in7, in8, pan_0, vslider_0x7ffc90634660_0, mute_0, pan_1, vslider_0x7ffc9063d6a0_1, mute_1, pan_2, vslider_0x7ffc90645600_2, mute_2, pan_3, vslider_0x7ffc9064d560_3, mute_3, pan_4, vslider_0x7ffc906554b0_4, mute_4, pan_5, vslider_0x7ffc9065d400_5, mute_5, pan_6, vslider_0x7ffc90665340_6, mute_6, pan_7, vslider_0x7ffc9066d180_7, mute_7, vslider_0x7ffc90675260_7)
  }

  *kr { | in1, in2, in3, in4, in5, in6, in7, in8, pan_0(0.0), vslider_0x7ffc90634660_0(0.0), mute_0(0.0), pan_1(0.0), vslider_0x7ffc9063d6a0_1(0.0), mute_1(0.0), pan_2(0.0), vslider_0x7ffc90645600_2(0.0), mute_2(0.0), pan_3(0.0), vslider_0x7ffc9064d560_3(0.0), mute_3(0.0), pan_4(0.0), vslider_0x7ffc906554b0_4(0.0), mute_4(0.0), pan_5(0.0), vslider_0x7ffc9065d400_5(0.0), mute_5(0.0), pan_6(0.0), vslider_0x7ffc90665340_6(0.0), mute_6(0.0), pan_7(0.0), vslider_0x7ffc9066d180_7(0.0), mute_7(0.0), vslider_0x7ffc90675260_7(0.0) |
      ^this.multiNew('control', in1, in2, in3, in4, in5, in6, in7, in8, pan_0, vslider_0x7ffc90634660_0, mute_0, pan_1, vslider_0x7ffc9063d6a0_1, mute_1, pan_2, vslider_0x7ffc90645600_2, mute_2, pan_3, vslider_0x7ffc9064d560_3, mute_3, pan_4, vslider_0x7ffc906554b0_4, mute_4, pan_5, vslider_0x7ffc9065d400_5, mute_5, pan_6, vslider_0x7ffc90665340_6, mute_6, pan_7, vslider_0x7ffc9066d180_7, mute_7, vslider_0x7ffc90675260_7)
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
      ^this.initOutputs(2, rate)
  }
  
/*
  SynthDef - Typical usage in SuperCollider:

["  in1Bus = Bus.audio(s,1);\n", "  in2Bus = Bus.audio(s,1);\n", "  in3Bus = Bus.audio(s,1);\n", "  in4Bus = Bus.audio(s,1);\n", "  in5Bus = Bus.audio(s,1);\n", "  in6Bus = Bus.audio(s,1);\n", "  in7Bus = Bus.audio(s,1);\n", "  in8Bus = Bus.audio(s,1);\n"]
  ~synth = Synth(\faustMixer, 
	[	in1:in1Bus.asMap,
		in2:in2Bus.asMap,
		in3:in3Bus.asMap,
		in4:in4Bus.asMap,
		in5:in5Bus.asMap,
		in6:in6Bus.asMap,
		in7:in7Bus.asMap,
		in8:in8Bus.asMap,
		pan_0:pan_0Var,
		vslider_0x7ffc90634660_0:vslider_0x7ffc90634660_0Var,
		mute_0:mute_0Var,
		pan_1:pan_1Var,
		vslider_0x7ffc9063d6a0_1:vslider_0x7ffc9063d6a0_1Var,
		mute_1:mute_1Var,
		pan_2:pan_2Var,
		vslider_0x7ffc90645600_2:vslider_0x7ffc90645600_2Var,
		mute_2:mute_2Var,
		pan_3:pan_3Var,
		vslider_0x7ffc9064d560_3:vslider_0x7ffc9064d560_3Var,
		mute_3:mute_3Var,
		pan_4:pan_4Var,
		vslider_0x7ffc906554b0_4:vslider_0x7ffc906554b0_4Var,
		mute_4:mute_4Var,
		pan_5:pan_5Var,
		vslider_0x7ffc9065d400_5:vslider_0x7ffc9065d400_5Var,
		mute_5:mute_5Var,
		pan_6:pan_6Var,
		vslider_0x7ffc90665340_6:vslider_0x7ffc90665340_6Var,
		mute_6:mute_6Var,
		pan_7:pan_7Var,
		vslider_0x7ffc9066d180_7:vslider_0x7ffc9066d180_7Var,
		mute_7:mute_7Var,
		vslider_0x7ffc90675260_7:vslider_0x7ffc90675260_7Var
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustMixer,
      SynthDef(\faustMixer,
        { |out=0|
          Out.ar(out, 
            FaustMixer.ar(
              \in1.ar(0), \in2.ar(0), \in3.ar(0), \in4.ar(0), \in5.ar(0), \in6.ar(0), \in7.ar(0), \in8.ar(0), \pan_0.kr(0.0), \vslider_0x7ffc90634660_0.kr(0.0), \mute_0.kr(0.0), \pan_1.kr(0.0), \vslider_0x7ffc9063d6a0_1.kr(0.0), \mute_1.kr(0.0), \pan_2.kr(0.0), \vslider_0x7ffc90645600_2.kr(0.0), \mute_2.kr(0.0), \pan_3.kr(0.0), \vslider_0x7ffc9064d560_3.kr(0.0), \mute_3.kr(0.0), \pan_4.kr(0.0), \vslider_0x7ffc906554b0_4.kr(0.0), \mute_4.kr(0.0), \pan_5.kr(0.0), \vslider_0x7ffc9065d400_5.kr(0.0), \mute_5.kr(0.0), \pan_6.kr(0.0), \vslider_0x7ffc90665340_6.kr(0.0), \mute_6.kr(0.0), \pan_7.kr(0.0), \vslider_0x7ffc9066d180_7.kr(0.0), \mute_7.kr(0.0), \vslider_0x7ffc90675260_7.kr(0.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \pan_0:[-90.0, 90.0, 0, 1.0, 0.0].asSpec,
          \vslider_0x7ffc90634660_0:[-70.0, 4.0, 0, 0.1, 0.0].asSpec,
          \mute_0:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \pan_1:[-90.0, 90.0, 0, 1.0, 0.0].asSpec,
          \vslider_0x7ffc9063d6a0_1:[-70.0, 4.0, 0, 0.1, 0.0].asSpec,
          \mute_1:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \pan_2:[-90.0, 90.0, 0, 1.0, 0.0].asSpec,
          \vslider_0x7ffc90645600_2:[-70.0, 4.0, 0, 0.1, 0.0].asSpec,
          \mute_2:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \pan_3:[-90.0, 90.0, 0, 1.0, 0.0].asSpec,
          \vslider_0x7ffc9064d560_3:[-70.0, 4.0, 0, 0.1, 0.0].asSpec,
          \mute_3:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \pan_4:[-90.0, 90.0, 0, 1.0, 0.0].asSpec,
          \vslider_0x7ffc906554b0_4:[-70.0, 4.0, 0, 0.1, 0.0].asSpec,
          \mute_4:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \pan_5:[-90.0, 90.0, 0, 1.0, 0.0].asSpec,
          \vslider_0x7ffc9065d400_5:[-70.0, 4.0, 0, 0.1, 0.0].asSpec,
          \mute_5:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \pan_6:[-90.0, 90.0, 0, 1.0, 0.0].asSpec,
          \vslider_0x7ffc90665340_6:[-70.0, 4.0, 0, 0.1, 0.0].asSpec,
          \mute_6:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \pan_7:[-90.0, 90.0, 0, 1.0, 0.0].asSpec,
          \vslider_0x7ffc9066d180_7:[-70.0, 4.0, 0, 0.1, 0.0].asSpec,
          \mute_7:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \vslider_0x7ffc90675260_7:[-70.0, 4.0, 0, 0.1, 0.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustMixer" }
}

