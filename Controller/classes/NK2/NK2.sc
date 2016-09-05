NK2 : Object {

	/*
	dictionary of dictionaries containing different types of controller,
	get values of rep
	*/

	var<>controls;
	var<>faders;
	var<>knobs;
	var<>solos;
	var<>mutes;
	var<>records;
	var<>transport;
	var <>ccs, <>toggles, <>trigs;

	*new {|key|
		^super.new.init;
	}

	init {
		// IF Midiclient not initalised initialise 
		if (MIDIClient.initialized!=true,{
			MIDIClient.init;
			});
		// Find nanoKONTROL2 in sources
		MIDIClient.sources.do{|source,n|
			if(source.device=="nanoKONTROL2") {
				"nanoKONTROL2 connected".postln;
				MIDIIn.connect(n, source)
			};
		};	
		//KONTROL KEYS
		controls=();

		faders=[];
		knobs=[];
		solos=[];
		mutes=[];
		records=[];
		transport=[];

		ccs = ();
		trigs = ();
		toggles = ();

		faders=[
			[\fader0,0],
			[\fader1,1],
			[\fader2,2],
			[\fader3,3],
			[\fader4,4],
			[\fader5,5],
			[\fader6,6],
			[\fader7,7]].collect{|pair|[pair,\cc].flat};
		knobs=[
			[\knob0,16],
			[\knob1,17],
			[\knob2,18],
			[\knob3,19],
			[\knob4,20],
			[\knob5,21],
			[\knob6,22],
			[\knob7,23]].collect{|pair|[pair,\cc].flat};
		solos=[
			[\s0,32],
			[\s1,33],
			[\s2,34],
			[\s3,35],
			[\s4,36],
			[\s5,37],
			[\s6,38],
			[\s7,39]].collect{|pair|[pair,\toggle].flat};
		mutes=[
			[\m0,48],
			[\m1,49],
			[\m2,50],
			[\m3,51],
			[\m4,52],
			[\m5,53],
			[\m6,54],
			[\m7,55]].collect{|pair|[pair,\toggle].flat};
		records=[
			[\r0,64],
			[\r1,65],
			[\r2,66],
			[\r3,67],
			[\r4,68],
			[\r5,69],
			[\r6,70],
			[\r7,71]].collect{|pair|[pair,\toggle].flat};
		transport=[
			[\play,41,\trig],
			[\stop,42,\trig],
			[\rec,45,\toggle],
			[\rewind,43,\trig],
			[\ffw,44,\trig],
			[\fwdTrack,59,\trig],
			[\bkTrack,58,\trig],
			[\cycle,46,\trig],
			[\setMarker,60,\trig],
			[\bkMarker,61,\trig],
			[\fwdMarker,62,\trig]];	

		[faders,knobs,solos,mutes,records,transport].flatten.do{|pair|

			(pair[2]==\cc).if({
				controls[pair[0]] = NKControl(pair[0],pair[1]);
				ccs[pair[0]] = controls[pair[0]];
			});

			(pair[2]==\toggle).if({
				controls[pair[0]] = NKToggle(pair[0],pair[1]);
				toggles[pair[0]] = controls[pair[0]];
				});

			(pair[2]==\trig).if({
				controls[pair[0]] = NKTrig(pair[0],pair[1]);
				trigs[pair[0]] = controls[pair[0]];
				});
			};

	}

	free {|key|
		controls[key].free;
	}

	freeAll {
		[faders,knobs,solos,mutes,records].flatten.do{|control|
			var defName  = (control[0]).asSymbol;

			(this.controls[defName].mididef!=nil).if({
						(defName++" freed").postln;
						this.controls[defName].free;
				});
		}
	}

	clearSettings{
		"python /Users/JDMuschett/Live/Controller/nkClearLayout.py".unixCmd
	}

	at {|key|
		^controls.at(key);
	}

}

//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

NKControl  {
	var<>num;
	var<>type;
	var<>name;
	var ccFunc;
	var cc;
	var<>mididef;

	*new {|kName,kNum|
		^super.new.init(kName,kNum);
	}

	init {|kName,kNum|

		num=kNum;
		type=\cc;
		name=kName;
	}

	def {|normalize=true|
		var defName;
		defName=('nk2'++name).asSymbol;

		(MIDIdef(defName).key!=nil).if({MIDIdef(defName).free});

		mididef = MIDIdef.cc('nk2'++name,{|val, num, chan, src|
			// name.postln;
			// ("VAL: "++val).postln;
			ccFunc.value(
				if(normalize,
					{val.linlin(1,127,0,1)},
					{val});
				);
			},num);
	}

	cc_ {|func,persist=true,normalize=false|
		var pyFunc;
		var pipe, line;
		ccFunc = func;
		//TO SETTINGS PAGE
		pyFunc = "n['"++this.name++"'].cc_("++ccFunc.asCompileString++");";
		pyFunc = "python /Users/JDMuschett/Live/Controller/nkLayout.py "
			++this.name.asString++" "++pyFunc.asCompileString;
		// pyFunc.postln;
		pyFunc.unixCmd;

		this.def.value(normalize);

		if(persist,
			{this.addToTree({this.def.value(normalize)})},
			{this.removeFromTree});
	}

	addToTree {|func|
		ServerTree.put(func,name);
	}

	removeFromTree {
		ServerTree.remove(name);
	}	

	free {
		this.mididef.free;
	}

}
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
NKTrig : NKControl {

	var onFunc;
	var <on;

	def {
		var defName;
		defName=('nk2'++name).asSymbol;

		(MIDIdef(defName).key!=nil).if({MIDIdef(defName).free});

		mididef = MIDIdef.cc(defName,{|val, num, chan, src|
			if (val==1,{
				// name.postln;
			// ("VAL: "++val).postln;
				onFunc.value;
				});
		},num);
	}

	on_ {|func,persist=true|
		var pyFunc, pyKey;
		var pipe, line;
		onFunc = func;
		//TO SETTINGS PAGE
		pyKey = this.name.asString++"'].on";
		pyFunc = "n['"++pyKey++"_("++onFunc.asCompileString++");";
		pyFunc = "python /Users/JDMuschett/Live/Controller/nkLayout.py "
		++pyKey.asCompileString++" "++pyFunc.asCompileString;
		pyFunc.unixCmd();

		this.def.value;
		if(persist,
			{this.addToTree({this.def.value})},
			{this.removeFromTree});
	}
}
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
NKToggle : NKTrig{

	var offFunc;
	var off;

	def {
		var defName;
		defName=('nk2'++name).asSymbol;

		(MIDIdef(defName).key!=nil).if({MIDIdef(defName).free});

		mididef = MIDIdef.cc(defName,{|val, num, chan, src|
			// name.postln;
			// ("VAL: "++val).postln;
			if (val==1,{
				onFunc.value;
				});
			if (val==0) {
				offFunc.value;
			}
		},num);
	}

	off_ {|func,persist=true|
		var pyFunc, pyKey;
		var pipe, line;
		offFunc = func;
		//TO SETTINGS PAGE
		pyKey = this.name.asString++"'].off";
		pyFunc = "n['"++pyKey++"_("++offFunc.asCompileString++");";
		pyFunc = "python /Users/JDMuschett/Live/Controller/nkLayout.py "
			++pyKey.asCompileString++" "++pyFunc.asCompileString;
		pyFunc.unixCmd();
		this.def.value;

		if(persist,
			{this.addToTree({this.def.value})},
			{this.removeFromTree});
	}
}
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
