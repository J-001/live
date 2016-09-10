LiveInput : Ndef {
	
	var <>channelsArray;
	var <>recordingDestinationRoot;
	var <>recordings;

	classvar <>recDir;
	classvar <>all;

	* initClass {
		all = ();
		recDir = "~/Live/rec".standardizePath;

		//ADD CALLBACK TO BE USED INSIDE SYMBOL CONVERTER
		['_L','_l'].do{|ext|
			SymbolConverter.addExtConvFunc(ext, {|symbol|
			 	LiveInput(symbol);
			})
		}
	}

	*new {|aName, aChannelsArray|
		var instance = all[aName.asSymbol];
		
		if (instance.isNil) { 
			instance = super.new(aName);	
			instance.channelsArray = aChannelsArray;
			instance.channelsArray !? {
				instance.source = {|amp = 1| SoundIn.ar(instance.channelsArray) * amp.lag(0.05)}
			};
			all[instance.key] = instance;
		} {
			if (aChannelsArray != instance.channelsArray) {
				instance.source_({SoundIn.ar(instance.channelsArray)})
			};
		};

		^instance;
	}

}


