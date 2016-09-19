//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
(	
	s.boot;
	//SET UPHORIA as AUDIO DEVICE
	if (ServerOptions.devices.contains("UMC404HD 192k")) {
		s.options.inDevice_("UMC404HD 192k");
		s.options.outDevice_("UMC404HD 192k");
		s.reboot;
	};

	(s.options.memSize <= (65536 * 4)).if(
		{"Enough Memory".postln},
	{s.options.memSize_(65536 * 4);
		s.reboot;}
	);

	x = nil; //quick nil
	a = AreaPlan();

	b = (); //BUFFERS
	// b[\load] = {
	// 	"~/Live/setup/loadJDSounds.scd".standardizePath.loadPaths;
	// };
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

	if (ServerTree.dict[\NKConf].isNil) {
		ServerTree.put({
				n[\fwdMarker].on_({ NKConf.fwd});
				n[\bkMarker].on_({ NKConf.back});
				n[\setMarker].on_({ NKConf.set});
				n[\cycle].on_({ NKConf.clear; n.clearSettings});
		}, \NKConf);
	};
	c = ();
	c[\refresh] = {
				n[\fwdMarker].on_({ NKConf.fwd});
				n[\bkMarker].on_({ NKConf.back});
				n[\setMarker].on_({ NKConf.set});
				n[\cycle].on_({ NKConf.clear; n.clearSettings});
	};
	
	t = ();
	t[\default] = TempoClock.default;
	t[\a] = TempoClock(60/60).permanent_(true); //TEMPO

	//SPECS
	Spec.add(\legato, ScalarSpec(0.001, 10, \linear, 0, 1));
	Spec.add(\dur, ScalarSpec(0.001, 1000, \linear, 0, 1));
	Spec.add(\lfo, [0.001, 20, \linear, 0]);
	Spec.add(\rate, [-1000, 1000, \linear, 0,1]);
	Spec.add(\done, ScalarSpec([0,14, \linear, 1,2]));
	//EFFECTS
	Spec.add(\mix, [0,1, \linear, 0, 0.333]);
	//ENV
	Spec.add(\env,EnvSpec(Env.newClear(8)));
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

//MetroNome
// t = ();
// (
// 	t[\basic] = ();
// 	t[\basic][\tempo] = 1;
// 	t[\basic][\window] = Window("TEMPO:"+"Basic", Rect(1440-300,100,300,100));
// 	t[\basic][\window].background_(Color(0.25,0.25,0.25));
// 	t[\basic][\window].alpha_(1);
// 	t[\basic][\window].alwaysOnTop_(true);
// 	t[\basic][\text] = TextView(t[\basic][\window].asView, Rect(40, 20, 200, 50));
// 	t[\basic][\text].editable_(false);
// 	t[\basic][\beats] = 4;
// 	t[\basic][\bars] = 4;
// 	t[\basic][\metro] =  { 
// 		t[\basic][\window].front;
// 		Routine({
// 		100.do {|n|
// 			t[\basic][\bars].do{|bar|
// 				t[\basic][\beats].do{|beat|
// 					var string = "\tbar :" + bar.asString++"\n"
// 						"\tbeat :" + beat.asString;
// 					t[\basic][\text].setString(
// 						string,
// 						0,
// 						string.size
// 						);
// 					t[\basic][\text].stringColor_(Color(0.75,0.75,0.75));
// 					t[\basic][\text].background_(Color(0.25,0.25,0.25));
// 					1.wait;
// 				};		
// 			};
// 		}
// 		});
// };

// 	Event.addEventType(\beginConductor, { AppClock.play(t.basic.metro)  });
		
// );

~key88 = ();
~key88.init = {
	var done = false;

	if (MIDIClient.initialized!=true,{
			MIDIClient.init;
			});
		// Find nanoKONTROL2 in sources
		MIDIClient.sources.do{|source,n|
			if (source.device=="Keystation 88") {
				if (done == false) {
					"Keystation 88 connected".postln;
					MIDIIn.connect(n, source);
					done = true;
				};
			};
		};	

		Ndef(\key88);

		~key88[\notes] = nil!88;
};
	
	
)

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------


//SETTINGS AS TEXT asView
/*
q = ();
q[\window] = Window("Conductor", Rect(10,900,400,800));
q[\text] = TextView(q[\window].asView, Rect(10, 10, 400 - 10, 800 - 10));
q[\text].open("~/Live/JD_Extensions/Controller/NK2/settings.scd".standardizePath);
q[\text].syntaxColorize;
q[\text].background_(Color(0.2,0.2,0.2));
q[\text].enterInterpretsSelection = true;
q[\window].front;
*/
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

