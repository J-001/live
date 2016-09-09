FaustPhaserFlanger : MultiOutUGen
{
  *ar { | in1, amplitude(-20.0), frequency(49.0), detuning_1(-0.1), detuning_2(0.1), portamento(0.1), saw_order(2.0), pink_noise_instead(0.0), external_input_instead(0.0), bypass_0(0.0), invert_flange_sum_0(0.0), speed_0(0.5), depth_0(1.0), feedback_0(0.0), flange_delay_0(10.0), delay_offset_0(1.0), flanger_output_level_0(0.0), bypass_1(0.0), invert_internal_phaser_sum_1(0.0), vibrato_mode_1(0.0), speed_1(0.5), notch_depth_1(1.0), feedback_gain_1(0.0), notch_width_1(1000.0), min_notch1_freq_1(100.0), max_notch1_freq_1(800.0), notch_freq_ratio_notchfreq_no_1(1.5), phaser_output_level_1(0.0), level_averaging_time_1(100.0), level_db_offset_1(50.0) |
      ^this.multiNew('audio', in1, amplitude, frequency, detuning_1, detuning_2, portamento, saw_order, pink_noise_instead, external_input_instead, bypass_0, invert_flange_sum_0, speed_0, depth_0, feedback_0, flange_delay_0, delay_offset_0, flanger_output_level_0, bypass_1, invert_internal_phaser_sum_1, vibrato_mode_1, speed_1, notch_depth_1, feedback_gain_1, notch_width_1, min_notch1_freq_1, max_notch1_freq_1, notch_freq_ratio_notchfreq_no_1, phaser_output_level_1, level_averaging_time_1, level_db_offset_1)
  }

  *kr { | in1, amplitude(-20.0), frequency(49.0), detuning_1(-0.1), detuning_2(0.1), portamento(0.1), saw_order(2.0), pink_noise_instead(0.0), external_input_instead(0.0), bypass_0(0.0), invert_flange_sum_0(0.0), speed_0(0.5), depth_0(1.0), feedback_0(0.0), flange_delay_0(10.0), delay_offset_0(1.0), flanger_output_level_0(0.0), bypass_1(0.0), invert_internal_phaser_sum_1(0.0), vibrato_mode_1(0.0), speed_1(0.5), notch_depth_1(1.0), feedback_gain_1(0.0), notch_width_1(1000.0), min_notch1_freq_1(100.0), max_notch1_freq_1(800.0), notch_freq_ratio_notchfreq_no_1(1.5), phaser_output_level_1(0.0), level_averaging_time_1(100.0), level_db_offset_1(50.0) |
      ^this.multiNew('control', in1, amplitude, frequency, detuning_1, detuning_2, portamento, saw_order, pink_noise_instead, external_input_instead, bypass_0, invert_flange_sum_0, speed_0, depth_0, feedback_0, flange_delay_0, delay_offset_0, flanger_output_level_0, bypass_1, invert_internal_phaser_sum_1, vibrato_mode_1, speed_1, notch_depth_1, feedback_gain_1, notch_width_1, min_notch1_freq_1, max_notch1_freq_1, notch_freq_ratio_notchfreq_no_1, phaser_output_level_1, level_averaging_time_1, level_db_offset_1)
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
  ~synth = Synth(\faustPhaserFlanger, 
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
		invert_flange_sum_0:invert_flange_sum_0Var,
		speed_0:speed_0Var,
		depth_0:depth_0Var,
		feedback_0:feedback_0Var,
		flange_delay_0:flange_delay_0Var,
		delay_offset_0:delay_offset_0Var,
		flanger_output_level_0:flanger_output_level_0Var,
		bypass_1:bypass_1Var,
		invert_internal_phaser_sum_1:invert_internal_phaser_sum_1Var,
		vibrato_mode_1:vibrato_mode_1Var,
		speed_1:speed_1Var,
		notch_depth_1:notch_depth_1Var,
		feedback_gain_1:feedback_gain_1Var,
		notch_width_1:notch_width_1Var,
		min_notch1_freq_1:min_notch1_freq_1Var,
		max_notch1_freq_1:max_notch1_freq_1Var,
		notch_freq_ratio_notchfreq_no_1:notch_freq_ratio_notchfreq_no_1Var,
		phaser_output_level_1:phaser_output_level_1Var,
		level_averaging_time_1:level_averaging_time_1Var,
		level_db_offset_1:level_db_offset_1Var
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustPhaserFlanger,
      SynthDef(\faustPhaserFlanger,
        { |out=0|
          Out.ar(out, 
            FaustPhaserFlanger.ar(
              \in1.ar(0), \amplitude.kr(-20.0), \frequency.kr(49.0), \detuning_1.kr(-0.1), \detuning_2.kr(0.1), \portamento.kr(0.1), \saw_order.kr(2.0), \pink_noise_instead.kr(0.0), \external_input_instead.kr(0.0), \bypass_0.kr(0.0), \invert_flange_sum_0.kr(0.0), \speed_0.kr(0.5), \depth_0.kr(1.0), \feedback_0.kr(0.0), \flange_delay_0.kr(10.0), \delay_offset_0.kr(1.0), \flanger_output_level_0.kr(0.0), \bypass_1.kr(0.0), \invert_internal_phaser_sum_1.kr(0.0), \vibrato_mode_1.kr(0.0), \speed_1.kr(0.5), \notch_depth_1.kr(1.0), \feedback_gain_1.kr(0.0), \notch_width_1.kr(1000.0), \min_notch1_freq_1.kr(100.0), \max_notch1_freq_1.kr(800.0), \notch_freq_ratio_notchfreq_no_1.kr(1.5), \phaser_output_level_1.kr(0.0), \level_averaging_time_1.kr(100.0), \level_db_offset_1.kr(50.0)
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
          \invert_flange_sum_0:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \speed_0:[0.0, 10.0, 0, 0.01, 0.5].asSpec,
          \depth_0:[0.0, 1.0, 0, 0.001, 1.0].asSpec,
          \feedback_0:[-0.999, 0.999, 0, 0.001, 0.0].asSpec,
          \flange_delay_0:[0.0, 20.0, 0, 0.001, 10.0].asSpec,
          \delay_offset_0:[0.0, 20.0, 0, 0.001, 1.0].asSpec,
          \flanger_output_level_0:[-60.0, 10.0, 0, 0.1, 0.0].asSpec,
          \bypass_1:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \invert_internal_phaser_sum_1:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \vibrato_mode_1:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \speed_1:[0.0, 10.0, 0, 0.001, 0.5].asSpec,
          \notch_depth_1:[0.0, 1.0, 0, 0.001, 1.0].asSpec,
          \feedback_gain_1:[-0.999, 0.999, 0, 0.001, 0.0].asSpec,
          \notch_width_1:[10.0, 5000.0, 0, 1.0, 1000.0].asSpec,
          \min_notch1_freq_1:[20.0, 5000.0, 0, 1.0, 100.0].asSpec,
          \max_notch1_freq_1:[20.0, 10000.0, 0, 1.0, 800.0].asSpec,
          \notch_freq_ratio_notchfreq_no_1:[1.1, 4.0, 0, 0.001, 1.5].asSpec,
          \phaser_output_level_1:[-60.0, 10.0, 0, 0.1, 0.0].asSpec,
          \level_averaging_time_1:[1.0, 10000.0, 0, 1.0, 100.0].asSpec,
          \level_db_offset_1:[0.0, 100.0, 0, 1.0, 50.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustPhaserFlanger" }
}

