+ String { 

	*rndName {|length|
		^"abcdefghijklmnopqrstuzwxyz#!@&".scramble[0..length-1].asSymbol;
	}

	asWords {| ... aSeperators|
		var newStr = List.new;
		var wordCount = 0;
		var commaCount = 0;
		var seperators = if (aSeperators.size==0) {
				[","," ","."]
			} {
				aSeperators;
		};
		newStr.add("");
		
		this.do {|char|
			if (seperators.contains(char.asString)) {
			// if (char.isPunct or: char.isSpace) {
				// CHECK FOR CONSECUTIVE SPACES OR PUNCT
				commaCount = commaCount + 1;
				if (commaCount<=1) {
					wordCount = wordCount + 1;
					newStr.add("");
				}
			} {
				commaCount = 0;
				if (commaCount<=1) {
					newStr[wordCount] = newStr[wordCount]++char.asString;
				}
			};
		}
		^newStr.asArray;
	}

}
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
+ Symbol {

	asWords {this.asSting.asWords}

}
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
+ Function {

	isEmpty {
		var cs, string;
		cs = this.asCompileString;
		string = [];
		cs.do{|char|
			if(char.isSpace==false){
				string = string++char;
			};
		};

		if(string.size==2){
			^true
		} {
			^false
		}
	}
	
	jdAbbreviations {|longVersion|
		var dct = ();
		var shortVersion;
		
		dct = (
			//SMP CONTROLS
			\rate : \rt,
			\startPos : \sPos,
			\loop : \lp
			);

		if (dct[longVersion].isNil) {
			^shortVersion = longVersion;
		}	{
			^shortVersion = dct[longVersion.asSymbol]
		};
	}
}