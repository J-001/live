StructQuant  {
	// classvar	default;
	var<>gridLevel, <>markOffset, <>beatOffset, <>timingOffset;
	// *default { ^default ?? { StructQuant.new } }
	// *default_ { |quant| default = quant.asQuant }

	*new { | gridLevel, markOffset, beatOffset, timingOffset = 0| 
		^super.newCopyArgs( gridLevel, markOffset, beatOffset, timingOffset) 
	}

	nextTimeOnGrid { | t_structure |
			^t_structure.nextTimeOnStructure(
			 (gridLevel ? \lowest), (markOffset ? 0), (beatOffset ? 0))
	}

	asQuant { ^this.copy 
}
	printOn { |stream|
		stream << "StructQuant(" << gridLevel << "," 
		 	<< markOffset << "," 
		 	<< beatOffset << ")"
	}

	storeArgs {|stream| ^[ gridLevel, markOffset, beatOffset] }
	
}
 + SequenceableCollection {

	asQuant { 
		if (this.at(0).class == Symbol){
			^StructQuant(*this);
		};
		if (this.at(0).class == TimeStructure){
			^StructQuant(*(this[1..this.size-1]));
		};
			^Quant(*this) 
	}
}


+ Symbol {
	asQuant {
		^StructQuant(this)
	}
}


