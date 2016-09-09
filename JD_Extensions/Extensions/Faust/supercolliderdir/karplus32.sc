FaustKarplus32 : MultiOutUGen
{
  *ar { | excitation(128.0), play(0.0), level(0.5), output_volume(0.5), attenuation(0.1), detune(32.0), duration(128.0), polyphony(1.0) |
      ^this.multiNew('audio', excitation, play, level, output_volume, attenuation, detune, duration, polyphony)
  }

  *kr { | excitation(128.0), play(0.0), level(0.5), output_volume(0.5), attenuation(0.1), detune(32.0), duration(128.0), polyphony(1.0) |
      ^this.multiNew('control', excitation, play, level, output_volume, attenuation, detune, duration, polyphony)
  } 

  init { | ... theInputs |
      inputs = theInputs
      ^this.initOutputs(2, rate)
  }
  
/*
  SynthDef - Typical usage in SuperCollider:

  ~synth = Synth(\faustKarplus32, 
	[	excitation:excitationVar,
		play:playVar,
		level:levelVar,
		output_volume:output_volumeVar,
		attenuation:attenuationVar,
		detune:detuneVar,
		duration:durationVar,
		polyphony:polyphonyVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustKarplus32,
      SynthDef(\faustKarplus32,
        { |out=0|
          Out.ar(out, 
            FaustKarplus32.ar(
              \excitation.kr(128.0), \play.kr(0.0), \level.kr(0.5), \output_volume.kr(0.5), \attenuation.kr(0.1), \detune.kr(32.0), \duration.kr(128.0), \polyphony.kr(1.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \excitation:[2.0, 512.0, 0, 1.0, 128.0].asSpec,
          \play:[0.0, 1, 0, 1, 0.0].asSpec,
          \level:[0.0, 1.0, 0, 0.1, 0.5].asSpec,
          \output_volume:[0.0, 1.0, 0, 0.1, 0.5].asSpec,
          \attenuation:[0.0, 1.0, 0, 0.01, 0.1].asSpec,
          \detune:[0.0, 512.0, 0, 1.0, 32.0].asSpec,
          \duration:[2.0, 512.0, 0, 1.0, 128.0].asSpec,
          \polyphony:[0.0, 32.0, 0, 1.0, 1.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustKarplus32" }
}

