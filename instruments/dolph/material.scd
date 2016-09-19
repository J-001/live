i !? {i.load(\dolph)};
//-----------------------------------------------------------------------
//------------KEY 88---------------------------------------------------
~key88[\dolph] = {
	
	if (MIDIdef.all[\key88_noteOn].notNil) {
		MIDIdef.all[\key88_noteOn] = nil;
		MIDIdef.all[\key88_noteOff] = nil;
	};

	MIDIdef.noteOn(\key88_noteOn, {|val, num|
		var rel, amp, midinote, n;
		n = num - 21;
		amp = val.linlin(1, 127, 0.0, 1.0);
		rel = val.linexp(1, 127, 0.1, 4.0);
		midinote = num;
		~key88[\notes][n] = Synth.head(Ndef(\key88).group, \dolph_asr, [\out, Ndef(\key88).bus.index, \freq, midinote, \amp, amp, \rel, rel]);

	});

	MIDIdef.noteOff(\key88_noteOff, {|val, num|
		var n = num - 21;
		~key88[\notes][n].release;
	});
};
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
"pattern_component : ".post;
Pdef(\dolph_arp_c.postln, 
	PbindProxy(
		\instrument, \dolph_asr,
		\freq, Pseq([0, 4, 7 , 11, 2 + 12, 5 + 12, 9 + 12], 3) + 48,
		\dur, Pseq([1/3, 1/3, 1/3, 1/3, 1/3, 1/3, 2], 3),
		\legato, Pseq([1.1!6, 0.25].flatten, 3), 
		\rel, Pseq([0.5!6, 4].flatten, 3),
	)
);
//-----------------------------------------------------------------------
"pattern_component : ".post;
Pdef(\dolph_change_up.postln, 
	PbindProxy(
		\instrument, \dolph_asr,
		\freq, Pseq([0, 4, 7 , 11]) ++ Pseq([4, 7 , 11, 2 + 12]) + 48,
		\dur, Pseq([1/3, 1/3, 1/3, 1], 2),
		\legato,0.5,
		\rel, Pseq([0.5!4].flatten,2),
	)
);
//-----------------------------------------------------------------------
"pattern_component : ".post;
Pdef(\dolph_arp_f.postln, 
		\dolph_arp_c >< Pbind(\freq , Pseq([0, 4, 7 , 11, 2 + 12, 6+ 12, 9 + 12], 3) + 48 + 5)
);
//-----------------------------------------------------------------------
"pattern_component : ".post;
Pdef(\dolph_change_down.postln, 
		\dolph_change_up >< Pbind(\freq, Pseq([4, 7 , 11, 2 + 12]) ++ Pseq([2, 5, 9, 0 + 12]) + 48)
);
//-----------------------------------------------------------------------
"pattern : ".post;
Pdef(\dolphGtr.postln,
	Pseq([
		Pdef(\dolph_arp_c),
		Pdef(\dolph_change_up),
		Pdef(\dolph_arp_f),
		Pdef(\dolph_change_down),
		],inf
	) 
);
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
Pdef(\dolCascade.postln,
	PbindProxy(
		\instrument, \dolph,
		\legato, Pwhite(1.25, 2.0),
		\dur, Pwhite(0.5,4.0),
		\rel, Pfunc{|e|( e.legato * e.dur)},
		\freq, Prand(Scale.aeolian.degrees,inf)  + Prand([12,24],inf) + 90 ,
		\amp,0.05
	)
)
