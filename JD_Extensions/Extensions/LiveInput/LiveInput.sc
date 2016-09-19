/*
	Still In beta
*/

LiveInput : Ndef {
	
	var <>channelsArray;
	var <>recHeading;
	var <>recBuf;
	var recSynth;

	classvar <>recordings, <>hasLoaded;
	classvar <>recDir;
	classvar <>recDict; //SET AS SoundLib //currently identityDct - b
	classvar <>all;

	* initClass {
		all = ();
		recordings = ();
		hasLoaded = false;
		recDir = "~/JDSounds/rec".standardizePath;

		this.initRecordings.postln;

		//UNTIMED
		(1..2).do {|n|
			SynthDef('diskout'++n.asSymbol, {|bufnum, bus = 0|
   				DiskOut.ar(bufnum, In.ar(bus,n));
			}).add;
		};
		(1..2).do {|n|
			SynthDef('recordBuf'++n.asSymbol, {|bufnum, bus = 0|
   				RecordBuf.ar( In.ar(bus,n),bufnum, doneAction:2);
			}).add;
		};
		//ADD CALLBACK TO BE USED INSIDE SYMBOL CONVERTER
		['_L','_l'].do{|ext|
			SymbolConverter.addExtConvFunc(ext, {|symbol|
			 	LiveInput(symbol);
			})
		};

		SymbolConverter.addinputConvFunc('@', 
			(
				\test : {| input |
					if (input.isKindOf(SimpleNumber)) { true} {false};
				}, 	
				\type :	{| 	symbol |
				 LiveInput(symbol).postln;
				}
			)
		)
		
	}

	*initRecordings {

		if(hasLoaded.not) {
			var path = PathName(recDir);
			path.entries.do {| folder |
				var folderName = folder.folderName.asSymbol.postln;
				folder.entries.do {|sound|
					var soundName = sound.fileName.asSymbol.postln;
					var path = sound.fullPath;
					if (recordings[folderName].isNil) {
						 recordings[folderName] = ();
					};
					if (recordings[folderName][soundName].isNil) {
						recordings[folderName][soundName] = Buffer.read(path: path);
					}
				}
			};
			hasLoaded = true
		};
	}

	*new {|aKey, aChannelsArray|
		var instance = all[aKey.asSymbol];
		
		if (instance.isNil) { 
			instance = super.new(aKey);	
			instance.channelsArray = aChannelsArray;
			instance.channelsArray !? {
				instance.source = {|amp = 1| SoundIn.ar(instance.channelsArray) * amp.lag(0.05)}
			};

			instance.recHeading = aKey.asString;
			recordings[instance.key] = ();
			all[instance.key] = instance;

			// Add Methods For use with Symbol
			[\buf, \rec, \srec].do{|method|
					instance.key.addUniqueMethod(
						method, 
						{|...arrArgs| 
							(arrArgs[1..arrArgs.size-1]).postln;
							LiveInput(instance.key).perform( method, *(arrArgs[1..arrArgs.size-1]))
						}
					)
				}
		} {
			//NOT NIL
			if (instance.isNeutral) { 
				instance.channelsArray = aChannelsArray;
				instance.channelsArray !? {
					instance.source = {|amp = 1| SoundIn.ar(instance.channelsArray) * amp.lag(0.05)}
				};
			};
		};

		^instance;
	}

	startRecording {|aTitle = ""| 
		var recTitle = aTitle.asString;
		var recPath = PathName(recDir +/+ recHeading +/+ recTitle);

		if (PathName(recPath.pathOnly).isFolder.not) {
			recPath.pathOnly.postln;
			("mkdir "++recPath.pathOnly.asString).unixCmd;
		};
		recBuf = Buffer.alloc(Server.default, 131072.nextPowerOfTwo, this.numChannels);
		recBuf.write(recPath.fullPath++".wav", "WAV", "int24", 0, 0, true);

		recSynth = Synth.tail(this.group, 'diskout'++this.numChannels.asSymbol, [\bus, this.bus, \bufnum, recBuf]);
		this.play;
	}

	//Doesn't write to Disk
	timedRecording {|aTitle, aDur = 4|
		var recTitle = aTitle.asString;
		var recPath = PathName(recDir +/+ recHeading +/+ recTitle);
		var dur = aDur;
		recBuf = Buffer.alloc(Server.default, Server.default.sampleRate * dur, this.numChannels);
		recSynth = Synth.tail(this.group, 'recordBuf'++this.numChannels.asSymbol, [\bus, this.bus, \bufnum, recBuf]);
		this.play;
		"HERE".postln;
		Routine({
			dur.wait;
			("completed:"+recHeading+recTitle).postln;
			this.stop;
			0.01.wait;
			recBuf.write(recPath.fullPath++".wav", "WAV");
			this.stopRecording;
		}).play;
	}

	stopRecording {
		recSynth.free;
		recBuf.close;
		recBuf.free;
	}

	rec {|aTitle, aDur|
		if (aDur.isNil) {
			this.startRecording(aTitle)
		} {
			this.timedRecording(aTitle, aDur)
		}
	}

	srec {
		this.stopRecording;
	}

	buf {|aTitle = ""|
		var buf;
		var recording = recordings[this.key][aTitle.asSymbol];
		buf = Buffer.read(Server.default, path:(recDir +/+ this.recHeading +/+ aTitle.asString)++".wav").postln;
		if (recording.isKindOf(Buffer)) {
			recording.clear;
			recording = buf;
		};
		^buf
	}
// Symbol overwrite
	@ {|chan|
		LiveInput(this.key)
		.channelsArray_(chan)
		.source_({SoundIn.ar(chan)})
	}

}


