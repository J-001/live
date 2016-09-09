FaustVcfWahPedals : MultiOutUGen
{
  *ar { | in1, amplitude(-20.0), frequency(49.0), detuning_1(-0.1), detuning_2(0.1), portamento(0.1), saw_order(2.0), pink_noise_instead(0.0), external_input_instead(0.0), bypass_0(0.0), wah_parameter_0(0.8), bypass_1(0.0), resonance_frequency_1(200.0), bypass_2(0.0), use_biquads_2(0.0), normalized_ladders_2(0.0), corner_frequency_2(25.0), corner_resonance_2(0.9), vcf_output_level_2(5.0), level_averaging_time_2(100.0), level_db_offset_2(50.0) |
      ^this.multiNew('audio', in1, amplitude, frequency, detuning_1, detuning_2, portamento, saw_order, pink_noise_instead, external_input_instead, bypass_0, wah_parameter_0, bypass_1, resonance_frequency_1, bypass_2, use_biquads_2, normalized_ladders_2, corner_frequency_2, corner_resonance_2, vcf_output_level_2, level_averaging_time_2, level_db_offset_2)
  }

  *kr { | in1, amplitude(-20.0), frequency(49.0), detuning_1(-0.1), detuning_2(0.1), portamento(0.1), saw_order(2.0), pink_noise_instead(0.0), external_input_instead(0.0), bypass_0(0.0), wah_parameter_0(0.8), bypass_1(0.0), resonance_frequency_1(200.0), bypass_2(0.0), use_biquads_2(0.0), normalized_ladders_2(0.0), corner_frequency_2(25.0), corner_resonance_2(0.9), vcf_output_level_2(5.0), level_averaging_time_2(100.0), level_db_offset_2(50.0) |
      ^this.multiNew('control', in1, amplitude, frequency, detuning_1, detuning_2, portamento, saw_order, pink_noise_instead, external_input_instead, bypass_0, wah_parameter_0, bypass_1, resonance_frequency_1, bypass_2, use_biquads_2, normalized_ladders_2, corner_frequency_2, corner_resonance_2, vcf_output_level_2, level_averaging_time_2, level_db_offset_2)
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
  ~synth = Synth(\faustVcfWahPedals, 
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
		wah_parameter_0:wah_parameter_0Var,
		bypass_1:bypass_1Var,
		resonance_frequency_1:resonance_frequency_1Var,
		bypass_2:bypass_2Var,
		use_biquads_2:use_biquads_2Var,
		normalized_ladders_2:normalized_ladders_2Var,
		corner_frequency_2:corner_frequency_2Var,
		corner_resonance_2:corner_resonance_2Var,
		vcf_output_level_2:vcf_output_level_2Var,
		level_averaging_time_2:level_averaging_time_2Var,
		level_db_offset_2:level_db_offset_2Var
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustVcfWahPedals,
      SynthDef(\faustVcfWahPedals,
        { |out=0|
          Out.ar(out, 
            FaustVcfWahPedals.ar(
              \in1.ar(0), \amplitude.kr(-20.0), \frequency.kr(49.0), \detuning_1.kr(-0.1), \detuning_2.kr(0.1), \portamento.kr(0.1), \saw_order.kr(2.0), \pink_noise_instead.kr(0.0), \external_input_instead.kr(0.0), \bypass_0.kr(0.0), \wah_parameter_0.kr(0.8), \bypass_1.kr(0.0), \resonance_frequency_1.kr(200.0), \bypass_2.kr(0.0), \use_biquads_2.kr(0.0), \normalized_ladders_2.kr(0.0), \corner_frequency_2.kr(25.0), \corner_resonance_2.kr(0.9), \vcf_output_level_2.kr(5.0), \level_averaging_time_2.kr(100.0), \level_db_offset_2.kr(50.0)
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
          \wah_parameter_0:[0.0, 1.0, 0, 0.01, 0.8].asSpec,
          \bypass_1:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \resonance_frequency_1:[100.0, 2000.0, 0, 1.0, 200.0].asSpec,
          \bypass_2:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \use_biquads_2:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \normalized_ladders_2:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \corner_frequency_2:[1.0, 88.0, 0, 0.01, 25.0].asSpec,
          \corner_resonance_2:[0.0, 1.0, 0, 0.01, 0.9].asSpec,
          \vcf_output_level_2:[-60.0, 20.0, 0, 0.1, 5.0].asSpec,
          \level_averaging_time_2:[1.0, 10000.0, 0, 1.0, 100.0].asSpec,
          \level_db_offset_2:[0.0, 100.0, 0, 1.0, 50.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustVcfWahPedals" }
}

