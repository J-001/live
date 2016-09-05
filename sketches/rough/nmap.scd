"~/Live/setup/init.scd".loadPaths;

Ndef(\src, {
	PinkNoise.ar(0.1);
	});

Ndef(\filt, {|freq = 1000, amp|
	var dry = \in.ar([0,0]);
	var wet = LPF.ar(dry, freq);
	wet;
	});

Ndef(\map, {MouseX.kr()}).specifyN(\amp, \freq)


Ndef(\filt).xset(\freq, 2000);
Ndef(\filt).xset(\freq, Ndef(\map));

Ndef(\filt).smap(\freq, Ndef(\map));
Ndef(\filt).smap(\amp, Ndef(\map));

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------

Ndef(\map).specDict.subsContain(\freq.asSpec);
Ndef(\map).specDict.do{|i|
	i[1].clear
}
Ndef(\map).specific(\freq.asSpec);
Ndef(\map).specDict.choose[1].bus;
Ndef(\map).clearSpecs(4);


Ndef(\k, 200);
Ndef(\k)[0] = {MouseX.kr(20,20000)};
Ndef(\k)[1] = 2000;

o = ()
o[\qw] = \freq.asSpec
o.has(\freq.asSpec)
