
+ NodeProxy {

	*new { | server, rate, numChannels, inputs |
		var res = super.new(server).init;
		if(rate.notNil or: { numChannels.notNil }) { res.initBus(rate, numChannels) };
		inputs.do { |o| res.add(o) };
		//JD_EXT
		res.destProxies = List.new;
		res.controlProxies = ();
		res.specDict = ();

		^res
	}

	keysValuesAsDict {
		var dict = ();
		this.getKeysValues.do{|i|
			var key = i[0];
			var val = i[1];
			dict[key] = val;
		};
		^dict;
	}

	//-----------------------------------------------------------------------
	//------------------CONTROL PROXIES--------------------------------------
	functionToProxy {|key, aSource|
		var source = aSource;
		if (source.isFunction) {
			//IF NIL MAKE A NEW ONE
			if (this.controlProxies[key].isNil) {
				this.controlProxies[key] = source = NodeProxy.control().source_(source);
			} {
				// IF NOT NIL BUT IS NEUTRAL SET THE SOURCE
				if (this.controlProxies[key].isNeutral) {
					source = this.controlProxies[key].source_(source);
				} {
					source = this.controlProxies[key].source_(source);
				};	
			};
		};
		^source;
	}
	
	prAddControlMethods {| instance, kpDct, pairs |
		//KEEP ORIGINAL ARGS FOR RESET
		pairs.pairsDo{|key, val|
			var valSource = val.source;
			var methodKey = ('k_'++key++'_').asSymbol;
			instance.addUniqueMethod(methodKey, {|self ... aArgs|
				kpDct[key].set(*aArgs);
			});
			//ADD UNIQUE METHODS TO GET/SET CONTROLS
			//GET
			instance.addUniqueMethod(key, { valSource }); 
			//SET
			instance.addUniqueMethod((key++'_').asSymbol, {|self, newVal|
				newVal !? {
					newVal.postln;
					valSource = newVal;
					if (instance.class==Symbol) { 
						instance.asN.set(key, valSource);
					} {
						instance.set(key, valSource);
					};
				};
				self; 
			});
		};
	}

	//-----------------------------------------------------------------------
	//-----------------------------------------------------------------------
	source_ {|obj|
		var hasInArg;
		this.put(nil, obj, 0);

		//CHECK FOR IN TYPE
		if (this.keysValuesAsDict[\in].notNil) {
			this.type = \in;
			if (inProxy.isNil) { inProxy = NodeProxy.audio() };
			this <<> inProxy;
		};

		this.prAddControlMethods(this, this.controlProxies, this.controlKeysValues);

		if(this.class==Ndef) {
			var symbolWithExtU = (this.key++'_N').asSymbol;
			var symbolWithExtL = (this.key++'_n').asSymbol;
			symbolWithExt.postln;
			this.prAddControlMethods( symbolWithExtU, this.controlProxies, this.controlKeysValues);
			this.prAddControlMethods( symbolWithExtL, this.controlProxies, this.controlKeysValues);
			this.prAddControlMethods( this.key, this.controlProxies, this.controlKeysValues);
		};
	}

	set { | ... aArgs | // pairs of keys or indices and value
		var args = aArgs;
		//converts Function Input to nroxy
		(args.size * 0.5).asInteger.do{|i|
			var key = args[i + 0].postln;
			var val = args[i + 1].postln;
			args[i + 1] = this.functionToProxy(key, val);
		};

		nodeMap.set(*args);
		if(this.isPlaying) {
			server.sendBundle(server.latency, [15, group.nodeID] ++ args.asOSCArgArray);
		}
	}
	
	//-------------ROUTING--------------------------------------------------
	//----------------------------------------------------------------------

	adaptInputToSelfType {|input|
		var typedInput;

		if (input.isKindOf(Symbol)) {

			if (this.class==Ndef) {
				^typedInput = Ndef(input);
			};
			if (currentEnvironment.isProxySpace) {
				if (currentEnvironment.type=='nType') {
					^typedInput = Ndef(input);
					} {
					^typedInput = currentEnvironment[input];
				};
			};
		} {
			^typedInput = input;
		};
	}

	<< { | proxy, key = \in |
		var ctl, rate, numChannels, canBeMapped;
		var input = this.adaptInputToSelfType(proxy);
		var self = this;
		// IF PROXY IS NIL
		if(input.isNil) { ^self.unmap(key) };

		self.generateUniqueName.postln;
		// IN TYPE HAS AN INPUT PROXY THAT MIXES MULTIPLE 
		// PROXIES BEFORE INPUTTING TO MATCHING
		if (self.type==\in) {
			// IF NO IN PROXY EXIST MAKE NEW ONE
			if(self.inProxy.isNil) {
			 	self.inProxy = NodeProxy.audio().fadeTime_(self.fadeTime);
			};
			self.inProxy[input.generateUniqueName.asSymbol] ?? {self.inProxy[input.generateUniqueName.asSymbol] = {input.ar} };
			// ADD THE IN_PROXY TO THE LIST OF YHE INPUT's OUTPUT's
			// SO CAN BE CLEARED 
			input.destProxies.add(self.inProxy);
			input = self.inProxy;
		};

		ctl = self.controlNames.detect { |x| x.name == key };
		rate = ctl.rate ?? {
			if(input.isNeutral) {
				if(self.isNeutral) { \audio } { self.rate }
			} {
				input.rate
			}
		};
		ctl.postln;
		input.postln;
		numChannels = ctl !? { ctl.defaultValue.asArray.size };
		numChannels.postln;
		canBeMapped = input.initBus(rate, numChannels); // warning: input should still have a fixed bus
		canBeMapped.postln;
		if(canBeMapped) {
			if(self.isNeutral) { self.defineBus(rate, numChannels) };
			self.xmap(key, input);
		} {
			"Could not link node proxies, no matching input found.".warn
		};
		
		^self // returns first argument for further chaining
	}

	>> { | proxy, key = \in |

		if(proxy.isKindOf(Symbol)) {
			proxy = Ndef(proxy);
		};
		proxy.perform('<<', this, key);
	}

	rm {|aProxy|
		var key;
		var func = {|proxy|
			proxy.postln;
			proxy = this.adaptInputToSelfType(proxy);

			proxy.postln;
			proxy !? {key = proxy.generateUniqueName.asSymbol};
			key.postln;
			this.inProxy[key] = nil;
		};

		if (aProxy.isArray) {
			aProxy.do{|proxy|
				func.(proxy);
			};
			^this;
		};

		func.(aProxy);

	}
	//-----------------------------------------------------------------------
	//----------------------PLAY CONTROLS------------------------------------
	rel { | releaseTime = 1 |
		this.set(\rel, releaseTime,\done,0,\gate,0);
	}
	trig { | attackTime = 0.01 |
		this.set(\atk, attackTime,\gate,1,\done,2);

	}

	clear { |fadeTime|

		// CLEAR SLOT IN DESTINITAION PROXY
		this.destProxies.do{|destProxy|
			this.generateUniqueName.asSymbol.postln;
			destProxy[this.generateUniqueName.asSymbol] = nil;
		};
		//CLEAR SPECS
		this.clearSpecs(fadeTime);
		//CLEAR IN PROXY
		this.inProxy !? {"IN PROXY CLEARED".postln; this.inProxy.clear(fadeTime) };
		//clear variants
		this.variants.clear;
		//controlProxies
		this.controlProxies.do(_.clear(fadeTime));
		this.removeUniqueMethods;
		(this.key++'_N').asSymbol.removeUniqueMethods;
		(this.key++'_n').asSymbol.removeUniqueMethods;

		//FROM ORIGINAL CLASS DEFINITION
		this.free(fadeTime, true); 	// free group and objects
		this.removeAll; 			// take out all objects
		children = nil;             // for now: also remove children
		this.stop(fadeTime, true);		// stop any monitor
		monitor = nil;
		this.fadeTime = fadeTime; // set the fadeTime one last time for freeBus
		this.freeBus;	 // free the bus from the server allocator
		this.init;	// reset the environment
		this.changed(\clear, [fadeTime]);
	}

	p { this.play }
	s { this.stop }
	c {|fadeTime| this.clear(fadeTime) }
	r{ this.rel }
	t { this.trig }

	q_ {|aQuant| this.quant = aQuant;^this}
	q { ^this.quant}
	cl_ {|aClock| this.clock = aClock; ^this}
	cl { ^this.clock}

	//-----------------------------------------------------------------------
	//-----------------------------------------------------------------------
	// Specified Proxies
	sm {| key, val, aSpec |
		/*
		TO DO: POSSIBLE INPUT AS ARRAY
		*/
		this.specMap(key, val, aSpec)
	}
	specMap {|key, val, aSpec|
		var spec;
		/*
			If key is a Spec use that;
			If key is not a spec use default control spec;
			Is aSpec is a spec use that;
			if aSpec is an array make it a spec
		*/
		(aSpec==key).or(aSpec.isNil).if(
			{
				(key.asSpec.isNil).if(
					{spec = nil.asSpec},
					{spec = key.asSpec})
			}, {
				(aSpec.isKindOf(Spec)).if(
					{spec = aSpec},
					{spec = aSpec.asSpec})
			});

		if(val.isKindOf(NodeProxy)) {
			val.specify(spec);
			^this.set(key, val.specDict[spec]);
		};

		^this.set(key, spec.map(val));
	}

	specify {|aSpec|
		var specifiedProxy;
		var spec;
		if (aSpec.isNil, {"Where's the spec?".postln; ^this});
		spec = aSpec.asSpec;

		if (this.specDict.at(spec).isNil) {
			"NO PROXY FOR THIS SPEC\nMAKING NEW SPEC PROXY".postln;

			specifiedProxy = NodeProxy.control(numChannels: this.numChannels);
			specifiedProxy.source_({spec.map(this.kr)});

			^this.specDict[spec] = specifiedProxy; 
		} {
				"SPEC ALREADY EXISTS\nRETURNING EXISTING SPEC".postln;
				specifiedProxy = this.specDict[spec];

				if (specifiedProxy.isNeutral) {
					"RESETTING SOURCE".postln;
					specifiedProxy.source_({spec.map(this.kr)});
					this.specDict[spec] = specifiedProxy;
					};
		};
		^specifiedProxy;
	}
	//IF I WANT TO PRIME ULTIPLES CONTROLS FOR USE
	specifyN {|aSpecs|
		aSpecs.do{|aSpec|
			this.specify(aSpec);
		}
	}

	clearSpecs {|fadeTime = 0.2|
		this.specDict.do{|i|
			i.postln;
			i.clear(fadeTime);
		}
	}
	//-----------------------------------------------------------------------
	//----------------------VARIANTS-----------------------------------------
	variant_ {|key ... kvPairs|
		key.postln;
		kvPairs.postln;
		if(kvPairs.size%2!=1) {
			^"key value pairs please!!!";
		};
		this.variants[key] = kvPairs;
	}

	variant {|key|
			this.set(*variants[key])
	}
	//--------------------REALTIME SHORTHAND---------------------------------
	//-----------------------------------------------------------------------
	//SourceSetting
	@ {|obj, at = 0| 
		var index;
		index = at;
		if (obj.isKindOf(Symbol), {
				^this.nd[index] = Ndef(obj);
			});
		if(obj.asCompileString.contains("|in")) {
			if(at==0) {
				index = this.nd.objects.size;
			};
			this.nd.filter( index, obj);
			^this.nd[index]
		};

		this[index] = obj;
	}
	//-----------------------------------------------------------------------
	//-----------------------------------------------------------------------
	// COPY WITH EXTENSION
	|+ {|extension, attachPoint = 'front'|
		/* INCOMPLETE */
		var self = this;
		var func = {|self|
			if (attachPoint=='back') {
				^self.copy(self.key++extension.asSymbol)
			};
			if (attachPoint=='front') {
				^self.copy(extension.asSymbol++self.key)
			};
		};

		if(this.isArray) {
			this.do{|self|
				func.(self)
			}
		} {
			func.(this);
		};
		
	}
	// COPY WITHOUT EXTENSION
	| {|newName, attachPoint = 'front'|
		var self = this;
		^self.copy(newName.asSymbol)
	} 
	//-----------------------------------------------------------------------
	//---------ARG SETTING---------------------------------------------------
	ust {|...setArgs|
		if (setArgs.size==0) {
			this.controlKeys.do{|controlKey|
				this.unset(controlKey)
			}
		} {
			this.unset(setArgs)
		}
	}
	//-----------------------------------------------------------------------
	//-----------------------------------------------------------------------
	// FOR INHERITED CLASSES CONTAINING KEYS
	//from SynthDef of same name
	fsd {|aVariant|
		var sdVariant = aVariant;
		if (sdVariant.notNil) {
			^this.source_(this.key)
			} {
			^this.source_(this.key++'.'++sdVariant.asSymbol)
		};
	}
	//from Pdef of same name
	fpd {
		^this.source_( Pdef(this.key) )
	}

}


