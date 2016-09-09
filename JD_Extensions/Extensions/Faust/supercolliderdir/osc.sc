FaustOsc : UGen
{
  *ar { | freq(1000.0), volume(0.0) |
      ^this.multiNew('audio', freq, volume)
  }

  *kr { | freq(1000.0), volume(0.0) |
      ^this.multiNew('control', freq, volume)
  } 
  
/*
  SynthDef - Typical usage in SuperCollider:

  ~synth = Synth(\faustOsc, 
	[	freq:freqVar,
		volume:volumeVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustOsc,
      SynthDef(\faustOsc,
        { |out=0|
          Out.ar(out, 
            FaustOsc.ar(
              \freq.kr(1000.0), \volume.kr(0.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \freq:[20.0, 24000.0, 0, 1.0, 1000.0].asSpec,
          \volume:[-96.0, 0.0, 0, 0.1, 0.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustOsc" }
}

