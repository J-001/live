FaustUITester : MultiOutUGen
{
  *ar { | in1, in2, in3, in4, in5, in6, button_0(0.0), hslider_0(60.0), num_0(60.0), vslider4_0(60.0), knob1_0(60.0), knob2_0(60.0), knob3_0(60.0), vslider1_0(60.0), vslider2_0(60.0), vslider3_0(60.0), check1_0(0.0), check2_0(0.0), knob0_0(60.0), button_1(0.0), hslider_1(60.0), knob4_1(60.0), num1_1(60.0), vslider5_1(60.0), vslider6_1(60.0), knob5_1(60.0), num2_1(60.0), vslider7_1(60.0), vslider8_1(60.0), knob6_1(60.0), num3_1(60.0), vslider10_1(60.0), vslider9_1(60.0), vslider4_1(60.0) |
      ^this.multiNew('audio', in1, in2, in3, in4, in5, in6, button_0, hslider_0, num_0, vslider4_0, knob1_0, knob2_0, knob3_0, vslider1_0, vslider2_0, vslider3_0, check1_0, check2_0, knob0_0, button_1, hslider_1, knob4_1, num1_1, vslider5_1, vslider6_1, knob5_1, num2_1, vslider7_1, vslider8_1, knob6_1, num3_1, vslider10_1, vslider9_1, vslider4_1)
  }

  *kr { | in1, in2, in3, in4, in5, in6, button_0(0.0), hslider_0(60.0), num_0(60.0), vslider4_0(60.0), knob1_0(60.0), knob2_0(60.0), knob3_0(60.0), vslider1_0(60.0), vslider2_0(60.0), vslider3_0(60.0), check1_0(0.0), check2_0(0.0), knob0_0(60.0), button_1(0.0), hslider_1(60.0), knob4_1(60.0), num1_1(60.0), vslider5_1(60.0), vslider6_1(60.0), knob5_1(60.0), num2_1(60.0), vslider7_1(60.0), vslider8_1(60.0), knob6_1(60.0), num3_1(60.0), vslider10_1(60.0), vslider9_1(60.0), vslider4_1(60.0) |
      ^this.multiNew('control', in1, in2, in3, in4, in5, in6, button_0, hslider_0, num_0, vslider4_0, knob1_0, knob2_0, knob3_0, vslider1_0, vslider2_0, vslider3_0, check1_0, check2_0, knob0_0, button_1, hslider_1, knob4_1, num1_1, vslider5_1, vslider6_1, knob5_1, num2_1, vslider7_1, vslider8_1, knob6_1, num3_1, vslider10_1, vslider9_1, vslider4_1)
  } 

  checkInputs {
    if (rate == 'audio', {
      6.do({|i|
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
      ^this.initOutputs(34, rate)
  }
  
/*
  SynthDef - Typical usage in SuperCollider:

["  in1Bus = Bus.audio(s,1);\n", "  in2Bus = Bus.audio(s,1);\n", "  in3Bus = Bus.audio(s,1);\n", "  in4Bus = Bus.audio(s,1);\n", "  in5Bus = Bus.audio(s,1);\n", "  in6Bus = Bus.audio(s,1);\n"]
  ~synth = Synth(\faustUITester, 
	[	in1:in1Bus.asMap,
		in2:in2Bus.asMap,
		in3:in3Bus.asMap,
		in4:in4Bus.asMap,
		in5:in5Bus.asMap,
		in6:in6Bus.asMap,
		button_0:button_0Var,
		hslider_0:hslider_0Var,
		num_0:num_0Var,
		vslider4_0:vslider4_0Var,
		knob1_0:knob1_0Var,
		knob2_0:knob2_0Var,
		knob3_0:knob3_0Var,
		vslider1_0:vslider1_0Var,
		vslider2_0:vslider2_0Var,
		vslider3_0:vslider3_0Var,
		check1_0:check1_0Var,
		check2_0:check2_0Var,
		knob0_0:knob0_0Var,
		button_1:button_1Var,
		hslider_1:hslider_1Var,
		knob4_1:knob4_1Var,
		num1_1:num1_1Var,
		vslider5_1:vslider5_1Var,
		vslider6_1:vslider6_1Var,
		knob5_1:knob5_1Var,
		num2_1:num2_1Var,
		vslider7_1:vslider7_1Var,
		vslider8_1:vslider8_1Var,
		knob6_1:knob6_1Var,
		num3_1:num3_1Var,
		vslider10_1:vslider10_1Var,
		vslider9_1:vslider9_1Var,
		vslider4_1:vslider4_1Var
	]);
 */

  *initClass {
    StartUp.add {
      SynthDesc.mdPlugin = TextArchiveMDPlugin;
// When SynthDef.writeOnce writes metadata:
//    SynthDef.writeOnce(\faustUITester,
      SynthDef(\faustUITester,
        { |out=0|
          Out.ar(out, 
            FaustUITester.ar(
              \in1.ar(0), \in2.ar(0), \in3.ar(0), \in4.ar(0), \in5.ar(0), \in6.ar(0), \button_0.kr(0.0), \hslider_0.kr(60.0), \num_0.kr(60.0), \vslider4_0.kr(60.0), \knob1_0.kr(60.0), \knob2_0.kr(60.0), \knob3_0.kr(60.0), \vslider1_0.kr(60.0), \vslider2_0.kr(60.0), \vslider3_0.kr(60.0), \check1_0.kr(0.0), \check2_0.kr(0.0), \knob0_0.kr(60.0), \button_1.kr(0.0), \hslider_1.kr(60.0), \knob4_1.kr(60.0), \num1_1.kr(60.0), \vslider5_1.kr(60.0), \vslider6_1.kr(60.0), \knob5_1.kr(60.0), \num2_1.kr(60.0), \vslider7_1.kr(60.0), \vslider8_1.kr(60.0), \knob6_1.kr(60.0), \num3_1.kr(60.0), \vslider10_1.kr(60.0), \vslider9_1.kr(60.0), \vslider4_1.kr(60.0)
            )
          )
        }, metadata: (specs:( 
        //\controlName:[min, max, warp, step, default, units].asSpec,
          \button_0:[0.0, 1, 0, 1, 0.0].asSpec,
          \hslider_0:[0.0, 127.0, 0, 0.1, 60.0].asSpec,
          \num_0:[0.0, 127.0, 0, 0.1, 60.0].asSpec,
          \vslider4_0:[0.0, 127.0, 0, 0.1, 60.0].asSpec,
          \knob1_0:[0.0, 127.0, 0, 0.1, 60.0].asSpec,
          \knob2_0:[0.0, 127.0, 0, 0.1, 60.0].asSpec,
          \knob3_0:[0.0, 127.0, 0, 0.1, 60.0].asSpec,
          \vslider1_0:[0.0, 127.0, 0, 0.1, 60.0].asSpec,
          \vslider2_0:[0.0, 127.0, 0, 0.1, 60.0].asSpec,
          \vslider3_0:[0.0, 127.0, 0, 0.1, 60.0].asSpec,
          \check1_0:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \check2_0:[0.0, 0.0, 0, 0.0, 0.0].asSpec,
          \knob0_0:[0.0, 127.0, 0, 0.1, 60.0].asSpec,
          \button_1:[0.0, 1, 0, 1, 0.0].asSpec,
          \hslider_1:[0.0, 127.0, 0, 0.1, 60.0].asSpec,
          \knob4_1:[0.0, 127.0, 0, 0.1, 60.0].asSpec,
          \num1_1:[0.0, 127.0, 0, 0.1, 60.0].asSpec,
          \vslider5_1:[0.0, 127.0, 0, 0.1, 60.0].asSpec,
          \vslider6_1:[0.0, 127.0, 0, 0.1, 60.0].asSpec,
          \knob5_1:[0.0, 127.0, 0, 0.1, 60.0].asSpec,
          \num2_1:[0.0, 127.0, 0, 0.1, 60.0].asSpec,
          \vslider7_1:[0.0, 127.0, 0, 0.1, 60.0].asSpec,
          \vslider8_1:[0.0, 127.0, 0, 0.1, 60.0].asSpec,
          \knob6_1:[0.0, 127.0, 0, 0.1, 60.0].asSpec,
          \num3_1:[0.0, 127.0, 0, 0.1, 60.0].asSpec,
          \vslider10_1:[0.0, 127.0, 0, 0.1, 60.0].asSpec,
          \vslider9_1:[0.0, 127.0, 0, 0.1, 60.0].asSpec,
          \vslider4_1:[0.0, 127.0, 0, 0.1, 60.0].asSpec
  ) ) ).store } }
// When SynthDef.writeOnce writes metadata:
//) ) ) } }

  name { ^"FaustUITester" }
}

