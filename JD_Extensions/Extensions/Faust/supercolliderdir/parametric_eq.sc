FaustParametricEq : MultiOutUGen
{
  *ar { | in1, amplitude(-20.0), frequency(49.0), detuning_1(-0.1), detuning_2(0.1), portamento(0.1), saw_order(2.0), pink_noise_instead(0.0), external_input_instead(0.0), low_boost_cut(0.0), transition_frequency_0(200.0), peak_boost_cut_0(0.0), peak_frequency_0(49.0), peak_q_0(40.0), high_boost_cut_0(0.0), transition_frequency_1(8000.0), level_averaging_time_1(100.0), level_db_offset_1(50.0) |
      ^this.multiNew('audio', in1, amplitude, frequency, detuning_1, detuning_2, portamento, saw_order, pink_noise_instead, external_input_instead, low_boost_cut, transition_frequency_0, peak_boost_cut_0, peak_frequency_0, peak_q_0, high_boost_cut_0, transition_frequency_1, level_averaging_time_1, level_db_offset_1)
  }

  *kr { | in1, amplitude(-20.0), frequency(49.0), detuning_1(-0.1), detuning_2(0.1), portamento(0.1), saw_order(2.0), pink_noise_instead(0.0), external_input_instead(0.0), low_boost_cut(0.0), transition_frequency_0(200.0), peak_boost_cut_0(0.0), peak_frequency_0(49.0), peak_q_0(40.0), high_boost_cut_0(0.0), transition_frequency_1(8000.0), level_averaging_time_1(100.0), level_db_offset_1(50.0) |
      ^this.multiNew('control', in1, amplitude, frequency, detuning_1, detuning_2, portamento, saw_order, pink_noise_instead, external_input_instead, low_boost_cut, transition_frequency_0, peak_boost_cut_0, peak_frequency_0, peak_q_0, high_boost_cut_0, transition_frequency_1, level_averaging_time_1, level_db_offset_1)
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
  ~synth = Synth(\faustParametricEq, 
	[	in1:in1Bus.asMap,
		amplitude:amplitudeVar,
		frequency:frequencyVar,
		detuning_1:detuning_1Var,
		detuning_2:detuning_2Var,
		portamento:portamentoVar,
		saw_order:saw_orderVar,
		pink_noise_instead:pink_noise_insteadVar,
		external_input_instead:external_input_insteadVar,
		low_boost_cut:low_boost_cutVar,
		transition_frequency_0:transition_frequency_0Var,
		peak_boost_cut_0:peak_boost_cut_0Var,
		peak_frequency_0:peak_frequency_0Var,
		peak_q_0:peak_q_0Var,
		high_boost_cut_0:high_boost_cut_0Var,
		transition_frequency_1:transition_frequency_1Var,
		level_averaging_time_1:level_averaging_time_1Var,
		level_db_offset_1:level_db_offset_1Var
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustParametricEq,
      SynthDef(\faustParametricEq,
        { |out=0|
          Out.ar(out, 
            FaustParametricEq.ar(
              \in1.ar(0), \amplitude.kr(-20.0), \frequency.kr(49.0), \detuning_1.kr(-0.1), \detuning_2.kr(0.1), \portamento.kr(0.1), \saw_order.kr(2.0), \pink_noise_instead.kr(0.0), \external_input_instead.kr(0.0), \low_boost_cut.kr(0.0), \transition_frequency_0.kr(200.0), \peak_boost_cut_0.kr(0.0), \peak_frequency_0.kr(49.0), \peak_q_0.kr(40.0), \high_boost_cut_0.kr(0.0), \transition_frequency_1.kr(8000.0), \level_averaging_time_1.kr(100.0), \level_db_offset_1.kr(50.0)
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
          \low_boost_cut:[-40.0, 40.0, 0, 0.1, 0.0].asSpec,
          \transition_frequency_0:[1.0, 5000.0, 0, 1.0, 200.0].asSpec,
          \peak_boost_cut_0:[-40.0, 40.0, 0, 0.1, 0.0].asSpec,
          \peak_frequency_0:[1.0, 100.0, 0, 1.0, 49.0].asSpec,
          \peak_q_0:[1.0, 1000.0, 0, 0.1, 40.0].asSpec,
          \high_boost_cut_0:[-40.0, 40.0, 0, 0.1, 0.0].asSpec,
          \transition_frequency_1:[20.0, 10000.0, 0, 1.0, 8000.0].asSpec,
          \level_averaging_time_1:[1.0, 10000.0, 0, 1.0, 100.0].asSpec,
          \level_db_offset_1:[0.0, 100.0, 0, 1.0, 50.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustParametricEq" }
}