//-----------------------------------------------------------------------
//-----------------------------------------------------------------------

+ Ndef {

	persist {|bool|
		bool.if({
			ServerTree.put({this.play}, this.key);
			}, {
				if(ServerTree.dict.matchAt(this.key).notNil, {
					ServerTree.remove(this.key);
				});
			});
	}
	
}

//SYMBOL NDEF SHORTHAND
+ Symbol {

	checkExt {
		var str = this.asString;
		var strlen = str.size;
		var ext = str[strlen-2 .. strlen-1];
		var root = str[0 .. strlen - 3].asSymbol;
		strlen.postln;
		if (ext=="_P" or: ext=="_p") {
			^root.asP.postln;
		};
		if (ext=="_N" or: ext=="_n") {
			^root.asN.postln;
		};
		if (ext=="_X" or: ext=="_x") {
			^currentEnvironment[root].postln;
		};
		^nil;
	}	

	selfType {
		//default 
		var self = Ndef(this);

		if (this.checkExt.notNil) {
			^this.checkExt;
		};

		if (currentEnvironment.isProxySpace) {
			if (currentEnvironment.type == 'nType') {
				"ntype".postln;
				self = Ndef(this);
			};
			if (currentEnvironment.type == 'pType'){
				"ptype".postln;
				self = currentEnvironment[this];
			};
		};
		^self;
	}
//-----------------------------------------------------------------------
//-----------------ROUTING-----------------------------------------------
	//RETURN INPUT
	<<> {| proxy, key = \in |
		var self = this.selfType;
		self.perform('<<>', self, key)
	}

	<>> {| proxy, key = \in |
		var self = this.selfType;
		self.perform('<>>', self, key)
	}
	//RETURN ORIGINAL
	<< { | proxy, key = \in |
		var self = this.selfType;
		self.perform('<<', self, key)
	}

	>> { | proxy, key = \in |
		var self = this.selfType;
		self.perform('>>', self,  key)
	}

	rm {|aProxy|
		var self = this.selfType;
		self.rm(aProxy)
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
		}
		self.perform('@', obj, at)
	} 
	//Copy
	|+ {|extension, attachPoint = 'front'|
		var self = this.selfType;
		self.perform('|', attachPoint, extension) 
	} 
	| {|newName, attachPoint = 'front'|
		var self = this.selfType;
		self.perform('|', attachPoint, newName) 
	} 

