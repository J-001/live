StructQuant  {
	// classvar	default;
	var <>area, <>gridLevel, <>markOffset, <>beatOffset, <>timingOffset;
	// *default { ^default ?? { StructQuant.new } }
	// *default_ { |quant| default = quant.asQuant }

	*new { | area, gridLevel, markOffset, beatOffset, timingOffset = 0| 
		^super.newCopyArgs( area, gridLevel, markOffset, beatOffset, timingOffset) 
	}

	nextTimeOnGrid { | t_structure |
			^t_structure.nextTimeOnStructure(
			 area, (gridLevel ? \lowest), (markOffset ? 0), (beatOffset ? 0))
	}

	asQuant { ^this.copy }

	printOn { |stream|
		stream << "StructQuant(" << area << "," 
			<< gridLevel << "," 
		 	<< (markOffset ? 0) << "," 
		 	<< (beatOffset ? 0) << ")"
	}

	storeArgs {|stream| ^[area, gridLevel, markOffset, beatOffset] }
	
}
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
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
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
+ Symbol {
	asQuant {
		var words = this.asWords(".","_");
		words = words.collect{|word|
			word.asSymbol
		};
		^StructQuant(*words)
	}
}


