FaustGateCompressor : MultiOutUGen
{
  *ar { | in1, amplitude(-20.0), frequency(49.0), detuning_1(-0.1), detuning_2(0.1), portamento(0.1), saw_order(2.0), pink_noise_instead(0.0), external_input_instead(0.0), bypass_0(0.0), threshold_0(-30.0), attack_0(10.0), hold_0(200.0), release_0(100.0), bypass_1(0.0), ratio_1(5.0), threshold_1(-30.0), attack_1(50.0), release_1(500.0), makeup_gain_1(40.0), level_averaging_time_1(100.0), level_db_offset_1(50.0) |
      ^this.multiNew('audio', in1, amplitude, frequency, detuning_1, detuning_2, portamento, saw_order, pink_noise_instead, external_input_instead, bypass_0, threshold_0, attack_0, hold_0, release_0, bypass_1, ratio_1, threshold_1, attack_1, release_1, makeup_gain_1, level_averaging_time_1, level_db_offset_1)
  }

  *kr { | in1, amplitude(-20.0), frequency(49.0), detuning_1(-0.1), detuning_2(0.1), portamento(0.1), saw_order(2.0), pink_noise_instead(0.0), external_input_instead(0.0), bypass_0(0.0), threshold_0(-30.0), attack_0(10.0), hold_0(200.0), release_0(100.0), bypass_1(0.0), ratio_1(5.0), threshold_1(-30.0), attack_1(50.0), release_1(500.0), makeup_gain_1(40.0), level_averaging_time_1(100.0), level_db_offset_1(50.0) |
      ^this.multiNew('control', in1, amplitude, frequency, detuning_1, detuning_2, portamento, saw_order, pink_noise_instead, external_input_instead, bypass_0, threshold_0, attack_0, hold_0, release_0, bypass_1, ratio_1, threshold_1, attack_1, release_1, makeup_gain_1, level_averaging_time_1, level_db_offset_1)
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
  ~synth = Synth(\faustGateCompressor, 
	[	in1:in1Bus.asMap,
		amplitude:amplitudeVar,
		frequency:frequencyVar,
		detuning_1:detuning_1Var,
		detuning_2:detuning_2Var,
		portamento:portamentoVar,
		saw_order:saw_orderVar,
		pink_noise_instead:pink_noise_insteadVar,
		external_input_instead:external_input_insteadVar,
		bypass_0:bypass_0Var,
		threshold_0:threshold_0Var,
		attack_0:attack_0Var,
		hold_0:hold_0Var,
		release_0:release_0Var,
		bypass_1:bypass_1Var,
		ratio_1:ratio_1Var,
		threshold_1:threshold_1Var,
		attack_1:attack_1Var,
		release_1:release_1Var,
		makeup_gain_1:makeup_gain_1Var,
		level_averaging_time_1:level_averaging_time_1Var,
		level_db_offset_1:level_db_offset_1Var
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustGateCompressor,
      SynthDef(\faustGateCompressor,
        { |out=0|
          Out.ar(out, 
            FaustGateCompressor.ar(
              \in1.ar(0), \amplitude.kr(-20.0), \frequency.kr(49.0), \detuning_1.kr(-0.1), \detuning_2.kr(0.1), \portamento.kr(0.1), \saw_order.kr(2.0), \pink_noise_instead.kr(0.0), \external_input_instead.kr(0.0), \bypass_0.kr(0.0), \threshold_0.kr(-30.0), \attack_0.kr(10.0), \hold_0.kr(200.0), \release_0.kr(100.0), \bypass_1.kr(0.0), \ratio_1.kr(5.0), \threshold_1.kr(-30.0), \attack_1.kr(50.0), \release_1.kr(500.0), \makeup_gain_1.kr(40.0), \level_averaging_time_1.kr(100.0), \level_db_offset_1.kr(50.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \amplitude:[-120.0, 10.0, 0, 0.1, -20.0].asSpec,
          \frequency:[1.0, 88.0, 0, 0.01, 49.0].asSpec,
          \detuning_1:[-10.0, 10.0, 0, 0.01, -0.1].asSpec,
          \detuning_2:[-10.0, 10.0, 0, 0.01, 0.1].asSpec,
          \portamento:[0.001, 10.0, 0, 0.001, 0.1].asSpec,
          \saw_order:[1.0, 4.0, 0, 1.0, 2.0].asSpec,
          \pink_noise_instead:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \external_input_instead:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \bypass_0:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \threshold_0:[-120.0, 0.0, 0, 0.1, -30.0].asSpec,
          \attack_0:[10.0, 10000.0, 0, 1.0, 10.0].asSpec,
          \hold_0:[1.0, 1000.0, 0, 1.0, 200.0].asSpec,
          \release_0:[1.0, 1000.0, 0, 1.0, 100.0].asSpec,
          \bypass_1:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \ratio_1:[1.0, 20.0, 0, 0.1, 5.0].asSpec,
          \threshold_1:[-100.0, 10.0, 0, 0.1, -30.0].asSpec,
          \attack_1:[1.0, 1000.0, 0, 0.1, 50.0].asSpec,
          \release_1:[1.0, 1000.0, 0, 0.1, 500.0].asSpec,
          \makeup_gain_1:[-96.0, 96.0, 0, 0.1, 40.0].asSpec,
          \level_averaging_time_1:[1.0, 10000.0, 0, 1.0, 100.0].asSpec,
          \level_db_offset_1:[0.0, 100.0, 0, 1.0, 50.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustGateCompressor" }
}

