
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
		instance.postln;
		//KEEP ORIGINAL ARGS FOR RESET
		pairs.pairsDo{|aKey, val|
			var valSource = val;
			var key = aKey.jdAbbreviations;
			var methodKey = ('k_'++key++'_').asSymbol;
			instance.addUniqueMethod(methodKey, {|self ... aArgs|
				kpDct[key].set(*aArgs);
			});
			//ADD UNIQUE METHODS TO GET/SET CONTROLS
			//GET
			((key++'_').asString+"Getter");
			instance.addUniqueMethod(key, { valSource }); 
			//SET
			((key++'_').asString+"Setter");
			instance.addUniqueMethod((key++'_').asSymbol, {|self, newVal|
				newVal !? {
					// newVal.postln;
					valSource = newVal;
					if (instance.class==Symbol) { 
						Ndef(instance).set(key, valSource);
					} {
						// instance.class.postln;
						instance.set(key, valSource);
					};
				};
				self; 
			});
		};
	}
	//Adds All node proxy Methods To Symbol
	methodsToSymbol{
		NodeProxy.methods.do{|method|
			// this.key.postln;
			// method.name.asSymbol.postln;
			this.key.addUniqueMethod(method.name.asSymbol, {|self ... args|
				// method.name.postln;
				// args.postln;
				this.perform(method.name.asSymbol, *args);
				});
		}
	}

	uniqueMethodsFromTemplate{|template|
		var kvControls = template;
		// template.postln;
		this.prAddControlMethods(this.key, this.controlProxies, kvControls)
	}

	umt {|template|
		var kvControls = template;
		// template.postln;
		this.prAddControlMethods(this.key, this.controlProxies, kvControls)
	}

	//-----------------------------------------------------------------------
	//-----------------------------------------------------------------------
	source_ {|obj|
		var hasInArg;

		if (obj.isKindOf(Pattern) or: obj.isKindOf(PatternProxy)) {
			obj.quant_(this.quant);
		};
		this.put(nil, obj, 0);

		//CHECK FOR IN TYPE
		if (this.keysValuesAsDict[\pre_in].notNil) {
			this.type = \pre_in;
			if (inProxy.isNil) { inProxy = NodeProxy.audio() };
			this <<> .pre_in inProxy;
		};
		//this
		this.prAddControlMethods(this, this.controlProxies, this.controlKeysValues);

		if(this.class == Ndef) {
			var symbolWithExtU = (this.key++'_N').asSymbol;
			var symbolWithExtL = (this.key++'_n').asSymbol;
			var controlKVPairs;

			if (obj.isKindOf(EventPatternProxy)) {
				obj = obj.source;
			};

			if(obj.class == Pchain) {
				controlKVPairs = List.new;

				obj.patterns.reverse.do{|pattern, n|
					var pairs;

					if (pattern.isKindOf(EventPatternProxy)) {
						pattern = pattern.source;
					};

					if (pattern.isKindOf(PbindProxy)) {
						pairs = pattern.pairs;
					};
					if (pattern.isKindOf(Pbind) or: pattern.isKindOf(Pmono)) {
						pairs = pattern.patternpairs;
					};

					pairs.pairsDo{|key, val|
						if (controlKVPairs.contains(key).not) {
								
								controlKVPairs.add(key);
								controlKVPairs.add(val);
							} {
								var i;
								i = controlKVPairs.array.find([key]);
								controlKVPairs.array[i + 1] = val;
						}
					}
				}
			};

			if (obj.isKindOf(PbindProxy)) {
				controlKVPairs = obj.pairs;
			};
			if (obj.isKindOf(Pbind) or: obj.isKindOf(Pmono)) {
				controlKVPairs = obj.patternpairs;
			};
			if (obj.isFunction) {
				controlKVPairs = this.controlProxies;
			};
			//removeUniqueMethods
			this.key.removeUniqueMethods;
			symbolWithExtU.removeUniqueMethods;
			symbolWithExtL.removeUniqueMethods;
			// Add Methods of new source
			this.prAddControlMethods( symbolWithExtU, this.controlProxies, controlKVPairs);
			this.prAddControlMethods( symbolWithExtL, this.controlProxies, controlKVPairs);
			this.prAddControlMethods( this.key, this.controlProxies, controlKVPairs);

			this.methodsToSymbol;
			
		};
	}

	set { | ... aArgs | // pairs of keys or indices and value
		var args = aArgs;
		//converts Function Input to nroxy
		(args.size * 0.5).asInteger.do{|i|
			var key = args[i + 0];
			var val = args[i + 1];
			args[i + 1] = this.functionToProxy(key, val);
		};
		
		nodeMap.set(*args);
		if(this.isPlaying) {
			server.sendBundle(server.latency, [15, group.nodeID] ++ args.asOSCArgArray);
		}
	}

	//
	pset{| ... pairs|
		this.objects[0].source.set(*pairs)	
	}
	
	//-------------ROUTING--------------------------------------------------
	//----------------------------------------------------------------------

	adaptInputToSelfType {|input|
		var typedInput;

		if (input.isKindOf(Symbol)) {

			if (this.class==Ndef) {
				^typedInput = Ndef(input);
			};
			// if (currentEnvironment.isProxySpace) {
			// 	^typedInput = currentEnvironment[input];
			// };
		} {
			^typedInput = input;
		};
	}

	<< { | proxy, key = \in |
		var ctl, rate, numChannels, canBeMapped;
		var input = this.adaptInputToSelfType(proxy);
		var self = this;
		input.postln;
		// IF PROXY IS NIL
		if(input.isNil) { ^self.unmap(key) };

		if (key=='in') { 
			// IN TYPE HAS AN INPUT PROXY THAT MIXES MULTIPLE 
			// PROXIES BEFORE INPUTTING TO MATCHING
			if (self.type==\pre_in) {
				// IF NO IN PROXY EXIST MAKE NEW ONE
				if(self.inProxy.isNil) {
				 	self.inProxy = NodeProxy.audio().fadeTime_(self.fadeTime);
				};
				// self.inProxy[input.generateUniqueName.asSymbol] ?? {
				// 	self.inProxy[input.generateUniqueName.asSymbol] = {input.ar} 
				// };
				(self.key.asString++ " inProxy <-" + input.key.asString).postln;
				self.inProxy[input.key.asSymbol] ?? {
					self.inProxy[input.key.asSymbol] = {input.ar} 
				};
				// ADD THE IN_PROXY TO THE LIST OF YHE INPUT's OUTPUT's
				// SO CAN BE CLEARED 
				input.destProxies.add(self.inProxy);
				input = self.inProxy;
				self.postln;
				self.inProxy.postln;
			};
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
		
		^self // returns self for further chaining
	}

	>> { | proxy, key = \in |

		if(proxy.isKindOf(Symbol)) {
			proxy = Ndef(proxy);
		};
		proxy.perform('<<', this, key);
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
			// destProxy[this.generateUniqueName.asSymbol] = nil;
			destProxy[this.asSymbol] = nil;
		};
		//CLEAR SPECS
		this.clearSpecs(fadeTime);
		//CLEAR IN PROXY
		this.inProxy !? {
			"IN PROXY CLEARED".postln; 
			this.inProxy.fadeTime_(fadeTime);
			this.inProxy = nil 
		};
		//clear variants
		this.variants.clear;
		//controlProxies
		this.controlProxies.do(_.clear(fadeTime));
		this.removeUniqueMethods;
		(this.key++'_N').asSymbol.removeUniqueMethods;
		(this.key++'_n').asSymbol.removeUniqueMethods;
		(this.key).asSymbol.removeUniqueMethods;

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

	rm {|fadeTime ... proxies|
		// fadeTime.postln;
		// proxies.postln;
		proxies.do{|proxy|
			this.inProxy.fadeTime_(fadeTime);
			this.inProxy[proxy] = nil;
		}
	}

	p { this.play }
	s { this.stop }
	c {|fadeTime| this.clear(fadeTime) }
	r{ this.rel }
	t { this.trig }

	q_ {|aQuant| this.quant = aQuant.asQuant;^this}
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
		if (obj.isKindOf(Symbol)) {
				if (Fdef(obj).cs.contains("|in")) {
					obj = Fdef(obj).postln
				} {
					^this[index] = Ndef(obj).postln;
				};
			};
		if (obj.asCompileString.contains("|in")) {
			// If no location supplied add to next index 
			if(at == 0) {
				index = this.objects.size;
			};
			this.filter( index, obj);

			obj.cs.postln;
			^this
		};
		if (at == 0) {
			this.source_(obj)
		} {
			this[index] = obj
		};
		
	}
	//-----------------------------------------------------------------------
	//-----------------------------------------------------------------------
	// COPY WITH EXTENSION
	|+ {|extension, attachPoint = 'back'|
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

	*initClass { 
		all = ();
		//ADDED SYMBOL CONVERTER CALLLBACK
		['_N','_n'].do{|ext|
			SymbolConverter.addExtConvFunc(ext, {|symbol|
			 	Ndef(symbol);
			})
		}
	}

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

//-----------------------------------------------------------------------
//-------------------PATTERNS--------------------------------------------

+ EventPatternProxy {

	source_ { arg obj;
		if(obj.isKindOf(Function)) // allow functions to be passed in
		{ pattern = PlazyEnvirN(obj) }
		{ if (obj.isNil)
			{ pattern = this.class.default }
			{ pattern = obj }
		};

		pattern = this.symbolFilter(obj);

		envir !? { pattern = pattern <> envir };
		this.wakeUp;
		source = obj;
		this.changed(\source, obj);
	}
}
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
		newCopyName.asSymbol;
		^this.copy(newCopyName.asSymbol)
	} 
	// Nameless Copy with set args
	|? {|aFuncKeyPairs|
		var funcKeyPairs = aFuncKeyPairs;

		if (funcKeyPairs.isFunction) {
			^this.source.basicAnon(funcKeyPairs)
		} {
			^this.source.anon(*funcKeyPairs)
		}
	}

	//-----------------------------------------------------------------------
	//-----------------------------------------------------------------------
	//c = a >< b; //b overrides a
	//CHAIN
	// >< {| aPattern|
	// 	var pattern = this.convertSymbol(aPattern);
	// 	var self = this.convertSelfFromSymbol;
	// 	^Pchain(pattern.source, self);
	// }
	// b <+ a; // b overrides a; return b;
	<+ {|aPattern|
		var pattern = this.convertSymbol(aPattern);
		var self = this.convertSelfFromSymbol;
		self.source_(Pchain(pattern, self));
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
		pattern; self;
		^pattern
	}
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
	//SET
	set {|... kvPairs|
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
		this.source.perform('@', aPairs, aControl)
	}
