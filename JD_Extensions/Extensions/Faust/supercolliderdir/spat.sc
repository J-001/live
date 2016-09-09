FaustSpat : MultiOutUGen
{
  *ar { | in1, angle(0.0), distance(0.5) |
      ^this.multiNew('audio', in1, angle, distance)
  }

  *kr { | in1, angle(0.0), distance(0.5) |
      ^this.multiNew('control', in1, angle, distance)
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
      ^this.initOutputs(8, rate)
  }
  
/*
  SynthDef - Typical usage in SuperCollider:

["  in1Bus = Bus.audio(s,1);\n"]
  ~synth = Synth(\faustSpat, 
	[	in1:in1Bus.asMap,
		angle:angleVar,
		distance:distanceVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustSpat,
      SynthDef(\faustSpat,
        { |out=0|
          Out.ar(out, 
            FaustSpat.ar(
              \in1.ar(0), \angle.kr(0.0), \distance.kr(0.5)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \angle:[0.0, 1.0, 0, 0.01, 0.0].asSpec,
          \distance:[0.0, 1.0, 0, 0.01, 0.5].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustSpat" }
}

