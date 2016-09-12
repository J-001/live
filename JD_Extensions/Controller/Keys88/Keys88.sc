Keys88 {
	*new {
		^super.new.init;
	}

	init {

		if(MIDIClient.initialised==false,{MIDIClient.init});

		MIDIClient.sources.do{|source,n|
			source.do{|src|
				if(source.device=="Keystation 88",{
					"Keystation 88 connected".postln;
					MIDIIn.connect(n);
					});
			}
		};
	}
}