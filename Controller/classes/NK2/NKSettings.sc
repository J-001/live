NKSettings {

	var<> faders;
	var<>knobs;

	*new {
		^super.new.init;
	}
	init {
		faders=(a1:1,a2:2,a3:3);
	}

	at {|sym|
		^faders.at(sym);
	}
}

