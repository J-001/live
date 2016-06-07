SynthDef(\gVerb, {|out=0,in=0,gate=1,size=50,rev=50,hdamp=0.5|
	var sig;
	sig = In.ar(in);
	sig = GVerb.ar(sig,size,rev,hdamp)*0.2;
	Out.ar(out,sig);
}).add;




SynthDef(\reverb, {|out=0,in=2|
	

}).add;








