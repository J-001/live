FaustReverbDesigner : MultiOutUGen
{
  *ar { | in1, in2, band_0_upper_edge_in_hz(500.0), band_1_upper_edge_in_hz(1000.0), band_2_upper_edge_in_hz(2000.0), band_3_upper_edge_in_hz(4000.0), vslider_0(8.4), vslider_1(6.5), vslider_2(5.0), vslider_3(3.8), vslider_4(2.7), min_acoustic_ray_length(46.0), max_acoustic_ray_length(63.0), mute_ext_inputs(0.0), pink_noise(0.0), left(0.0), center(0.0), right(0.0), quench(0.0), output_level(-40.0) |
      ^this.multiNew('audio', in1, in2, band_0_upper_edge_in_hz, band_1_upper_edge_in_hz, band_2_upper_edge_in_hz, band_3_upper_edge_in_hz, vslider_0, vslider_1, vslider_2, vslider_3, vslider_4, min_acoustic_ray_length, max_acoustic_ray_length, mute_ext_inputs, pink_noise, left, center, right, quench, output_level)
  }

  *kr { | in1, in2, band_0_upper_edge_in_hz(500.0), band_1_upper_edge_in_hz(1000.0), band_2_upper_edge_in_hz(2000.0), band_3_upper_edge_in_hz(4000.0), vslider_0(8.4), vslider_1(6.5), vslider_2(5.0), vslider_3(3.8), vslider_4(2.7), min_acoustic_ray_length(46.0), max_acoustic_ray_length(63.0), mute_ext_inputs(0.0), pink_noise(0.0), left(0.0), center(0.0), right(0.0), quench(0.0), output_level(-40.0) |
      ^this.multiNew('control', in1, in2, band_0_upper_edge_in_hz, band_1_upper_edge_in_hz, band_2_upper_edge_in_hz, band_3_upper_edge_in_hz, vslider_0, vslider_1, vslider_2, vslider_3, vslider_4, min_acoustic_ray_length, max_acoustic_ray_length, mute_ext_inputs, pink_noise, left, center, right, quench, output_level)
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
  ~synth = Synth(\faustReverbDesigner, 
	[	in1:in1Bus.asMap,
		in2:in2Bus.asMap,
		band_0_upper_edge_in_hz:band_0_upper_edge_in_hzVar,
		band_1_upper_edge_in_hz:band_1_upper_edge_in_hzVar,
		band_2_upper_edge_in_hz:band_2_upper_edge_in_hzVar,
		band_3_upper_edge_in_hz:band_3_upper_edge_in_hzVar,
		vslider_0:vslider_0Var,
		vslider_1:vslider_1Var,
		vslider_2:vslider_2Var,
		vslider_3:vslider_3Var,
		vslider_4:vslider_4Var,
		min_acoustic_ray_length:min_acoustic_ray_lengthVar,
		max_acoustic_ray_length:max_acoustic_ray_lengthVar,
		mute_ext_inputs:mute_ext_inputsVar,
		pink_noise:pink_noiseVar,
		left:leftVar,
		center:centerVar,
		right:rightVar,
		quench:quenchVar,
		output_level:output_levelVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustReverbDesigner,
      SynthDef(\faustReverbDesigner,
        { |out=0|
          Out.ar(out, 
            FaustReverbDesigner.ar(
              \in1.ar(0), \in2.ar(0), \band_0_upper_edge_in_hz.kr(500.0), \band_1_upper_edge_in_hz.kr(1000.0), \band_2_upper_edge_in_hz.kr(2000.0), \band_3_upper_edge_in_hz.kr(4000.0), \vslider_0.kr(8.4), \vslider_1.kr(6.5), \vslider_2.kr(5.0), \vslider_3.kr(3.8), \vslider_4.kr(2.7), \min_acoustic_ray_length.kr(46.0), \max_acoustic_ray_length.kr(63.0), \mute_ext_inputs.kr(0.0), \pink_noise.kr(0.0), \left.kr(0.0), \center.kr(0.0), \right.kr(0.0), \quench.kr(0.0), \output_level.kr(-40.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \band_0_upper_edge_in_hz:[100.0, 10000.0, 0, 1.0, 500.0].asSpec,
          \band_1_upper_edge_in_hz:[100.0, 10000.0, 0, 1.0, 1000.0].asSpec,
          \band_2_upper_edge_in_hz:[100.0, 10000.0, 0, 1.0, 2000.0].asSpec,
          \band_3_upper_edge_in_hz:[100.0, 10000.0, 0, 1.0, 4000.0].asSpec,
          \vslider_0:[0.1, 100.0, 0, 0.1, 8.4].asSpec,
          \vslider_1:[0.1, 100.0, 0, 0.1, 6.5].asSpec,
          \vslider_2:[0.1, 100.0, 0, 0.1, 5.0].asSpec,
          \vslider_3:[0.1, 100.0, 0, 0.1, 3.8].asSpec,
          \vslider_4:[0.1, 100.0, 0, 0.1, 2.7].asSpec,
          \min_acoustic_ray_length:[0.1, 63.0, 0, 0.1, 46.0].asSpec,
          \max_acoustic_ray_length:[0.1, 63.0, 0, 0.1, 63.0].asSpec,
          \mute_ext_inputs:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \pink_noise:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \left:[0.0, 1, 0, 1, 0.0].asSpec,
          \center:[0.0, 1, 0, 1, 0.0].asSpec,
          \right:[0.0, 1, 0, 1, 0.0].asSpec,
          \quench:[0.0, 1, 0, 1, 0.0].asSpec,
          \output_level:[-70.0, 20.0, 0, 0.1, -40.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustReverbDesigner" }
}

