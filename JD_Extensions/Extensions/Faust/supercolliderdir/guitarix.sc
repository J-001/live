FaustGuitarix : UGen
{
  *ar { | in1, drive(0.5), level(-16.0), tone(400.0), pregain(-6.0), gain(-6.0), treble(0.5), middle(0.5), bass(0.5), amount(100.0) |
      ^this.multiNew('audio', in1, drive, level, tone, pregain, gain, treble, middle, bass, amount)
  }

  *kr { | in1, drive(0.5), level(-16.0), tone(400.0), pregain(-6.0), gain(-6.0), treble(0.5), middle(0.5), bass(0.5), amount(100.0) |
      ^this.multiNew('control', in1, drive, level, tone, pregain, gain, treble, middle, bass, amount)
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
  
/*
  SynthDef - Typical usage in SuperCollider:

["  in1Bus = Bus.audio(s,1);\n"]
  ~synth = Synth(\faustGuitarix, 
	[	in1:in1Bus.asMap,
		drive:driveVar,
		level:levelVar,
		tone:toneVar,
		pregain:pregainVar,
		gain:gainVar,
		treble:trebleVar,
		middle:middleVar,
		bass:bassVar,
		amount:amountVar
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustGuitarix,
      SynthDef(\faustGuitarix,
        { |out=0|
          Out.ar(out, 
            FaustGuitarix.ar(
              \in1.ar(0), \drive.kr(0.5), \level.kr(-16.0), \tone.kr(400.0), \pregain.kr(-6.0), \gain.kr(-6.0), \treble.kr(0.5), \middle.kr(0.5), \bass.kr(0.5), \amount.kr(100.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \drive:[0.0, 1.0, 0, 0.01, 0.5].asSpec,
          \level:[-20.0, 4.0, 0, 0.1, -16.0].asSpec,
          \tone:[100.0, 1000.0, 0, 1.03, 400.0].asSpec,
          \pregain:[-20.0, 20.0, 0, 0.1, -6.0].asSpec,
          \gain:[-20.0, 20.0, 0, 0.1, -6.0].asSpec,
          \treble:[0.0, 1.0, 0, 0.01, 0.5].asSpec,
          \middle:[0.0, 1.0, 0, 0.01, 0.5].asSpec,
          \bass:[0.0, 1.0, 0, 0.01, 0.5].asSpec,
          \amount:[0.0, 100.0, 0, 1.0, 100.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustGuitarix" }
}

