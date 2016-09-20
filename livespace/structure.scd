(
	s.boot;
	t = TimeStructure().permanent_(true);
	t.make(
		Area(\intro, 32).make({|area, dur|
			area.addN( 
				TimeGrid(\beat, ([1,1,2]!8).flatten.normalizeSum * dur ),
				TimeGrid(\bar, (4!8) )
				);
		}),
		Area(\a, 32).make({|area, dur|
			area.addN( 
				TimeGrid(\beat, (0.5!64).normalizeSum * dur ),
				TimeGrid(\bar, (7!4 ++ 4) ),
				TimeGrid(\signal, [24,8] )
				);
		})
	);
)


t.isDefaultClock
t.default_(true)
p = Pbind(\freq,100).play(quant: [\bar]);
q = Pbind(\freq,320).play(t, quant: [\bar]);


Pbind(\freq,).play

t.current.lowest.current.dur
t.current.lowest.skip(2).dur
/*
	\beat
	\bar
	\phrase
	\sub
	\section
	\signa
l	\area
*/
t.begin
t.current.lowest.current.time
t.next.lowest
t.zero
t.areas[\intro].grids[\beat].marks
t.begin;
t.nextTimeOnGrid(\bar,2).postln;t.nextTimeOnGrid(\bar,0).postln;
t.areas

p = Pbind().play(quant: [t,0]).play

p.play

Ndef(\sin).stop

Ndef(\sin, {
	SinOsc.ar * 0.1
}).quant_([t,0]).play;

Ndef(\sin).quant_( [t,0] ).play;




