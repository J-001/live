FaustNoise : UGen
{
  *ar { | volume(0.5) |
      ^this.multiNew('audio', volume)
  }

  *kr { | volume(0.5) |
      ^this.multiNew('control', volume)
  } 
  
/*
  SynthDef - Typical usage in SuperCollider:

  ~synth = Synth(\faustNoise, 
	[	volume:volumeVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustNoise,
      SynthDef(\faustNoise,
        { |out=0|
          Out.ar(out, 
            FaustNoise.ar(
              \volume.kr(0.5)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \volume:[0.0, 1.0, 0, 0.1, 0.5].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustNoise" }
}

