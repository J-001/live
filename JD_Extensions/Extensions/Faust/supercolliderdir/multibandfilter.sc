FaustMultibandfilter : UGen
{
  *ar { | in1, q_factor_0(50.0), freq_0(1000.0), gain_0(0.0), q_factor_1(50.0), freq_1(2000.0), gain_1(0.0), q_factor_2(50.0), freq_2(3000.0), gain_2(0.0), q_factor_3(50.0), freq_3(4000.0), gain_3(0.0), q_factor_4(50.0), freq_4(5000.0), gain_4(0.0), q_factor_5(50.0), freq_5(6000.0), gain_5(0.0), q_factor_6(50.0), freq_6(7000.0), gain_6(0.0), q_factor_7(50.0), freq_7(8000.0), gain_7(0.0), q_factor_8(50.0), freq_8(9000.0), gain_8(0.0), q_factor_9(50.0), freq_9(10000.0), gain_9(0.0) |
      ^this.multiNew('audio', in1, q_factor_0, freq_0, gain_0, q_factor_1, freq_1, gain_1, q_factor_2, freq_2, gain_2, q_factor_3, freq_3, gain_3, q_factor_4, freq_4, gain_4, q_factor_5, freq_5, gain_5, q_factor_6, freq_6, gain_6, q_factor_7, freq_7, gain_7, q_factor_8, freq_8, gain_8, q_factor_9, freq_9, gain_9)
  }

  *kr { | in1, q_factor_0(50.0), freq_0(1000.0), gain_0(0.0), q_factor_1(50.0), freq_1(2000.0), gain_1(0.0), q_factor_2(50.0), freq_2(3000.0), gain_2(0.0), q_factor_3(50.0), freq_3(4000.0), gain_3(0.0), q_factor_4(50.0), freq_4(5000.0), gain_4(0.0), q_factor_5(50.0), freq_5(6000.0), gain_5(0.0), q_factor_6(50.0), freq_6(7000.0), gain_6(0.0), q_factor_7(50.0), freq_7(8000.0), gain_7(0.0), q_factor_8(50.0), freq_8(9000.0), gain_8(0.0), q_factor_9(50.0), freq_9(10000.0), gain_9(0.0) |
      ^this.multiNew('control', in1, q_factor_0, freq_0, gain_0, q_factor_1, freq_1, gain_1, q_factor_2, freq_2, gain_2, q_factor_3, freq_3, gain_3, q_factor_4, freq_4, gain_4, q_factor_5, freq_5, gain_5, q_factor_6, freq_6, gain_6, q_factor_7, freq_7, gain_7, q_factor_8, freq_8, gain_8, q_factor_9, freq_9, gain_9)
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
  ~synth = Synth(\faustMultibandfilter, 
	[	in1:in1Bus.asMap,
		q_factor_0:q_factor_0Var,
		freq_0:freq_0Var,
		gain_0:gain_0Var,
		q_factor_1:q_factor_1Var,
		freq_1:freq_1Var,
		gain_1:gain_1Var,
		q_factor_2:q_factor_2Var,
		freq_2:freq_2Var,
		gain_2:gain_2Var,
		q_factor_3:q_factor_3Var,
		freq_3:freq_3Var,
		gain_3:gain_3Var,
		q_factor_4:q_factor_4Var,
		freq_4:freq_4Var,
		gain_4:gain_4Var,
		q_factor_5:q_factor_5Var,
		freq_5:freq_5Var,
		gain_5:gain_5Var,
		q_factor_6:q_factor_6Var,
		freq_6:freq_6Var,
		gain_6:gain_6Var,
		q_factor_7:q_factor_7Var,
		freq_7:freq_7Var,
		gain_7:gain_7Var,
		q_factor_8:q_factor_8Var,
		freq_8:freq_8Var,
		gain_8:gain_8Var,
		q_factor_9:q_factor_9Var,
		freq_9:freq_9Var,
		gain_9:gain_9Var
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustMultibandfilter,
      SynthDef(\faustMultibandfilter,
        { |out=0|
          Out.ar(out, 
            FaustMultibandfilter.ar(
              \in1.ar(0), \q_factor_0.kr(50.0), \freq_0.kr(1000.0), \gain_0.kr(0.0), \q_factor_1.kr(50.0), \freq_1.kr(2000.0), \gain_1.kr(0.0), \q_factor_2.kr(50.0), \freq_2.kr(3000.0), \gain_2.kr(0.0), \q_factor_3.kr(50.0), \freq_3.kr(4000.0), \gain_3.kr(0.0), \q_factor_4.kr(50.0), \freq_4.kr(5000.0), \gain_4.kr(0.0), \q_factor_5.kr(50.0), \freq_5.kr(6000.0), \gain_5.kr(0.0), \q_factor_6.kr(50.0), \freq_6.kr(7000.0), \gain_6.kr(0.0), \q_factor_7.kr(50.0), \freq_7.kr(8000.0), \gain_7.kr(0.0), \q_factor_8.kr(50.0), \freq_8.kr(9000.0), \gain_8.kr(0.0), \q_factor_9.kr(50.0), \freq_9.kr(10000.0), \gain_9.kr(0.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \q_factor_0:[0.1, 100.0, 0, 0.1, 50.0].asSpec,
          \freq_0:[20.0, 20000.0, 0, 1.0, 1000.0].asSpec,
          \gain_0:[-50.0, 50.0, 0, 0.1, 0.0].asSpec,
          \q_factor_1:[0.1, 100.0, 0, 0.1, 50.0].asSpec,
          \freq_1:[20.0, 20000.0, 0, 1.0, 2000.0].asSpec,
          \gain_1:[-50.0, 50.0, 0, 0.1, 0.0].asSpec,
          \q_factor_2:[0.1, 100.0, 0, 0.1, 50.0].asSpec,
          \freq_2:[20.0, 20000.0, 0, 1.0, 3000.0].asSpec,
          \gain_2:[-50.0, 50.0, 0, 0.1, 0.0].asSpec,
          \q_factor_3:[0.1, 100.0, 0, 0.1, 50.0].asSpec,
          \freq_3:[20.0, 20000.0, 0, 1.0, 4000.0].asSpec,
          \gain_3:[-50.0, 50.0, 0, 0.1, 0.0].asSpec,
          \q_factor_4:[0.1, 100.0, 0, 0.1, 50.0].asSpec,
          \freq_4:[20.0, 20000.0, 0, 1.0, 5000.0].asSpec,
          \gain_4:[-50.0, 50.0, 0, 0.1, 0.0].asSpec,
          \q_factor_5:[0.1, 100.0, 0, 0.1, 50.0].asSpec,
          \freq_5:[20.0, 20000.0, 0, 1.0, 6000.0].asSpec,
          \gain_5:[-50.0, 50.0, 0, 0.1, 0.0].asSpec,
          \q_factor_6:[0.1, 100.0, 0, 0.1, 50.0].asSpec,
          \freq_6:[20.0, 20000.0, 0, 1.0, 7000.0].asSpec,
          \gain_6:[-50.0, 50.0, 0, 0.1, 0.0].asSpec,
          \q_factor_7:[0.1, 100.0, 0, 0.1, 50.0].asSpec,
          \freq_7:[20.0, 20000.0, 0, 1.0, 8000.0].asSpec,
          \gain_7:[-50.0, 50.0, 0, 0.1, 0.0].asSpec,
          \q_factor_8:[0.1, 100.0, 0, 0.1, 50.0].asSpec,
          \freq_8:[20.0, 20000.0, 0, 1.0, 9000.0].asSpec,
          \gain_8:[-50.0, 50.0, 0, 0.1, 0.0].asSpec,
          \q_factor_9:[0.1, 100.0, 0, 0.1, 50.0].asSpec,
          \freq_9:[20.0, 20000.0, 0, 1.0, 10000.0].asSpec,
          \gain_9:[-50.0, 50.0, 0, 0.1, 0.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustMultibandfilter" }
}

