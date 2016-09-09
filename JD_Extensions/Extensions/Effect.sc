
Effect {

 	var<>numChan;
	var<>bus;
	var<>group;
	var<>synth;
	var<>synthName;
	var<>func;
	var<>synthRunning;

	//For subclass access
	var<>key;
	var<>accessingClass;

	*new{|aNumChan,target,addAction, aSynth...synthArgs|

		if(SynthDescLib.match(aSynth).notNil,
			{
				if(Server.default.serverRunning,
					{^super.new.init(aNumChan,target,addAction,aSynth,synthArgs)},
					{"Server Not Booted".postln;
					^nil}
					);
			},
			{"SynthDef doesn't exist".postln;
			^nil}
			);
	}

	init{|aNumChan, target,addAction, aSynth, synthArgs|
		accessingClass=this.class;
		synthRunning=false;
		numChan=aNumChan;
		synthName = aSynth;

		if(bus.isNil,{bus = Bus.audio(Server.default,numChan)});

		func = {
			group = Group(target,addAction:addAction);
			synth = Synth.tail(group,aSynth,[\in,bus]++synthArgs);

			synthRunning=true;
		};

		if (synthRunning==false,{func.value});
	}

	clear {
		synth.free;
		group.free;
		bus.free;
	}

	index{
		^bus.index;
	}

	addToTree{
  		ServerTree.put(func,key);
  	}
  	removeFromTree{
  		ServerTree.remove(key);
  	}

  	printOn {|stream|
        stream << accessingClass.asString << "('" << this.key << "')";
    }

}


//---------------------------------------------------------------
//---------------------------------------------------------------

FX  {
	var<>key;
	var<>effect;
	classvar<> all;

	*initClass {
		all=IdentityDictionary.new;
	}

	*new {|key,aEffect|

		var res = this.at(key);

		if(res.isNil,
			{res = super.new.init(key,aEffect)},
			{if(aEffect.notNil,
				{
					if(res.effect.notNil){
						res.remove;
						res = super.new.init(key,aEffect);
					};  
					
					});
			});
		^res
	}

	init {|key,item|
		effect = item;
		effect.key = key;
		effect.accessingClass = this.class;
		effect.addToTree(key);
		^this.addInstanceToDict(key);
	}

	printOn {|stream|
        stream << this.class.asString << "('" << key << "')";
    }
	
	addInstanceToDict {|argKey|
		key = argKey;
		all.put(argKey, this);
	}

	clear {
		this.effect.clear;
	}

	set{|synthArgs|
		synthArgs.postln;
		// this.effect.synth = Synth.replace(this.effect.synth,aSynth,[\in,this.index]++synthArgs,sameID:true);
		this.effect.synth.release;
		this.effect.synth = Synth.tail(this.group,this.effect.synthName,[\in,this.index]++synthArgs);
	}

	index{
		^this.effect.index;
	}
	group {
		^this.effect.group;
	}

	synth {
		^this.effect.synth;
	}

	numChan{
		^this.effect.numChan;
	}

	outs {
		^this.index+(0..this.effect.numChan);
	}

	addToTree{
  		this.effect.addToTree;
  	}
  	removeFromTree{
  		this.effect.removeFromTree;
  	}
  	remove {
  		this.effect.clear;
  		this.effect.removeFromTree;
  		this.effect.key.postln;
  		all.removeAt(this.effect.key);
  	}

	*at{|key|
  		^this.all.at(key);
  	}

  	*remove {|key|
  		this.all.at(key).remove;
  	}
  	
  	*hasGlobalDictionary{^true}

}

//---------------------------------------------------------------
//---------------------------------------------------------------
/*
EXAMPLE
(
SynthDef(\verb, {|out=0,in=0,mix=0.5,fb=0,cutoff=5000,gate=1,rel=4,tail=3|
	var sig,env;
	var local;
	sig = In.ar(in,2);
	local = LocalIn.ar(2)+sig;
	env = EnvGen.ar(Env.asr(rel,1,rel,curve:-3),gate,doneAction:2);
	//multiple passes through filter
	15.do{local = AllpassC.ar(local,0.06,rrand(0.01,0.06),tail)};
	LocalOut.ar(local*fb.clip(0,1));
	local = LPF.ar(local,cutoff);
	sig=  (sig*(1-mix))+(local*(mix));
	Out.ar(out,sig*env);
}).add(completionMsg:{|server,name| 

	if(FX.all[name].notNil){
		r{
			0.01.wait;
			FX(name).set([\fb,0.2,\mix,0.1,\cutoff,5000,\rel,3,\tail,3])
		}.play
	} {
		r{
			0.05.wait;
			FX(name, Effect(2,s,\addAfter,name));
		}.play;
	};
	});
)

*/





