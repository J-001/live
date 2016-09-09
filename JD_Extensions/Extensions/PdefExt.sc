+ Pdef {

	//EXPERIMENTAL
	fx {|key|
		var fxName;
		var fx;

		fxName = this.key++'_fx';

		if(key.notNil) 
		{fx = Pfx(this, key)};

		fx.postln;

		Pdef(fxName.asSymbol, fx);
	}
}