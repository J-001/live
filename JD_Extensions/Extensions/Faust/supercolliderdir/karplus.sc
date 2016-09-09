FaustKarplus : UGen
{
  *ar { | excitation(128.0), play(0.0), level(0.5), attenuation(0.1), duration(128.0) |
      ^this.multiNew('audio', excitation, play, level, attenuation, duration)
  }

  *kr { | excitation(128.0), play(0.0), level(0.5), attenuation(0.1), duration(128.0) |
      ^this.multiNew('control', excitation, play, level, attenuation, duration)
  } 
  
/*
  SynthDef - Typical usage in SuperCollider:

  ~synth = Synth(\faustKarplus, 
	[	excitation:excitationVar,
		play:playVar,
		level:levelVar,
		attenuation:attenuationVar,
		duration:durationVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustKarplus,
      SynthDef(\faustKarplus,
        { |out=0|
          Out.ar(out, 
            FaustKarplus.ar(
              \excitation.kr(128.0), \play.kr(0.0), \level.kr(0.5), \attenuation.kr(0.1), \duration.kr(128.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \excitation:[2.0, 512.0, 0, 1.0, 128.0].asSpec,
          \play:[0.0, 1, 0, 1, 0.0].asSpec,
          \level:[0.0, 1.0, 0, 0.01, 0.5].asSpec,
          \attenuation:[0.0, 1.0, 0, 0.01, 0.1].asSpec,
          \duration:[2.0, 512.0, 0, 1.0, 128.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustKarplus" }
}

