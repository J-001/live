(
	s.boot;
	t = TimeStructure(queueSize:512).permanent_(true);
	t.make({
		var intro, a;
			intro = Area(\intro, 32,0).make({|area, dur|
				area.addN( 
					TimeGrid(\beat, ([1,1,2]!8).flatten.normalizeSum * dur ),
					TimeGrid(\bar, (4!8) )
					);
			});

			Area(\a, 32,32).make({|area, dur|
				area.addN( 
					TimeGrid(\beat, (0.5!64).normalizeSum * dur ),
					TimeGrid(\bar, (7!4 ++ 4) ),
					TimeGrid(\signal, [24,8] )
					);
			})
		});
)

t.areas.push;
~intro = Area(\intro, 32).make(

	);

~a = Area(\a, 32).start_(~intro.end - 30).make(

	);

~long = Area(\long, 500).make({|area, dur|
	
	TimeGrid(\signal, )
	})

currentEnvironment.pop;

t.addArea(
	Area.new()
	)
t.addArea(
	Area.startFrom(\end, \a)
	Area.extend(\a, name, dur)
	)

t.addArea()

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

t.areas[\a].grids[\beat].marks.first.time


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

t = TimeStructure().permanent_(true)
.make({|ts|
	
	ts.add(
		Area(\intro, 64, 0)
		.start_(0)
		.make({|area, dur|

			})	
	)


	|}) 

(
	s.boot;
	t = TimeStructure().permanent_(true).make({

		// Intro Section
		~intro = Area(16,0).make({ | dur |
				~beat = TimeGrid( ([1,1,2]!4).flatten);
				~bar = TimeGrid( (4!4) );
				// ~phrase = TimeGrid( 2!8 );
		});	
		// A Section
		// ~a = Area(16).start_(~intro.dur).make({|area, dur|
		// 		// ~beat = TimeGrid( ([3,1]!4).flatten );
		// });
	}) 
)
t.begin
t.remaining('intro.bar')
t.areas

t.begin

t.xon({
	Pbind(
	\dur, Pseq(t.remaining(\intro, \beat) )
	).play(t,quant:[\intro,\beat])

}, [\intro, \bar])


t.intro.beat.durs


t.beats
t.nextTimeOnGrid

t.areas[\intro].grids[\bar].skip(2)
t.areas[\a].grids[\beat].times
t.areas[\intro].grids[\beat].times

t.intro.beat.times[0]
t.begin;
t.beats

t.nextTimeOnStructure(\intro, \beat)
t.remaining(\intro, \bar)

(t.intro.beat.times[t.intro.beat.mark] + t.zero)
t.remaining(\intro, \beat);

t.beats.postln;
t.nextTimeOnStructure(\intro,\bar);
t.nextTimeOnStructure

Pbind(
	\dur, Pseq(t.remaining(\intro, \beat) )
).play(t,quant:[\intro,\beat])


Event.addEventType(\grid,{


	})
