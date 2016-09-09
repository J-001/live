FaustCubicDistortion : MultiOutUGen
{
  *ar { | amplitude(-20.0), frequency(49.0), portamento(0.1), bypass(0.0), drive(0.0), offset(0.0), level_averaging_time(100.0), level_db_offset(50.0) |
      ^this.multiNew('audio', amplitude, frequency, portamento, bypass, drive, offset, level_averaging_time, level_db_offset)
  }

  *kr { | amplitude(-20.0), frequency(49.0), portamento(0.1), bypass(0.0), drive(0.0), offset(0.0), level_averaging_time(100.0), level_db_offset(50.0) |
      ^this.multiNew('control', amplitude, frequency, portamento, bypass, drive, offset, level_averaging_time, level_db_offset)
  } 

  init { | ... theInputs |
      inputs = theInputs
      ^this.initOutputs(2, rate)
  }
  
/*
  SynthDef - Typical usage in SuperCollider:

  ~synth = Synth(\faustCubicDistortion, 
	[	amplitude:amplitudeVar,
		frequency:frequencyVar,
		portamento:portamentoVar,
		bypass:bypassVar,
		drive:driveVar,
		offset:offsetVar,
		level_averaging_time:level_averaging_timeVar,
		level_db_offset:level_db_offsetVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustCubicDistortion,
      SynthDef(\faustCubicDistortion,
        { |out=0|
          Out.ar(out, 
            FaustCubicDistortion.ar(
              \amplitude.kr(-20.0), \frequency.kr(49.0), \portamento.kr(0.1), \bypass.kr(0.0), \drive.kr(0.0), \offset.kr(0.0), \level_averaging_time.kr(100.0), \level_db_offset.kr(50.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \amplitude:[-120.0, 10.0, 0, 0.1, -20.0].asSpec,
          \frequency:[1.0, 88.0, 0, 0.01, 49.0].asSpec,
          \portamento:[0.001, 10.0, 0, 0.001, 0.1].asSpec,
          \bypass:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \drive:[0.0, 1.0, 0, 0.01, 0.0].asSpec,
          \offset:[0.0, 1.0, 0, 0.01, 0.0].asSpec,
          \level_averaging_time:[1.0, 10000.0, 0, 1.0, 100.0].asSpec,
          \level_db_offset:[0.0, 100.0, 0, 1.0, 50.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustCubicDistortion" }
}

