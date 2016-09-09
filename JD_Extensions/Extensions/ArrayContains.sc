+ Collection {
	
	contains {|probe|

		var isMatch = false;

		this.do{|i|
			if(i==probe) {
				isMatch = true;
			};
		};
		^isMatch;
	}

	subsContain {|probe|

		var isMatch = false;

		this.do{|sub|
			sub.do{|i|
				if(i==probe) {
				isMatch = sub.contains(probe);
				};
			};
		};
		^isMatch;

	}

	indexAt {|probe|
		var index = nil;
		this.do{|i, n|
			if(i==probe) {
				index = n;
			};
		};
		^index;
	}

	subIndexAt {|probe|
		var index = nil;
		this.do{|sub, n|
			sub.do{|i|
				if(i==probe) {
					index = index ? List.new;
					index.add(n);
				};
			};
		};
		^index;
	}

	removeItem {|probe|
		var index = nil;
		this.do{|i, n|
			if(i==probe) {
				index = n;
			};
		};
		if(index.notNil,
		{this.removeAt[index]});
	}

	//-----------------------------------------------------------------------
	//-----------------------------------------------------------------------

}



+ Dictionary {

	keyAt {|probe|
		var key;
		this.asSortedArray.do{|item|
			if(probe==item[1],{
				key = item[0];
				});
		}
		^key;
	}

	deepKeyAt {|probe|
		var key;
		this.asSortedArray.do{|sub|
			sub[1].flatten.do{|i|
				if(probe==i,{
					key = sub[0];
				});
			}
		};
		^key;
	}
}