//-----------------------------------------------------------------------
//----------------PbindProxy-Access--------------------------------------
	
	*new { arg key, item;
		var res = this.at(key);
		if(res.isNil) {
			res = super.new(item).prAdd(key);

			if (item.class==PbindProxy) {
				item.prAddControlMethods(res, item.controlProxies, item.pairs);
				item.prAddControlMethods((key++'_P').asSymbol, item.controlProxies, item.pairs);
			}
		} {
			if(item.notNil) { 
				res.source = item; 
				// res.source.postln;
				if (item.class==PbindProxy) {
					item.prAddControlMethods(res, item.controlProxies, item.pairs);
					item.prAddControlMethods((key++'_P').asSymbol, item.controlProxies, item.pairs);
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

	asStream {
		^this.source.asStream
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
					// newVal.postln;
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

	*new { arg ... pairs;
		^super.newCopyArgs(pairs).init
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
					pairs[i + 1].postln;
					val.postln;
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

	//Nameless Copy and add value
	anon {|...aPairs|
		var dct = this.pairs.as(Dictionary);
		var copy = this.deepCopy;

		aPairs.pairsDo {|key, func|
			var source = dct[key].source;
			copy.set(key, func.(source))
		};
		dct.postln;
		^copy;
	}

	basicAnon {|func|
		var source = this.pairs[1].source;
		var copy = this.deepCopy;
		copy.set(this.pairs[0], func.(source))
		^copy;
	}

	|? {|aFuncKeyPairs|
		var funcKeyPairs = aFuncKeyPairs;

		if (funcKeyPairs.isFunction) {
			^this.basicAnon(funcKeyPairs)
		} {
			^this.anon(*funcKeyPairs)
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
//-----------------------------------------------------------------------
+ Pattern {

	convertSelfFromSymbol {
		if (this.class.isKindOf(Symbol)) {
			^Pdef(this)
		} {
			^this;
		}
	}

	symbolFilter {|aInput|
		var input = aInput;
		if (input.class==Symbol) {
			[\rest].do {|illegal|
				if (input==illegal) {
					^input
				} {
					^input = Pdef(input)
				}
			} 
		} {
			if (input.class==Env) {
				^[input].postln;
			} {
				^input				
			}
		}
	}


	>< {| aPattern|
		var pattern = this.symbolFilter(aPattern);
		var self = this.convertSelfFromSymbol;
		^Pchain(pattern, self);
	}

}

+ ListPattern {
	*new { arg list, repeats=1;
		if (list.size > 0) {
			var filteredList;
			filteredList = list.collect{|element|
				super.new.symbolFilter(element)
			};
			^super.new.list_(filteredList).repeats_(repeats)
		}{
			Error("ListPattern (" ++ this.name ++ ") requires a non-empty collection; received "
				++ list ++ ".").throw;
		}
	}
}

+ Pchain {

	*new { arg ... patterns;
		var filteredList;
		filteredList = patterns.collect{|element|
			super.new.symbolFilter(element)
		};
		^super.newCopyArgs(filteredList);
	}
}

+ Pbind {
	
	*new { arg ... pairs;
		var filteredPairs = nil!pairs.size;
		if (pairs.size.odd, { Error("Pbind should have even number of args.\n").throw; });

		forBy (0, pairs.size - 1, 2, {|i|
			var key = pairs[i + 0];
			var item = pairs[i + 1];
			filteredPairs[i + 0] = key;

			if (item.class == PatternProxy) {
				if ([\type, \instrument].contains(key)) {
					filteredPairs[i + 1] = PatternProxy.new(item)
				}{
					filteredPairs[i + 1] = PatternProxy.new.setSource(super.new.symbolFilter(item))
				};

			} {
				if ([\type, \instrument].contains(key)) {
					filteredPairs[i + 1] = item
				}{
					filteredPairs[i + 1] = super.new.symbolFilter(item)
				};	
			};
			
		});
		// filteredPairs.postln;
		// pairs.postln;
		^super.newCopyArgs(filteredPairs)
	}
}

+ Pmono {
	*new { arg name ... pairs;
		var filteredPairs = nil!pairs.size;
		if (pairs.size.odd, { Error("Pmono should have odd number of args.\n").throw; });

		forBy (0, pairs.size - 1, 2, {|i|
			var key = pairs[i + 0];
			var item = pairs[i + 1];
			filteredPairs[i + 0] = key;
			if ([\type, \instrument].contains(key)) {
					filteredPairs[i + 1] = item
				} {
					filteredPairs[i + 1] = super.new.symbolFilter(item)
			}
		});

		// filteredPairs.postln;
		^super.newCopyArgs(name.asSymbol, filteredPairs)
	}
}

//-----------------------------------------------------------------------
//------------------PROXY-SPACE------------------------------------------
+ Environment {
	isProxySpace {^ false}
}

+ ProxySpace {

	isProxySpace { ^true }
}