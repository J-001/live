StructQuant : Quant {
	classvar	default;
	var <>quant, <>phase, <>timingOffset, <>timeStructure;

	*default { ^default ?? { StructQuant.new } }
	*default_ { |quant| default = quant.asQuant }

	*new { |quant, phase, timingOffset, timeStructure| 
		^super.newCopyArgs(quant, phase, timingOffset)
		.timeStructure_(timeStructure) 
	}

	nextTimeOnGrid { | clock |
		^clock.nextTimeOnGrid(timeStructure.nextTimeOnGrid , 0);
	}

	asQuant { ^this.copy }

	printOn { |stream|
		stream << "StructQuant(" << quant;
		if(phase.notNil) { stream << ", " << phase };
		if(timingOffset.notNil) {
			stream << ", ";
			if(phase.isNil) {
				stream << "nil, ";
			};
			stream << timingOffset
		};
		stream << ")"
	}

	storeArgs { ^[quant, phase, timingOffset] }
	
}


 + SequenceableCollection {

	asQuant { 
		if (this.at(0).class == TimeStructure){
			^StructQuant(*(this[1..this.size-1])).timeStructure_(this.at(0));
		};
			^Quant(*this) 
	}

}


