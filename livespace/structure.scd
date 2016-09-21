(
	s.boot;
	t = TimeStructure(queueSize:512).permanent_(true);
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
/*

	(
		s.boot;
		t = TimeStructure().permanent_(true);
		t.make (
			Area(\intro, 8).make({|area, dur|
				area.addN( 
					TimeGrid(\beat, ([1,1,2]!2).flatten.normalizeSum * dur ),
					TimeGrid(\bar, (4!2) )
					)
			}),
			Area(\a, 2).make({|area, dur|
				area.addN( 
					TimeGrid(\beat, ([1,1]).flatten.normalizeSum * dur ),
					TimeGrid(\bar, (2!1) )
					)
			})
		)
	)
*/


t.begin;
Fdef(\beat, {t.remaining(\intro, \beat)});

t[\beat].timeArray
t[\bar].timeArray


t.begin;

t.schedAbs( (t.zero + t.current.lowest.next.time) , {2.postln; nil}) 

t.onNext(\bar, { 2.postln; nil })

t.play({ 1.postln; nil }, [\bar])

t.areas.do{|area|
	// area.grids.do{|grid|
		area.grid.marks.do{|mark|
			mark.dur.postln
		}
	// }
}


Event.addEventType(\gridSync, {

	var grid = if (~grid.isArray) {
		~grid[0].postln
	};
	var area = if (~area.isArray) {
		~area[0].postln
	};
	var ts = if (~ts.isArray) {
		~ts[0].postln
	};
	var skip = ~skip;

	~dur = ts.areas[(area ? ts.current.name)]
	.grids[(grid ? \lowest)].next.dur.postln;

	~type = \note;
	currentEnvironment.play;
});

t.next(\area,{
	Pbind(
		\dur, Pseq( t.remaining(\intro,\beat) ) ,
		\post,  Pfunc{|e| e.dur.postln}
		).play(t, \bar)
	})

t.begin
// t.onNext(\bar, 
	Pgrid(
		\dur, Pseq( t.remaining(\intro,\beat) ) ,
		\post,  Pfunc{|e| e.dur.postln}
	).play(t, quant: [\bar])
// )


t.onNext(\bar,
	Pbind
	)


Pbind(
	\dur, Pseq(t.remaining('intro', \beat)),
	// \dur, Pseq(t.remaining(\intro, \beat)),
	\freq,320).play(t, quant: [\bar]);


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

Ndef(\sin).cl_(t).quant_([\bar]).play;