//-----------------------------------------------------------------------
//-----------------SET---------------------------------------------------
	ust {|...setArgs|
		var self = this.selfType;
		self.unset(setArgs)
	}
	st {|...setArgs|
		var self = this.selfType;
		self.set(setArgs)
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
	asN { ^Ndef(this) }
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
		this.asP.perform('<>', aPattern)
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
	

}
//-----------------------------------------------------------------------
//-------------------PATTERNS--------------------------------------------

+ Pdef {

	convertSymbol {|input|
		if (input.isKindOf(Symbol)) {
			^Pdef(input)
		} {
			^input;
		}
	}
	convertSelfFromSymbol {
		if (this.class.isKindOf(Symbol)) {
			^Pdef(this)
		} {
			^this;
		}
	}
	// ShortHand
	p {|clock| this.play(clock) }
	s { this.stop }
	c {|fadeTime| this.clear(fadeTime) }
	r{ this.rel }
	t { this.trig }

	q_ {|aQuant| this.quant = aQuant;^this}
	q { ^this.quant}
	cl_ {|aClock| this.clock = aClock; ^this}
	cl { ^this.clock}
	//-----------------------------------------------------------------------
	//-----------------------------------------------------------------------
	// Copy w/ extension
	|+ {|extension, attachPoint = 'front'|
		if (attachPoint=='front') {
			^this.copy(extension.asSymbol ++ this.key)
		} {
			^this.copy(this.key ++ extension.asSymbol)
		};
	}
	//copy to new name
	| {|newCopyName|
		newCopyName.asSymbol.postln;
		^this.copy(newCopyName.asSymbol)
	} 

	//-----------------------------------------------------------------------
	//-----------------------------------------------------------------------
	//c = a <> b; //b overrides a
	//CHAIN
	<> {| aPattern|
		var pattern = this.convertSymbol(aPattern);
		var self = this.convertSelfFromSymbol;
		^Pchain(pattern, self);
	}
	// b <+ a; // b overrides a; return b;
	<+ {|aPattern|
		var pattern = this.convertSymbol(aPattern);
		var self = this.convertSelfFromSymbol;
		self.source_(Pchain(pattern.postln, self.postln));
		^self
	}
	// b +> a; // a overrides b; return a;
	+> {|aPattern|
		var pattern = this.convertSymbol(aPattern);
		var self = this.convertSelfFromSymbol;
		^pattern.perform('<+', self)
	}
	//-----------------------------------------------------------------------
	//-----------------------------------------------------------------------
	//set source
	// b ?< a; // a source of b; return b
	?< {|aPattern|
		var pattern = this.convertSymbol(aPattern);
		var self = this.convertSelfFromSymbol;
		self.source_(pattern);
		^self
	}
	// b >? a; // b source of b; return b
	>? {|aPattern|
		var pattern = this.convertSymbol(aPattern);
		var self = this.convertSelfFromSymbol;
		pattern.source_(self);
		pattern.postln; self.postln;
		^pattern
	}
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
	//SET
	st {|... kvPairs|
		this.source.set(*kvPairs)
	}
	//RESET
	rst {|... keys|
		this.source.set(*keys)
	}
	//Unset for pattern same as reset
	ust {|... keys|
		this.source.set(*keys)
	}
	//SET ARG
	@! {|aPairs, aControl|
		this.source.perform('@!', aPairs, aControl)
	}
	@ {|aPairs, aControl|
		this.source.perform('@!', aPairs, aControl)
	}
//-----------------------------------------------------------------------
//----------------PbindProxy-Access--------------------------------------
	
	*new { arg key, item;
		var res = this.at(key);
		if(res.isNil) {
			res = super.new(item).prAdd(key);
			if (item.class==PbindProxy) {
				item.prAddControlMethods(res, item.controlProxies, item.pairs);
				item.prAddControlMethods((key).asSymbol, item.controlProxies, item.pairs);
			}
		} {
			if(item.notNil) { 
				res.source = item; 
				if (item.class==PbindProxy) {
					item.prAddControlMethods(res, item.controlProxies, item.pairs);
					item.prAddControlMethods((key).asSymbol, item.controlProxies, item.pairs);
				}
			}
		}
		^res

	}

	clear{
		this.removeUniqueMethods;
		this.key.removeUniqueMethods;
		super.clear
	}

	at {|key|
		this.source.at(key)
	}
}


