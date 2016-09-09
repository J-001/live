FaustNoiseMD : UGen
{
  *ar { | volume(0.0) |
      ^this.multiNew('audio', volume)
  }

  *kr { | volume(0.0) |
      ^this.multiNew('control', volume)
  } 
  
/*
  SynthDef - Typical usage in SuperCollider:

  ~synth = Synth(\faustNoiseMD, 
	[	volume:volumeVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustNoiseMD,
      SynthDef(\faustNoiseMD,
        { |out=0|
          Out.ar(out, 
            FaustNoiseMD.ar(
              \volume.kr(0.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \volume:[0.0, 1.0, 0, 0.1, 0.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustNoiseMD" }
}

