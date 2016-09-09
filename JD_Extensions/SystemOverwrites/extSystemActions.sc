+ ServerTree {

    *initClass{
    this.dict = ();
    }
	*init {
		/*Dictionar in MainFunction*/
		^super.init;
	}

	*add {|object,key|

		if (this.objects.isNil,{this.init});

		if (key.notNil,{
			var sKey=key.asSymbol;
			this.dict.add(sKey->object);
			this.dict[sKey].postln;
			^super.add(this.dict[sKey]);
		}, {
			^super.add(object)
		})
	}

    *remove {|name|
        super.remove(this.dict[name]);
        this.dict.removeAt(name);
    }

	*replace {|newFunc,key|
		var sKey = key.asSymbol;
		this.dict[sKey].postln;
		this.remove(this.dict[sKey]);
		this.dict[sKey]=newFunc;
		this.add(this.dict[sKey])
	}

	*put {|newFunc,key|

		if(key.notNil,{
			var sKey = key.asSymbol;
			if (this.dict[sKey].notNil,
				{this.replace(newFunc,sKey)},
				{this.add(newFunc,sKey)})
			});
	}

}

