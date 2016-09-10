LiveInput : NodeProxy {
	
	var <>name;
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
				SymbolConverter.addTypeExt(ext, {|symbol|
				 	LiveInput(symbol);
				})
			}
	}

	*new {|aName, aChannelsArray|
		var instance = all[aName.asSymbol];
		
		if (instance.isNil) { 
			instance = super.new;
			instance.name = aName.asSymbol;		
			instance.channelsArray = aChannelsArray;
			instance.channelsArray !? {instance.source_({SoundIn.ar(instance.channelsArray)})};
			all[instance.name] = instance;
		} {
			if (aChannelsArray != instance.channelsArray) {
				instance.source_({SoundIn.ar(instance.channelsArray)
			};
		};

		^instance;
	}

}


