FaustVirtualAnalogOscillators : MultiOutUGen
{
  *ar { | in1, sawtooth(1.0), order_3(0.0), vslider_0x7ff19fb9c590(0.0), duty_cycle(0.5), square(0.0), triangle(0.0), pink_noise(0.0), ext_input(0.0), mix_amplitude(-20.0), frequency(49.0), detuning_1(-0.1), detuning_2(0.1), portamento(0.1), saw_order(2.0), bypass(0.0), use_biquads(0.0), normalized_ladders(0.0), corner_frequency(25.0), corner_resonance(0.9), vcf_output_level(5.0), level_averaging_time(100.0), level_db_offset(50.0) |
      ^this.multiNew('audio', in1, sawtooth, order_3, vslider_0x7ff19fb9c590, duty_cycle, square, triangle, pink_noise, ext_input, mix_amplitude, frequency, detuning_1, detuning_2, portamento, saw_order, bypass, use_biquads, normalized_ladders, corner_frequency, corner_resonance, vcf_output_level, level_averaging_time, level_db_offset)
  }

  *kr { | in1, sawtooth(1.0), order_3(0.0), vslider_0x7ff19fb9c590(0.0), duty_cycle(0.5), square(0.0), triangle(0.0), pink_noise(0.0), ext_input(0.0), mix_amplitude(-20.0), frequency(49.0), detuning_1(-0.1), detuning_2(0.1), portamento(0.1), saw_order(2.0), bypass(0.0), use_biquads(0.0), normalized_ladders(0.0), corner_frequency(25.0), corner_resonance(0.9), vcf_output_level(5.0), level_averaging_time(100.0), level_db_offset(50.0) |
      ^this.multiNew('control', in1, sawtooth, order_3, vslider_0x7ff19fb9c590, duty_cycle, square, triangle, pink_noise, ext_input, mix_amplitude, frequency, detuning_1, detuning_2, portamento, saw_order, bypass, use_biquads, normalized_ladders, corner_frequency, corner_resonance, vcf_output_level, level_averaging_time, level_db_offset)
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
  ~synth = Synth(\faustVirtualAnalogOscillators, 
	[	in1:in1Bus.asMap,
		sawtooth:sawtoothVar,
		order_3:order_3Var,
		vslider_0x7ff19fb9c590:vslider_0x7ff19fb9c590Var,
		duty_cycle:duty_cycleVar,
		square:squareVar,
		triangle:triangleVar,
		pink_noise:pink_noiseVar,
		ext_input:ext_inputVar,
		mix_amplitude:mix_amplitudeVar,
		frequency:frequencyVar,
		detuning_1:detuning_1Var,
		detuning_2:detuning_2Var,
		portamento:portamentoVar,
		saw_order:saw_orderVar,
		bypass:bypassVar,
		use_biquads:use_biquadsVar,
		normalized_ladders:normalized_laddersVar,
		corner_frequency:corner_frequencyVar,
		corner_resonance:corner_resonanceVar,
		vcf_output_level:vcf_output_levelVar,
		level_averaging_time:level_averaging_timeVar,
		level_db_offset:level_db_offsetVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustVirtualAnalogOscillators,
      SynthDef(\faustVirtualAnalogOscillators,
        { |out=0|
          Out.ar(out, 
            FaustVirtualAnalogOscillators.ar(
              \in1.ar(0), \sawtooth.kr(1.0), \order_3.kr(0.0), \vslider_0x7ff19fb9c590.kr(0.0), \duty_cycle.kr(0.5), \square.kr(0.0), \triangle.kr(0.0), \pink_noise.kr(0.0), \ext_input.kr(0.0), \mix_amplitude.kr(-20.0), \frequency.kr(49.0), \detuning_1.kr(-0.1), \detuning_2.kr(0.1), \portamento.kr(0.1), \saw_order.kr(2.0), \bypass.kr(0.0), \use_biquads.kr(0.0), \normalized_ladders.kr(0.0), \corner_frequency.kr(25.0), \corner_resonance.kr(0.9), \vcf_output_level.kr(5.0), \level_averaging_time.kr(100.0), \level_db_offset.kr(50.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \sawtooth:[0.0, 1.0, 0, 0.01, 1.0].asSpec,
          \order_3:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \vslider_0x7ff19fb9c590:[0.0, 1.0, 0, 0.01, 0.0].asSpec,
          \duty_cycle:[0.0, 1.0, 0, 0.01, 0.5].asSpec,
          \square:[0.0, 1.0, 0, 0.01, 0.0].asSpec,
          \triangle:[0.0, 1.0, 0, 0.01, 0.0].asSpec,
          \pink_noise:[0.0, 1.0, 0, 0.01, 0.0].asSpec,
          \ext_input:[0.0, 1.0, 0, 0.01, 0.0].asSpec,
          \mix_amplitude:[-120.0, 10.0, 0, 0.1, -20.0].asSpec,
          \frequency:[1.0, 88.0, 0, 0.01, 49.0].asSpec,
          \detuning_1:[-10.0, 10.0, 0, 0.01, -0.1].asSpec,
          \detuning_2:[-10.0, 10.0, 0, 0.01, 0.1].asSpec,
          \portamento:[0.001, 10.0, 0, 0.001, 0.1].asSpec,
          \saw_order:[1.0, 4.0, 0, 1.0, 2.0].asSpec,
          \bypass:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \use_biquads:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \normalized_ladders:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \corner_frequency:[1.0, 88.0, 0, 0.01, 25.0].asSpec,
          \corner_resonance:[0.0, 1.0, 0, 0.01, 0.9].asSpec,
          \vcf_output_level:[-60.0, 20.0, 0, 0.1, 5.0].asSpec,
          \level_averaging_time:[1.0, 10000.0, 0, 1.0, 100.0].asSpec,
          \level_db_offset:[0.0, 100.0, 0, 1.0, 50.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustVirtualAnalogOscillators" }
}

