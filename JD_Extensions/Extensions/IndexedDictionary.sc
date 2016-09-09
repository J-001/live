+ Dictionary {

	at {|key|
		if (key.isInteger){
			^this.arr.at(key);
		};
			^array.at(this.scanFor(key) + 1)
	}

	arr {
		^this.collectAs({|i|i}, Array)
	}

	- {arg keyString;

		var pnts;
		var new;

		pnts=List();
		pnts.add(0);

		keyString.asString.do{|letter,i|
			if(letter.isPunct) {
				pnts.add(i);
			};
		};
		pnts.add(keyString.asString.size-1);

		 new = pnts[0..pnts.size-2].collect {|ind,i|
			var ind0 = pnts[i];
			var ind1 = pnts[i+1];
			var arr = keyString.asString;

			if (i==0) {
				// [ind0,ind1-1].postln;
				arr = arr[ind0..ind1-1];
			} {
				if (i==(pnts.size-2)) {
				// [ind0+1,ind1].postln;
				arr = arr[ind0+1..ind1];

				} {
					// [ind0+1,ind1-1].postln;
					arr = arr[ind0+1..ind1-1];

				};
			};
			arr;
		};

		new = new.collect{|i|
			i.postln;
			this.at(i.asSymbol);
		};

		new = new.collect{|i|
			i.collectAs({|j|j},Array)
		};
		new = new.flatten;
		^new;
	}

	filenames {
		this.do{|i|
			PathName(i.path).fileNameWithoutExtension.postln;
		}
	}

}