+ PbindProxy {

	functionToProxy {|key, aSource|
		var source = aSource;
		if (source.isFunction) {
			//IF NIL MAKE A NEW ONE
			if (this.controlProxies[key].isNil) {
				this.controlProxies[key] = source = NodeProxy.control().source_(source);
			} {
				// IF NOT NIL BUT IS NEUTRAL SET THE SOURCE
				if (this.controlProxies[key].isNeutral) {
					source = this.controlProxies[key].source_(source);
				} {
					source = this.controlProxies[key].source_(source);
				};	
			};
		};
		^source;
	}

	prStoreOriginalArgs{|pairs|
		var dctPairs = ();
		pairs.pairsDo{|key, val|
			var valSource = val.source;
			dctPairs[key] = valSource;
		};
		this.originalArgs = dctPairs;
	}

	/*
	for use in pdef instantiation to be able to use the unique methods 
	of the source pattern;
	*/
	prAddControlMethods{| instance, kpDct, pairs |
		//KEEP ORIGINAL ARGS FOR RESET
		pairs.pairsDo{|key, val|
			var valSource = val.source;
			var methodKey = ('k_'++key++'_').asSymbol;
			instance.addUniqueMethod(methodKey, {|self ... aArgs|
				
				kpDct[key].set(*aArgs);
			});
			//ADD UNIQUE METHODS TO GET/SET CONTROLS
			//GET
			instance.addUniqueMethod(key, { valSource }); 
			//SET
			instance.addUniqueMethod((key++'_').asSymbol, {|self, newVal|
				newVal !? {
					newVal.postln;
					valSource = newVal;
					if (instance.class==Symbol) { 
						instance.asP.set(key, valSource);
					} {
						instance.set(key, valSource);
					};
				};
				self; 
			});
		};
	}

	init {
		//Store in this dict original args for unset
		var dctPairs = ();
		this.controlProxies = ();
		forBy(0, pairs.size-1, 2) { arg i;
			var proxy = PatternProxy.new;
			var source = pairs[i + 1];

			source = this.functionToProxy(pairs[i], source);
			
			proxy.setSource(source);
			pairs[i+1] = proxy
		};
		source = EventPatternProxy(Pbind(*pairs));

		this.prAddControlMethods(this, pairs);
		this.prStoreOriginalArgs(pairs);
	}

	clear {|fadeTime=0.1|
		this.removeUniqueMethods;
		originalArgs = ();
		controlProxies.do(_.clear(fadeTime));
		pairs = [];
		source.clear;
	}
//-----------------------------------------------------------------------
//----------------SETTING------------------------------------------------

	set { arg ... args; // key, val ...
		var changedPairs = false, quant;
		quant = this.quant;
		args.pairsDo { |key, val|
			var i, remove;
			i = this.find(key);
			if(i.notNil)
			{
				if(val.isNil) {
					pairs.removeAt(i);
					pairs.removeAt(i);
					changedPairs = true;
				}{
					var source = this.functionToProxy(key, val.source);
					val.source.postln;
					pairs[i+1].quant_(quant).setSource(source)
				};
			}{
				pairs = pairs ++ [key, PatternProxy.new.quant_(quant).setSource(val)];
				changedPairs = true;
			};

		};
		if(changedPairs) { source.source =  Pbind(*pairs) };

	}

	rst {|...keys|
		keys.do{|key|
			this.set(this.originalArgs[key]);
		}
	}

	@! {|aPairs, control|
		var pairs = aPairs;

		control !? { this.perform(control, aPairs); ^this };
		// Impractical
		if (pairs.isKindOf(Symbol) or: pairs.isString) {
			pairs = pairs.asString.asWords("|");
			pairs = pairs.collect{ |word|
				if (word%2==0) {
					word.interpret.postln;
				}
			};
		}; 
		this.set(*pairs);
	}

}

//-----------------------------------------------------------------------
//------------------PROXY-SPACE------------------------------------------
+ Environment {
	isProxySpace {^ false}
}

+ ProxySpace {
	/*
	interpretation of symbols
	types:
		xType -> proxy
		nType -> Ndef
		pType -> Pdef /*AWAITING IMPLEMENTING*/
	*/
	*new { | server, name, clock, type = 'xType' |
		^super.new.init(server ? Server.default, name, clock, type)
	}

	init { | argServer, argName, argClock , argType|
		server = argServer;
		clock = argClock;
		this.name = argName;
		if(clock.notNil) { this.quant = 1.0 };

		this.type = argType;
	}

	isProxySpace { ^true }
}