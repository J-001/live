FaustTapiir : MultiOutUGen
{
  *ar { | in1, in2, delay_0(0.0), gain_0(1.0), input_0_0(1.0), input_1_0(1.0), tap_0_0(0.0), tap_1_0(0.0), tap_2_0(0.0), tap_3_0(0.0), tap_4_0(0.0), tap_5_0(0.0), delay_1(0.0), gain_1(1.0), input_0_1(1.0), input_1_1(1.0), tap_0_1(0.0), tap_1_1(0.0), tap_2_1(0.0), tap_3_1(0.0), tap_4_1(0.0), tap_5_1(0.0), delay_2(0.0), gain_2(1.0), input_0_2(1.0), input_1_2(1.0), tap_0_2(0.0), tap_1_2(0.0), tap_2_2(0.0), tap_3_2(0.0), tap_4_2(0.0), tap_5_2(0.0), delay_3(0.0), gain_3(1.0), input_0_3(1.0), input_1_3(1.0), tap_0_3(0.0), tap_1_3(0.0), tap_2_3(0.0), tap_3_3(0.0), tap_4_3(0.0), tap_5_3(0.0), delay_4(0.0), gain_4(1.0), input_0_4(1.0), input_1_4(1.0), tap_0_4(0.0), tap_1_4(0.0), tap_2_4(0.0), tap_3_4(0.0), tap_4_4(0.0), tap_5_4(0.0), delay_5(0.0), gain_5(1.0), input_0_5(1.0), input_1_5(1.0), tap_0_5(0.0), tap_1_5(0.0), tap_2_5(0.0), tap_3_5(0.0), tap_4_5(0.0), tap_5_5(0.0), gain_6(1.0), input_0_6(1.0), input_1_6(1.0), tap_0_6(0.0), tap_1_6(0.0), tap_2_6(0.0), tap_3_6(0.0), tap_4_6(0.0), tap_5_6(0.0), gain_7(1.0), input_0_7(1.0), input_1_7(1.0), tap_0_7(0.0), tap_1_7(0.0), tap_2_7(0.0), tap_3_7(0.0), tap_4_7(0.0), tap_5_7(0.0) |
      ^this.multiNew('audio', in1, in2, delay_0, gain_0, input_0_0, input_1_0, tap_0_0, tap_1_0, tap_2_0, tap_3_0, tap_4_0, tap_5_0, delay_1, gain_1, input_0_1, input_1_1, tap_0_1, tap_1_1, tap_2_1, tap_3_1, tap_4_1, tap_5_1, delay_2, gain_2, input_0_2, input_1_2, tap_0_2, tap_1_2, tap_2_2, tap_3_2, tap_4_2, tap_5_2, delay_3, gain_3, input_0_3, input_1_3, tap_0_3, tap_1_3, tap_2_3, tap_3_3, tap_4_3, tap_5_3, delay_4, gain_4, input_0_4, input_1_4, tap_0_4, tap_1_4, tap_2_4, tap_3_4, tap_4_4, tap_5_4, delay_5, gain_5, input_0_5, input_1_5, tap_0_5, tap_1_5, tap_2_5, tap_3_5, tap_4_5, tap_5_5, gain_6, input_0_6, input_1_6, tap_0_6, tap_1_6, tap_2_6, tap_3_6, tap_4_6, tap_5_6, gain_7, input_0_7, input_1_7, tap_0_7, tap_1_7, tap_2_7, tap_3_7, tap_4_7, tap_5_7)
  }

  *kr { | in1, in2, delay_0(0.0), gain_0(1.0), input_0_0(1.0), input_1_0(1.0), tap_0_0(0.0), tap_1_0(0.0), tap_2_0(0.0), tap_3_0(0.0), tap_4_0(0.0), tap_5_0(0.0), delay_1(0.0), gain_1(1.0), input_0_1(1.0), input_1_1(1.0), tap_0_1(0.0), tap_1_1(0.0), tap_2_1(0.0), tap_3_1(0.0), tap_4_1(0.0), tap_5_1(0.0), delay_2(0.0), gain_2(1.0), input_0_2(1.0), input_1_2(1.0), tap_0_2(0.0), tap_1_2(0.0), tap_2_2(0.0), tap_3_2(0.0), tap_4_2(0.0), tap_5_2(0.0), delay_3(0.0), gain_3(1.0), input_0_3(1.0), input_1_3(1.0), tap_0_3(0.0), tap_1_3(0.0), tap_2_3(0.0), tap_3_3(0.0), tap_4_3(0.0), tap_5_3(0.0), delay_4(0.0), gain_4(1.0), input_0_4(1.0), input_1_4(1.0), tap_0_4(0.0), tap_1_4(0.0), tap_2_4(0.0), tap_3_4(0.0), tap_4_4(0.0), tap_5_4(0.0), delay_5(0.0), gain_5(1.0), input_0_5(1.0), input_1_5(1.0), tap_0_5(0.0), tap_1_5(0.0), tap_2_5(0.0), tap_3_5(0.0), tap_4_5(0.0), tap_5_5(0.0), gain_6(1.0), input_0_6(1.0), input_1_6(1.0), tap_0_6(0.0), tap_1_6(0.0), tap_2_6(0.0), tap_3_6(0.0), tap_4_6(0.0), tap_5_6(0.0), gain_7(1.0), input_0_7(1.0), input_1_7(1.0), tap_0_7(0.0), tap_1_7(0.0), tap_2_7(0.0), tap_3_7(0.0), tap_4_7(0.0), tap_5_7(0.0) |
      ^this.multiNew('control', in1, in2, delay_0, gain_0, input_0_0, input_1_0, tap_0_0, tap_1_0, tap_2_0, tap_3_0, tap_4_0, tap_5_0, delay_1, gain_1, input_0_1, input_1_1, tap_0_1, tap_1_1, tap_2_1, tap_3_1, tap_4_1, tap_5_1, delay_2, gain_2, input_0_2, input_1_2, tap_0_2, tap_1_2, tap_2_2, tap_3_2, tap_4_2, tap_5_2, delay_3, gain_3, input_0_3, input_1_3, tap_0_3, tap_1_3, tap_2_3, tap_3_3, tap_4_3, tap_5_3, delay_4, gain_4, input_0_4, input_1_4, tap_0_4, tap_1_4, tap_2_4, tap_3_4, tap_4_4, tap_5_4, delay_5, gain_5, input_0_5, input_1_5, tap_0_5, tap_1_5, tap_2_5, tap_3_5, tap_4_5, tap_5_5, gain_6, input_0_6, input_1_6, tap_0_6, tap_1_6, tap_2_6, tap_3_6, tap_4_6, tap_5_6, gain_7, input_0_7, input_1_7, tap_0_7, tap_1_7, tap_2_7, tap_3_7, tap_4_7, tap_5_7)
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
  ~synth = Synth(\faustTapiir, 
	[	in1:in1Bus.asMap,
		in2:in2Bus.asMap,
		delay_0:delay_0Var,
		gain_0:gain_0Var,
		input_0_0:input_0_0Var,
		input_1_0:input_1_0Var,
		tap_0_0:tap_0_0Var,
		tap_1_0:tap_1_0Var,
		tap_2_0:tap_2_0Var,
		tap_3_0:tap_3_0Var,
		tap_4_0:tap_4_0Var,
		tap_5_0:tap_5_0Var,
		delay_1:delay_1Var,
		gain_1:gain_1Var,
		input_0_1:input_0_1Var,
		input_1_1:input_1_1Var,
		tap_0_1:tap_0_1Var,
		tap_1_1:tap_1_1Var,
		tap_2_1:tap_2_1Var,
		tap_3_1:tap_3_1Var,
		tap_4_1:tap_4_1Var,
		tap_5_1:tap_5_1Var,
		delay_2:delay_2Var,
		gain_2:gain_2Var,
		input_0_2:input_0_2Var,
		input_1_2:input_1_2Var,
		tap_0_2:tap_0_2Var,
		tap_1_2:tap_1_2Var,
		tap_2_2:tap_2_2Var,
		tap_3_2:tap_3_2Var,
		tap_4_2:tap_4_2Var,
		tap_5_2:tap_5_2Var,
		delay_3:delay_3Var,
		gain_3:gain_3Var,
		input_0_3:input_0_3Var,
		input_1_3:input_1_3Var,
		tap_0_3:tap_0_3Var,
		tap_1_3:tap_1_3Var,
		tap_2_3:tap_2_3Var,
		tap_3_3:tap_3_3Var,
		tap_4_3:tap_4_3Var,
		tap_5_3:tap_5_3Var,
		delay_4:delay_4Var,
		gain_4:gain_4Var,
		input_0_4:input_0_4Var,
		input_1_4:input_1_4Var,
		tap_0_4:tap_0_4Var,
		tap_1_4:tap_1_4Var,
		tap_2_4:tap_2_4Var,
		tap_3_4:tap_3_4Var,
		tap_4_4:tap_4_4Var,
		tap_5_4:tap_5_4Var,
		delay_5:delay_5Var,
		gain_5:gain_5Var,
		input_0_5:input_0_5Var,
		input_1_5:input_1_5Var,
		tap_0_5:tap_0_5Var,
		tap_1_5:tap_1_5Var,
		tap_2_5:tap_2_5Var,
		tap_3_5:tap_3_5Var,
		tap_4_5:tap_4_5Var,
		tap_5_5:tap_5_5Var,
		gain_6:gain_6Var,
		input_0_6:input_0_6Var,
		input_1_6:input_1_6Var,
		tap_0_6:tap_0_6Var,
		tap_1_6:tap_1_6Var,
		tap_2_6:tap_2_6Var,
		tap_3_6:tap_3_6Var,
		tap_4_6:tap_4_6Var,
		tap_5_6:tap_5_6Var,
		gain_7:gain_7Var,
		input_0_7:input_0_7Var,
		input_1_7:input_1_7Var,
		tap_0_7:tap_0_7Var,
		tap_1_7:tap_1_7Var,
		tap_2_7:tap_2_7Var,
		tap_3_7:tap_3_7Var,
		tap_4_7:tap_4_7Var,
		tap_5_7:tap_5_7Var
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustTapiir,
      SynthDef(\faustTapiir,
        { |out=0|
          Out.ar(out, 
            FaustTapiir.ar(
              \in1.ar(0), \in2.ar(0), \delay_0.kr(0.0), \gain_0.kr(1.0), \input_0_0.kr(1.0), \input_1_0.kr(1.0), \tap_0_0.kr(0.0), \tap_1_0.kr(0.0), \tap_2_0.kr(0.0), \tap_3_0.kr(0.0), \tap_4_0.kr(0.0), \tap_5_0.kr(0.0), \delay_1.kr(0.0), \gain_1.kr(1.0), \input_0_1.kr(1.0), \input_1_1.kr(1.0), \tap_0_1.kr(0.0), \tap_1_1.kr(0.0), \tap_2_1.kr(0.0), \tap_3_1.kr(0.0), \tap_4_1.kr(0.0), \tap_5_1.kr(0.0), \delay_2.kr(0.0), \gain_2.kr(1.0), \input_0_2.kr(1.0), \input_1_2.kr(1.0), \tap_0_2.kr(0.0), \tap_1_2.kr(0.0), \tap_2_2.kr(0.0), \tap_3_2.kr(0.0), \tap_4_2.kr(0.0), \tap_5_2.kr(0.0), \delay_3.kr(0.0), \gain_3.kr(1.0), \input_0_3.kr(1.0), \input_1_3.kr(1.0), \tap_0_3.kr(0.0), \tap_1_3.kr(0.0), \tap_2_3.kr(0.0), \tap_3_3.kr(0.0), \tap_4_3.kr(0.0), \tap_5_3.kr(0.0), \delay_4.kr(0.0), \gain_4.kr(1.0), \input_0_4.kr(1.0), \input_1_4.kr(1.0), \tap_0_4.kr(0.0), \tap_1_4.kr(0.0), \tap_2_4.kr(0.0), \tap_3_4.kr(0.0), \tap_4_4.kr(0.0), \tap_5_4.kr(0.0), \delay_5.kr(0.0), \gain_5.kr(1.0), \input_0_5.kr(1.0), \input_1_5.kr(1.0), \tap_0_5.kr(0.0), \tap_1_5.kr(0.0), \tap_2_5.kr(0.0), \tap_3_5.kr(0.0), \tap_4_5.kr(0.0), \tap_5_5.kr(0.0), \gain_6.kr(1.0), \input_0_6.kr(1.0), \input_1_6.kr(1.0), \tap_0_6.kr(0.0), \tap_1_6.kr(0.0), \tap_2_6.kr(0.0), \tap_3_6.kr(0.0), \tap_4_6.kr(0.0), \tap_5_6.kr(0.0), \gain_7.kr(1.0), \input_0_7.kr(1.0), \input_1_7.kr(1.0), \tap_0_7.kr(0.0), \tap_1_7.kr(0.0), \tap_2_7.kr(0.0), \tap_3_7.kr(0.0), \tap_4_7.kr(0.0), \tap_5_7.kr(0.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \delay_0:[0.0, 5.0, 0, 0.01, 0.0].asSpec,
          \gain_0:[0.0, 1.0, 0, 0.1, 1.0].asSpec,
          \input_0_0:[0.0, 1.0, 0, 0.1, 1.0].asSpec,
          \input_1_0:[0.0, 1.0, 0, 0.1, 1.0].asSpec,
          \tap_0_0:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_1_0:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_2_0:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_3_0:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_4_0:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_5_0:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \delay_1:[0.0, 5.0, 0, 0.01, 0.0].asSpec,
          \gain_1:[0.0, 1.0, 0, 0.1, 1.0].asSpec,
          \input_0_1:[0.0, 1.0, 0, 0.1, 1.0].asSpec,
          \input_1_1:[0.0, 1.0, 0, 0.1, 1.0].asSpec,
          \tap_0_1:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_1_1:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_2_1:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_3_1:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_4_1:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_5_1:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \delay_2:[0.0, 5.0, 0, 0.01, 0.0].asSpec,
          \gain_2:[0.0, 1.0, 0, 0.1, 1.0].asSpec,
          \input_0_2:[0.0, 1.0, 0, 0.1, 1.0].asSpec,
          \input_1_2:[0.0, 1.0, 0, 0.1, 1.0].asSpec,
          \tap_0_2:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_1_2:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_2_2:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_3_2:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_4_2:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_5_2:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \delay_3:[0.0, 5.0, 0, 0.01, 0.0].asSpec,
          \gain_3:[0.0, 1.0, 0, 0.1, 1.0].asSpec,
          \input_0_3:[0.0, 1.0, 0, 0.1, 1.0].asSpec,
          \input_1_3:[0.0, 1.0, 0, 0.1, 1.0].asSpec,
          \tap_0_3:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_1_3:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_2_3:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_3_3:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_4_3:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_5_3:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \delay_4:[0.0, 5.0, 0, 0.01, 0.0].asSpec,
          \gain_4:[0.0, 1.0, 0, 0.1, 1.0].asSpec,
          \input_0_4:[0.0, 1.0, 0, 0.1, 1.0].asSpec,
          \input_1_4:[0.0, 1.0, 0, 0.1, 1.0].asSpec,
          \tap_0_4:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_1_4:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_2_4:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_3_4:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_4_4:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_5_4:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \delay_5:[0.0, 5.0, 0, 0.01, 0.0].asSpec,
          \gain_5:[0.0, 1.0, 0, 0.1, 1.0].asSpec,
          \input_0_5:[0.0, 1.0, 0, 0.1, 1.0].asSpec,
          \input_1_5:[0.0, 1.0, 0, 0.1, 1.0].asSpec,
          \tap_0_5:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_1_5:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_2_5:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_3_5:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_4_5:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_5_5:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \gain_6:[0.0, 1.0, 0, 0.1, 1.0].asSpec,
          \input_0_6:[0.0, 1.0, 0, 0.1, 1.0].asSpec,
          \input_1_6:[0.0, 1.0, 0, 0.1, 1.0].asSpec,
          \tap_0_6:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_1_6:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_2_6:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_3_6:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_4_6:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_5_6:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \gain_7:[0.0, 1.0, 0, 0.1, 1.0].asSpec,
          \input_0_7:[0.0, 1.0, 0, 0.1, 1.0].asSpec,
          \input_1_7:[0.0, 1.0, 0, 0.1, 1.0].asSpec,
          \tap_0_7:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_1_7:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_2_7:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_3_7:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_4_7:[0.0, 1.0, 0, 0.1, 0.0].asSpec,
          \tap_5_7:[0.0, 1.0, 0, 0.1, 0.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustTapiir" }
}

