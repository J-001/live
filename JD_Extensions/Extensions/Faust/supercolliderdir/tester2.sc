FaustStereoAudioTester : MultiOutUGen
{
  *ar { | freq(440.0), volume(-96.0), signal(0.0), channel(0.0) |
      ^this.multiNew('audio', freq, volume, signal, channel)
  }

  *kr { | freq(440.0), volume(-96.0), signal(0.0), channel(0.0) |
      ^this.multiNew('control', freq, volume, signal, channel)
  } 

  init { | ... theInputs |
      inputs = theInputs
      ^this.initOutputs(2, rate)
  }
  
/*
  SynthDef - Typical usage in SuperCollider:

  ~synth = Synth(\faustStereoAudioTester, 
	[	freq:freqVar,
		volume:volumeVar,
		signal:signalVar,
		channel:channelVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustStereoAudioTester,
      SynthDef(\faustStereoAudioTester,
        { |out=0|
          Out.ar(out, 
            FaustStereoAudioTester.ar(
              \freq.kr(440.0), \volume.kr(-96.0), \signal.kr(0.0), \channel.kr(0.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \freq:[40.0, 20000.0, 0, 1.0, 440.0].asSpec,
          \volume:[-96.0, 0.0, 0, 1.0, -96.0].asSpec,
          \signal:[0.0, 2.0, 0, 1.0, 0.0].asSpec,
          \channel:[0.0, 3.0, 0, 1.0, 0.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustStereoAudioTester" }
}

