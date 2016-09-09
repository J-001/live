FaustTester : MultiOutUGen
{
  *ar { | freq(1000.0), volume(-96.0), destination(0.0), pink_noise(0.0), sine_wave(0.0), white_noise(0.0) |
      ^this.multiNew('audio', freq, volume, destination, pink_noise, sine_wave, white_noise)
  }

  *kr { | freq(1000.0), volume(-96.0), destination(0.0), pink_noise(0.0), sine_wave(0.0), white_noise(0.0) |
      ^this.multiNew('control', freq, volume, destination, pink_noise, sine_wave, white_noise)
  } 

  init { | ... theInputs |
      inputs = theInputs
      ^this.initOutputs(8, rate)
  }
  
/*
  SynthDef - Typical usage in SuperCollider:

  ~synth = Synth(\faustTester, 
	[	freq:freqVar,
		volume:volumeVar,
		destination:destinationVar,
		pink_noise:pink_noiseVar,
		sine_wave:sine_waveVar,
		white_noise:white_noiseVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustTester,
      SynthDef(\faustTester,
        { |out=0|
          Out.ar(out, 
            FaustTester.ar(
              \freq.kr(1000.0), \volume.kr(-96.0), \destination.kr(0.0), \pink_noise.kr(0.0), \sine_wave.kr(0.0), \white_noise.kr(0.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \freq:[10.0, 20000.0, 0, 1.0, 1000.0].asSpec,
          \volume:[-96.0, 0.0, 0, 1.0, -96.0].asSpec,
          \destination:[0.0, 8.0, 0, 1.0, 0.0].asSpec,
          \pink_noise:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \sine_wave:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \white_noise:[0.0, 0.0, 0, 0.0, 0.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustTester" }
}

