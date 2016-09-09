FaustGraphicEq : MultiOutUGen
{
  *ar { | in1, amplitude(-20.0), frequency(49.0), detuning_1(-0.1), detuning_2(0.1), portamento(0.1), saw_order(2.0), pink_noise_instead(0.0), external_input_instead(0.0), bypass(0.0), vslider_0x7f97288d4a90(-10.0), vslider_0x7f97288d3040(-10.0), vslider_0x7f97288d15f0(-10.0), vslider_0x7f97288cfb90(-10.0), vslider_0x7f97288ce130(-10.0), vslider_0x7f97288cc6c0(-10.0), vslider_0x7f97288cac50(-10.0), vslider_0x7f97288c91d0(-10.0), vslider_0x7f97288c7750(-10.0), vslider_0x7f97288c58e0(-10.0), level_averaging_time(100.0), level_db_offset(50.0) |
      ^this.multiNew('audio', in1, amplitude, frequency, detuning_1, detuning_2, portamento, saw_order, pink_noise_instead, external_input_instead, bypass, vslider_0x7f97288d4a90, vslider_0x7f97288d3040, vslider_0x7f97288d15f0, vslider_0x7f97288cfb90, vslider_0x7f97288ce130, vslider_0x7f97288cc6c0, vslider_0x7f97288cac50, vslider_0x7f97288c91d0, vslider_0x7f97288c7750, vslider_0x7f97288c58e0, level_averaging_time, level_db_offset)
  }

  *kr { | in1, amplitude(-20.0), frequency(49.0), detuning_1(-0.1), detuning_2(0.1), portamento(0.1), saw_order(2.0), pink_noise_instead(0.0), external_input_instead(0.0), bypass(0.0), vslider_0x7f97288d4a90(-10.0), vslider_0x7f97288d3040(-10.0), vslider_0x7f97288d15f0(-10.0), vslider_0x7f97288cfb90(-10.0), vslider_0x7f97288ce130(-10.0), vslider_0x7f97288cc6c0(-10.0), vslider_0x7f97288cac50(-10.0), vslider_0x7f97288c91d0(-10.0), vslider_0x7f97288c7750(-10.0), vslider_0x7f97288c58e0(-10.0), level_averaging_time(100.0), level_db_offset(50.0) |
      ^this.multiNew('control', in1, amplitude, frequency, detuning_1, detuning_2, portamento, saw_order, pink_noise_instead, external_input_instead, bypass, vslider_0x7f97288d4a90, vslider_0x7f97288d3040, vslider_0x7f97288d15f0, vslider_0x7f97288cfb90, vslider_0x7f97288ce130, vslider_0x7f97288cc6c0, vslider_0x7f97288cac50, vslider_0x7f97288c91d0, vslider_0x7f97288c7750, vslider_0x7f97288c58e0, level_averaging_time, level_db_offset)
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
  ~synth = Synth(\faustGraphicEq, 
	[	in1:in1Bus.asMap,
		amplitude:amplitudeVar,
		frequency:frequencyVar,
		detuning_1:detuning_1Var,
		detuning_2:detuning_2Var,
		portamento:portamentoVar,
		saw_order:saw_orderVar,
		pink_noise_instead:pink_noise_insteadVar,
		external_input_instead:external_input_insteadVar,
		bypass:bypassVar,
		vslider_0x7f97288d4a90:vslider_0x7f97288d4a90Var,
		vslider_0x7f97288d3040:vslider_0x7f97288d3040Var,
		vslider_0x7f97288d15f0:vslider_0x7f97288d15f0Var,
		vslider_0x7f97288cfb90:vslider_0x7f97288cfb90Var,
		vslider_0x7f97288ce130:vslider_0x7f97288ce130Var,
		vslider_0x7f97288cc6c0:vslider_0x7f97288cc6c0Var,
		vslider_0x7f97288cac50:vslider_0x7f97288cac50Var,
		vslider_0x7f97288c91d0:vslider_0x7f97288c91d0Var,
		vslider_0x7f97288c7750:vslider_0x7f97288c7750Var,
		vslider_0x7f97288c58e0:vslider_0x7f97288c58e0Var,
		level_averaging_time:level_averaging_timeVar,
		level_db_offset:level_db_offsetVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustGraphicEq,
      SynthDef(\faustGraphicEq,
        { |out=0|
          Out.ar(out, 
            FaustGraphicEq.ar(
              \in1.ar(0), \amplitude.kr(-20.0), \frequency.kr(49.0), \detuning_1.kr(-0.1), \detuning_2.kr(0.1), \portamento.kr(0.1), \saw_order.kr(2.0), \pink_noise_instead.kr(0.0), \external_input_instead.kr(0.0), \bypass.kr(0.0), \vslider_0x7f97288d4a90.kr(-10.0), \vslider_0x7f97288d3040.kr(-10.0), \vslider_0x7f97288d15f0.kr(-10.0), \vslider_0x7f97288cfb90.kr(-10.0), \vslider_0x7f97288ce130.kr(-10.0), \vslider_0x7f97288cc6c0.kr(-10.0), \vslider_0x7f97288cac50.kr(-10.0), \vslider_0x7f97288c91d0.kr(-10.0), \vslider_0x7f97288c7750.kr(-10.0), \vslider_0x7f97288c58e0.kr(-10.0), \level_averaging_time.kr(100.0), \level_db_offset.kr(50.0)
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
          \bypass:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \vslider_0x7f97288d4a90:[-70.0, 10.0, 0, 0.1, -10.0].asSpec,
          \vslider_0x7f97288d3040:[-70.0, 10.0, 0, 0.1, -10.0].asSpec,
          \vslider_0x7f97288d15f0:[-70.0, 10.0, 0, 0.1, -10.0].asSpec,
          \vslider_0x7f97288cfb90:[-70.0, 10.0, 0, 0.1, -10.0].asSpec,
          \vslider_0x7f97288ce130:[-70.0, 10.0, 0, 0.1, -10.0].asSpec,
          \vslider_0x7f97288cc6c0:[-70.0, 10.0, 0, 0.1, -10.0].asSpec,
          \vslider_0x7f97288cac50:[-70.0, 10.0, 0, 0.1, -10.0].asSpec,
          \vslider_0x7f97288c91d0:[-70.0, 10.0, 0, 0.1, -10.0].asSpec,
          \vslider_0x7f97288c7750:[-70.0, 10.0, 0, 0.1, -10.0].asSpec,
          \vslider_0x7f97288c58e0:[-70.0, 10.0, 0, 0.1, -10.0].asSpec,
          \level_averaging_time:[1.0, 10000.0, 0, 1.0, 100.0].asSpec,
          \level_db_offset:[0.0, 100.0, 0, 1.0, 50.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustGraphicEq" }
}

