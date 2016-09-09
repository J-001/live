FaustSpectrumLevel : MultiOutUGen
{
  *ar { | in1, level_averaging_time(100.0), level_db_offset(50.0) |
      ^this.multiNew('audio', in1, level_averaging_time, level_db_offset)
  }

  *kr { | in1, level_averaging_time(100.0), level_db_offset(50.0) |
      ^this.multiNew('control', in1, level_averaging_time, level_db_offset)
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
  ~synth = Synth(\faustSpectrumLevel, 
	[	in1:in1Bus.asMap,
		level_averaging_time:level_averaging_timeVar,
		level_db_offset:level_db_offsetVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustSpectrumLevel,
      SynthDef(\faustSpectrumLevel,
        { |out=0|
          Out.ar(out, 
            FaustSpectrumLevel.ar(
              \in1.ar(0), \level_averaging_time.kr(100.0), \level_db_offset.kr(50.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \level_averaging_time:[1.0, 10000.0, 0, 1.0, 100.0].asSpec,
          \level_db_offset:[0.0, 100.0, 0, 1.0, 50.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustSpectrumLevel" }
}

