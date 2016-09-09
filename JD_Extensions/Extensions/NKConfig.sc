NKConf {
	classvar<>funcs, <>keys;
	classvar<>current;
	classvar<>history;
	classvar<>index;

	*initClass {
		funcs = ();
		history = List.new;
		keys = List.new;
		current = nil;
		index = 0;
	}

	*new {|key, configFunc|	

		if(key.notNil) {
			if(configFunc.isKindOf(Function)) {
				keys.add(key);
				funcs[key] = configFunc;		
				current ?? {
					current = key;
				};		
			} {
				^funcs[key];
			};	
		} {
			^funcs[key];
		}
	}

	*fwd {
		var key;
		index = index + 1;
		key = keys.wrapAt(index);
		current = key;
		"Hovering over: "++current.asString.postln;
	}

	*back {
		var key;
		index = index - 1;
		key = keys.wrapAt(index);
		current = key;
		"Hovering over: "++current.asString.postln;
	}

	*prev {
		current = history[history.size-2];
		funcs[current].value;
	}

	*set {|confKey|
		if (confKey.notNil) {
				current = confKey;
				funcs[confKey].value;
				if(history.last!=current)
					{ history.add(current) };

			} {
				funcs[current].value;
				history.add(current);
			};
	}	
	*clear {
		this.initClass;
	}
	/*
		TO DO: Make ech function an array of strings for each key 
		dynamically set placement on board
	*/
	/**transpose {|key|
		var str = funcs[key].asCompileString;
		var num = 3;
		var test = "\\knob";
		var index = List.new;
		var charCount = 0;
		var arr = List.new;
		str.do{|char, n|
			arr.add(char);
			if(char.asString==test[charCount].asString) {
				charCount = charCount + 1;
				if (charCount==test.size) {
					index.add(n)
				};
			} {
				charCount=0;
			}
		};
		index.do{|n|

			((n-test.size+1) .. n).do{|m, i|
				arr[m] = "newt"[i];
			};
		};
		str = "";
		arr.do{|char|
			str = str++char;
		};
		str.postln;
	}*/

}