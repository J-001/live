FaustZitaRev1 : MultiOutUGen
{
  *ar { | in1, in2, in_delay(60.0), lf_x(200.0), low_rt60(3.0), mid_rt60(2.0), hf_damping(6000.0), eq1_freq(315.0), eq1_level(0.0), eq2_freq(1500.0), eq2_level(0.0), dry_wet_mix(0.0), level(-20.0) |
      ^this.multiNew('audio', in1, in2, in_delay, lf_x, low_rt60, mid_rt60, hf_damping, eq1_freq, eq1_level, eq2_freq, eq2_level, dry_wet_mix, level)
  }

  *kr { | in1, in2, in_delay(60.0), lf_x(200.0), low_rt60(3.0), mid_rt60(2.0), hf_damping(6000.0), eq1_freq(315.0), eq1_level(0.0), eq2_freq(1500.0), eq2_level(0.0), dry_wet_mix(0.0), level(-20.0) |
      ^this.multiNew('control', in1, in2, in_delay, lf_x, low_rt60, mid_rt60, hf_damping, eq1_freq, eq1_level, eq2_freq, eq2_level, dry_wet_mix, level)
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
  ~synth = Synth(\faustZitaRev1, 
	[	in1:in1Bus.asMap,
		in2:in2Bus.asMap,
		in_delay:in_delayVar,
		lf_x:lf_xVar,
		low_rt60:low_rt60Var,
		mid_rt60:mid_rt60Var,
		hf_damping:hf_dampingVar,
		eq1_freq:eq1_freqVar,
		eq1_level:eq1_levelVar,
		eq2_freq:eq2_freqVar,
		eq2_level:eq2_levelVar,
		dry_wet_mix:dry_wet_mixVar,
		level:levelVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustZitaRev1,
      SynthDef(\faustZitaRev1,
        { |out=0|
          Out.ar(out, 
            FaustZitaRev1.ar(
              \in1.ar(0), \in2.ar(0), \in_delay.kr(60.0), \lf_x.kr(200.0), \low_rt60.kr(3.0), \mid_rt60.kr(2.0), \hf_damping.kr(6000.0), \eq1_freq.kr(315.0), \eq1_level.kr(0.0), \eq2_freq.kr(1500.0), \eq2_level.kr(0.0), \dry_wet_mix.kr(0.0), \level.kr(-20.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \in_delay:[20.0, 100.0, 0, 1.0, 60.0].asSpec,
          \lf_x:[50.0, 1000.0, 0, 1.0, 200.0].asSpec,
          \low_rt60:[1.0, 8.0, 0, 0.1, 3.0].asSpec,
          \mid_rt60:[1.0, 8.0, 0, 0.1, 2.0].asSpec,
          \hf_damping:[1500.0, 23520.0, 0, 1.0, 6000.0].asSpec,
          \eq1_freq:[40.0, 2500.0, 0, 1.0, 315.0].asSpec,
          \eq1_level:[-15.0, 15.0, 0, 0.1, 0.0].asSpec,
          \eq2_freq:[160.0, 10000.0, 0, 1.0, 1500.0].asSpec,
          \eq2_level:[-15.0, 15.0, 0, 0.1, 0.0].asSpec,
          \dry_wet_mix:[-1.0, 1.0, 0, 0.01, 0.0].asSpec,
          \level:[-70.0, 40.0, 0, 0.1, -20.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustZitaRev1" }
}

