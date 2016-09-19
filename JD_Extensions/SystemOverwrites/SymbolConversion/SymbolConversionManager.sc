SymbolConverter {

	classvar <>extConvFuncs; //Used by Symbol
	classvar <>defaultConvFunc;// Default Ndef
	classvar <>inputConvFuncs;
	classvar <>methodConvFunc;

	*initClass { 
		extConvFuncs = ();
		inputConvFuncs = ();
		methodConvFunc = ();
		defaultConvFunc = {|symbol| Ndef(symbol) };

		//SymbolCallback - Doesn't work in Pdef for some reason
		['_P','_p'].do{|ext|
			SymbolConverter.addExtConvFunc(ext, {|symbol|
			 	Pdef(symbol);
			})
		};
	}

	*addExtConvFunc {|aType, aFunc|
		extConvFuncs[aType] = aFunc;
	}

	*addinputConvFunc {|aType, aFunc|
		inputConvFuncs[aType] = aFunc;
	}

	*addMethodConvFunc{|aMethod, aFunc|
		methodConvFunc[aMethod] = aFunc;
	}
	
}

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
+ Symbol {

	jdAbbreviations {
		var dct = ();
		var shortVersion;
		
		dct = (
			//SMP CONTROLS
			\rate : \rt,
			\startPos : \sPos,
			\loop : \lp
			);

		if (dct[this].isNil) {
			^shortVersion = this;
		}	{
			^shortVersion = dct[this.asSymbol]
		};
	}
	//-----------------------------------------------------------------------
	//-----------------------------------------------------------------------
	
	checkExt {
		SymbolConverter.extConvFuncs.pairsDo{| key, typeWrapFunc| 
			var str = this.asString;
			var strlen = str.size;
			var ext = str[strlen-2 .. strlen-1];
			var root = str[0 .. strlen - 3].asSymbol;

			if (ext.asSymbol == key.asSymbol) {
				^typeWrapFunc.(root);
			};
		};
		^nil
	}

	selfType {
		//default 
		var self = Ndef(this);

		if (this.checkExt.notNil) {
			^this.checkExt.postln;
		};

		^self;
	}

	checkInput {|method ... input|

		var funcs = SymbolConverter.inputConvFuncs[method.asSymbol];

		if( funcs.isNil) {^nil};

		if (funcs[\test].(*input).postln) {
			var str = this.asString;
			var strlen = str.size;
			var root = str[0 .. strlen - 3].asSymbol;
			funcs[\typewrap].(root)
		};

		^nil
	}

	postSome{
		"Symbol".postln;
	}
//-----------------------------------------------------------------------
//-----------------ROUTING-----------------------------------------------
	//RETURN INPUT
	<<> {| proxy, key = \in |
		var self = this.selfType;
		self.perform('<<>', proxy, key)
	}

	<>> {| proxy, key = \in |
		var self = this.selfType;
		self.perform('<>>', proxy, key)
	}
	//RETURN ORIGINAL
	<< { | proxy, key = \in |
		var self = this.selfType;
		self.perform('<<', proxy, key)
	}

	>> { | proxy, key = \in |
		var self = this.selfType;
		proxy.perform('>>', self,  key)
	}

	rm {|fadeTime ... proxies|
		var self = this.selfType;
		self.rm(fadeTime, *proxies)
	}
//--------------NODE CONTROLS--------------------------------------------
//-----------------------------------------------------------------------
	rel { | releaseTime = 1 |
		var self = this.selfType;
		self.rel(releaseTime ) 
	}

	trig { | attackTime = 0.01 |
		var self = this.selfType;
		self.trig( attackTime ) 
	}

	clear { |fadeTime|
		var self = this.selfType;
		self.clear( fadeTime ) 
	}
//-----------------------------------------------------------------------
//---------------SPEC PROXIES--------------------------------------------
	//shorthand - specMap
	sm {|key, val, aSpec|
		var self = this.selfType;
		self.specMap(key, val, aSpec) 
	}

	specMap {|key, val, aSpec|
		var self = this.selfType;
		self.specMap(key, val, aSpec) 
	}
	clearSpecs {|fadeTime = 0.2|
		var self = this.selfType;
		self.clearSpecs(fadeTime) 
	}

	specify {|aSpec|
		var self = this.selfType;
		self.specify(aSpec) 
	}

	specifyN {|aSpecs|
		var self = this.selfType;
		self.specifyN(aSpecs) 
	}
//-----------------------------------------------------------------------
//------------------VARIANTS---------------------------------------------
	variant_ {|key ... kvPairs|
		var self = this.selfType;
		self.variant_(key, *kvPairs) 
	}

	variant {|key|
		var self = this.selfType;
		self.variant(key) 
	}
	//
	@ {|obj, at = 0|  
		var self = this.selfType;
		if (obj.isArray) {
			self = this.asP;
		};
		self =  this.checkInput('@', obj, at) ? self;	
		self.postln;
		self.perform('@', obj, at)
	} 
	//Copy
	|+ {|extension, attachPoint = 'front'|
		var self = this.selfType;
		^self.perform('|+', attachPoint, extension) 
	} 
	| {|newName|
		var self = this.selfType;
		^self.perform('|', newName)
	} 

//-----------------------------------------------------------------------
//-----------------SET---------------------------------------------------
	ust {|...setArgs|
		var self = this.selfType;
		self.unset(setArgs)
	}
	set {|...setArgs|
		var self = this.selfType;
		self.set(*setArgs)
	}

//-----------------------------------------------------------------------
//---------------PLAY CONTROLS-------------------------------------------
	//PLAY
	play { 
		var self = this.selfType;
		self.play;
		^self;
	}
	p {  this.play }

	stop { 
		var self = this.selfType;
		self.stop;
		^self;
	}
	s { this.stop }

	clear {|fadeTime=0.1| 
		var self = this.selfType;
		self.clear(fadeTime);
		^self;
	}
	c {|fadeTime| this.clear(fadeTime) }

	rel {
		var self = this.selfType;
		self.rel;
		^self;
	}
	r {this.rel}

	trig {
		var self = this.selfType;
		self.trig;
		^self;
	}
	tr { this.trig }

	q_ {|aQuant| 
		var self = this.selfType;
		self.quant = aQuant;
		^self;
	}// ShortHand

	q { 
		var self = this.selfType;
		^self.quant;
	}// ShortHand

	cl_ {|aClock|
		var self = this.selfType;
	 	self.clock = aClock;
	 	^self;
	}// ShortHand

	cl {
		var self = this.selfType;
	 	^self.clock
	}// ShortHand

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
	asN { 
		var self = this.selfType;
		^self;
	}

	asL {
		^LiveInput(this)
	}
//-----------------------------------------------------------------------
//-------------------------PATTERN STUFF---------------------------------
	asP_ {|obj|
		obj !? {Pdef(this, obj)};
		^Pdef(this)
	}
	asP { ^Pdef(this) }

	pat { ^Pdef(this) }

	//-----------------------------------------------------------------------
	//-----------------------------------------------------------------------
	// Chain
	<> {|aPattern|
		^this.asP.perform('<>', aPattern)
	}
	>< {|aPattern|
		^this.asP.perform('><', aPattern)
	}
	<+ {|aPattern|
		this.asP.perform('<+', aPattern)
	}
	+> {|aPattern|
		this.asP.perform('+>', aPattern)
	}
	//Set Source
	?< {|aPattern|
		this.asP.perform('?<', aPattern)
	}
	>? {|aPattern|
		this.asP.perform('>?', aPattern)
	}
	
	|? {|funcKeyPair|
		^this.asP.perform('|?', funcKeyPair)
	}

	//-----------------------------------------------------------------------
	//-----------------------------------------------------------------------
	//Fdef

}