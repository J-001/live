//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
(
	
	//SET UPHORIA as AUDIO DEVICE
	if (ServerOptions.devices.contains("UMC404HD 192k")) {
		s.options.inDevice_("UMC404HD 192k");
		s.options.outDevice_("UMC404HD 192k");
		// s.options.outDevice_(nil);
		s.options.sampleRate_(192000 * 0.5);
		s.reboot;
	};
	

	(s.options.memSize == (65536 * 4)).if(
		{"Enough Memory".postln},
	{s.options.memSize_(65536 * 4);
		s.reboot;}
	);

	a = (); //AUDIO PROXIES
	k = (); //CONTROL PROXIES

	b = (); //BUFFERS
	b[\load] = {
		"~/Live/setup/loadJDSounds.scd".standardizePath.loadPaths;
	};
	p = (); //patterns
	i = (); //instruments
	i[\load] = {|function ... names|
		names.do{|name|
			("~/Live/instruments/".standardizePath++name.asString++"/inst.scd").loadPaths; 
			}
	};
	i[\mat] = {|function ... names|
		names.do{|name|
			("~/Live/instruments/".standardizePath++name.asString++"/material.scd").loadPaths; 
			}
	};
	f = ();
	f[\load] = {|function, name|
		("~/Live/filters/".standardizePath++name.asString++".scd").loadPaths; 
	};
	n = NK2(); //nanoKONTROL2
	ServerTree.put({
		if(n.class == NK2){
			n[\fwdMarker].on_({ NKConf.fwd});
			n[\bkMarker].on_({ NKConf.back});
			n[\setMarker].on_({ NKConf.set});
			n[\cycle].on_({ NKConf.clear; n.clearSettings});
		};
	}, \nkConf);
	u = (); //NKinstrumentSETTINGS
	t = TempoClock(60/60).permanent_(true); //TEMPO

	// nil shorthand
	x = nil;

	//SPECS
	Spec.add(\legato, ScalarSpec(0.001, 10, \linear, 0, 1));
	Spec.add(\dur, ScalarSpec(0.001, 1000, \linear, 0, 1));
	Spec.add(\lfo, [0.001, 20, \linear, 0]);
	Spec.add(\rate, [-1000, 1000, \linear, 0,1]);
	Spec.add(\done, ScalarSpec([0,14, \linear, 1,2]));
	//EFFECTS
	Spec.add(\mix, [0,1, \linear, 0, 0.333]);
	//ENV
	Spec.add(\atk,[0.001,30, 2]);
	//JPVerb
	Spec.add(\t60, [0.1, 60, \exp]);
	Spec.add(\damp, [0, 1]);
	Spec.add(\size, [0.5, 3]);
	Spec.add(\earlydiff, [0, 1]);
	Spec.add(\mdepth, [0, 5]);
	Spec.add(\mfreq, [0, 10]);
	Spec.add(\lowx, [0, 1]);
	Spec.add(\midx, [0, 1]);
	Spec.add(\highx, [0, 1]);
	Spec.add(\lowband, [100, 6000, \exp]);
	Spec.add(\highband, [1000, 15000, \exp]);

	//GUIS

	// (s.serverRunning==false).if({s.plotTree});
)
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

// (
// var instrPath = "~/Live/setup/instruments".standardizePath;

// PathName(instrPath).entries.do{|sub|
// 	sub.entries.do{|files|
// 		files.fullPath.loadPaths;
// 	}
// }

// )

/*

Give all pdefs/proxies contol proxies for each arg???
//INITALIZE KPROXIES
k['dreamverb'] ?? {k[\dreamverb] = Ndef(\dreamverb).kvals };
//RESET KPROXIES
k['dreamverb'] !? {k[\dreamverb].do(_.clear); k[\dreamverb] = Ndef(\dreamverb).kvals };
//LIST KPROXYNAMES
k.dreamverb['list'] = {Ndef('dreamverb').getKeysValues.do{|i|i[0].postln}};

*/

