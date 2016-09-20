StructQuant  {
	// classvar	default;
	var <>timeStructure, <>gridLevel, <>markOffset, <>beatOffset, <>timingOffset;
	// *default { ^default ?? { StructQuant.new } }
	// *default_ { |quant| default = quant.asQuant }

	*new { |timeStructure, gridLevel, markOffset, beatOffset, timingOffset| 
		^super.newCopyArgs(timeStructure, gridLevel, markOffset, beatOffset, timingOffset) 
	}

	nextTimeOnGrid { | tStructure |
		var clock = tStructure.asClock.postln;
		"Here third".postln;
		tStructure.postln;
		^clock.nextTimeOnGrid(
			tStructure.nextTime( (gridLevel ? \lowest), (markOffset ? 0), (beatOffset ? 0)),
				0
			).postln;
	}

	asQuant { ^this.copy }

	printOn { |stream|
		stream << "StructQuant(" << timeStructure << "," 
		 	<< gridLevel << "," 
		 	<< markOffset << "," 
		 	<< beatOffset << ")"
	}

	storeArgs {|stream| ^[timeStructure, gridLevel, markOffset, beatOffset] }
	
}


 + SequenceableCollection {

	asQuant { 
		if (this.at(0).class == TimeStructure){
			^StructQuant(*this);
		};
			^Quant(*this) 
	}
}


