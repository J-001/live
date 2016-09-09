FaustHarpe : MultiOutUGen
{
  *ar { | attenuation(0.005), hand(0.0), level(0.5) |
      ^this.multiNew('audio', attenuation, hand, level)
  }

  *kr { | attenuation(0.005), hand(0.0), level(0.5) |
      ^this.multiNew('control', attenuation, hand, level)
  } 

  init { | ... theInputs |
      inputs = theInputs
      ^this.initOutputs(2, rate)
  }
  
/*
  SynthDef - Typical usage in SuperCollider:

  ~synth = Synth(\faustHarpe, 
	[	attenuation:attenuationVar,
		hand:handVar,
		level:levelVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustHarpe,
      SynthDef(\faustHarpe,
        { |out=0|
          Out.ar(out, 
            FaustHarpe.ar(
              \attenuation.kr(0.005), \hand.kr(0.0), \level.kr(0.5)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \attenuation:[0.0, 0.01, 0, 0.001, 0.005].asSpec,
          \hand:[0.0, 1.0, 0, 0.01, 0.0].asSpec,
          \level:[0.0, 1.0, 0, 0.01, 0.5].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustHarpe" }
}

