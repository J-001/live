//-----------------------------------------------------------------------
//-----------------------------------------------------------------------

(
	a = AreaPlan(t);
	a.make(
	[\intro, [1,1,1,1,1,1,1,1]]
	);

	{|dur| 
		{SinOsc.ar*0.1 * EnvGen.kr(Env.triangle(dur),doneAction:2)}.play
	}.fromTo(2,4,{"done".postln})
)

a.begin



\radio.q_(0) @ Pdef(\radioNoise)


\radio.p;

a.areas[0].fromTo(2,4,{|dur|
	{ SinOsc.ar*0.1 * EnvGen.kr(Env.triangle(dur),doneAction:2)}.play;
})

{
	SinOsc.ar  * EnvGen.kr(Env.triangle(_), doneAction: 2)
}.fromTo(2,4,{

	})

a[4].until
a.current.name
	{ SinOsc.ar*0.1 * Line.kr(0,1,a.current.current.until)}.play;

/*
	Quantization To Background Structure
*/

a.current.current


[NodeProxy, Pattern].targetMode_();

\every, current + 1
\skip , current + 2
\skip , current + rand(1,3)
\only, 86
\near, duration.findNearest


\radio.amp_({Line.kr(0,1,a[3].until)})

a.current.name

a[\intro][7]



Ndef(\radio).markQuant => quantize actions to Structure

m[0].dur
a.current => current mark
a.next => next mark
a.between => duration between marks

a.onMark [func] => evaluate func on specified mark
a.onNext [func] => evaluate func on next mark


make marks
play them

//Offsetting
not new mark just new ScheduledFunction

{action}.throwNear(m[0], mul:, add: )

mark.cleanThrow(action,dur,done,offset);
{action}.cleanThrow(mark,dur,done,offset);

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------

