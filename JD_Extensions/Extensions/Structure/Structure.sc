// Mark {
// 	var <>clock, <>time, <>dur,
// 	<>beatZero, <>onMarkFuncs, <>grid, <>num,
// 	<>reached;


// 	*new {|clock, time, dur|
// 		^super.newCopyArgs(clock ? TempoClock.default.permanent_(true), time, dur).init;
// 	}

// 	init {
// 		onMarkFuncs = List.new;
// 		reached = false;
// 	}

// 	throw {
// 		beatZero = clock.beats;
// 		clock.schedAbs( (clock.beats + time), {
// 			onMarkFuncs.do(_.value);
// 			reached = true;
// 			grid.current = this;
// 			nil;
// 		});
// 	}

// 	until {|mul = 1, add = 0|
// 		if (reached) {
// 			^nil;
// 		}{
// 			^((time - (clock.beats - beatZero)) * mul) + add
// 		}
// 	}

// 	onReach_ {|aFunc|
// 		onMarkFuncs.add(aFunc)
// 	}

// 	clear {
// 		super.newCopyArgs(*(nil!5))
// 	}
// }
//--------------------------------------------------------------
//--------------------------------------------------------------
TimeGrid {
	var <>durs, <>times, <>rout;
	var <>area;
	var <>current, <>mark;

	*new {|durs|
		^super.newCopyArgs( [0] ++ durs ).init;
	}	

	init {
		times = [];
		mark = 0;
	}

	makeMarks {|clock, startTime|

		durs.do{ | dur, n |
			var time = if (times[0].isNil) {
					startTime + dur
				} {
					times[n - 1] + dur;
				};
			times = times.add(time);
		};

		rout = Routine({
			durs.do{|dur, n|
				mark = n;
				dur.wait;
			}
		}).clock_(clock);
	}

	throw {
		rout.play;
	}

	dur {
		^durs.sum;
	}

	end {
		^times.last
	}

	next {
		if (( mark + 1) >= durs.size) {

		} {
			^times.at(mark + 1);		
		}
	}

	skip {| markOffset = 0|
		if ((mark + markOffset) >= durs.size) {
			^nil;
		} {
			// times.postln;
			^times.at(mark + markOffset);		
		}
	}

	until {

	}

	at {|int|
		^durs[int];
	}
}
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
Area {
	var <>dur, <>start, <>grids, <>clock, <>plan;
	var <>completed;

	*new {|dur, start|
		^super.newCopyArgs(dur, start).init;
	}

	init {
		grids = ();
	}

	make { | func |
		grids.push;
		func.value(this, dur);
		currentEnvironment.pop;

		grids.getPairs.pairsDo{ | key, val | 
			this.addUniqueMethod(key, { val })
		}
	}

	grid { | gridKey |
		^(grids[gridKey] ? this.lowest)
	}

}
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
TimeStructure : TempoClock {

	var <>areaSeq, <>areas;
	var <>defaultArea, <>defaultGrid;
	var <>durs, <>runningDur;
	var <>zero, <>isRunning;
	var <>isDefaultClock;
/*

	make {|...aAreas|
		var runningTime = 0;

		areaSeq = List.new;
		areas = ();
		durs = ();
		this.postln;
		this.zero = this.beats;
		runningDur = 0;

		isRunning = false;
		isDefaultClock = false;

		aAreas.do{ | rawArea, n |
			var area = rawArea;

			areaSeq.add(area.name);

			area.grids.do{ | grid |
				grid.makeMarks(this, area.start);
			};

			areas[area.name] = area;
			durs[area.name] = area.dur;
		};
		areaIndex = 0;
	}
*/  
//UTILS

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
	make { | func |
		areaSeq = areaSeq ? List.new;
		areas = areas ? ();
		zero = zero ? this.beats;

		areas.push;
		func.value;
		currentEnvironment.pop;

		areas.do{|area|
			area.grids.do{|grid|
				grid.makeMarks(this, area.start)
			}
		};

		areas.getPairs.pairsDo{ | key, val | 
			this.addUniqueMethod(key, { val })
		}
	}

	begin {
		// Begin On Next Integer Beat
		this.schedAbs( this.nextTimeOnGrid, {
			zero = this.beats;
			isRunning = true;

			areas.do{ | area |
				this.schedAbs(zero + area.start, {
					area.grids.do{ | grid |
						grid.throw
					};
					nil;
				});
			};
			nil;
		})	
	}

	remaining { | ... argList |
		//get current mark in grid and return array of durations until next area
		var input = if (argList[0].asWords("_",".").size > 1) {
				argList[0].asWords("_",".")
			} {
				argList
			};
		var area = areas[input[0].asSymbol];
		var grid = area.grids[input[1].asSymbol];
		var mark = grid.mark;
		var t_remaining = grid.durs.copy[ mark + 1 .. grid.durs.size - 1];
		^t_remaining
	}
	
	// ON-THE-FLY AREA CONSTRUCTION
	throwArea { | ref_area, attach, offset, area, dur |
		var start;
		if (attach.isNil) {
			start = ref_area.dur;
		};
		if (attach == \end) {
			start = ref_area.end;
		};
		if (attach == \start) {
			start = ref_area.start;
		};

		areas[area] = Area(dur, start)
	}

	// REBUILD
	executeOn { | func, quant |
		this.schedAbs( 
			quant.asQuant.nextTimeOnGrid(this),
			{func.value; nil} 
		)
	}

	xon { | ... argList |
		this.executeOn(*argList)
	}

	//SETTINGS
	defaults{ | area, grid |
		default
	}
	// QUANT
	nextTimeOnStructure { | area, gridLvl = \lowest, markOffset, beatOffset = 0|
		var waitTime;
		var grid;

		if (gridLvl == \lowest) {
			grid = this.areas[area].lowest;
		} {
			grid = this.areas[area].grids[gridLvl];
		};

		waitTime = grid.skip((markOffset ? 1));

		^(zero + waitTime + (beatOffset ? 0));
	}

	asQuant { ^StructQuant() }
	
	//CLOCK 
	asClock { ^this }

	play {| task, quant |
		this.schedAbs(quant.nextTimeOnGrid(this), task)
	}

	timeToNextBeat { arg quant = 1.0; // logical time to next beat
		^quant.nextTimeOnGrid(this) - this.beats
	}

	defaultClock_ { | bool |
		isDefaultClock = bool;
		if (bool) {
			TempoClock.default = this;
		} {
			TempoClock.default = TempoClock.new;
		}
	}

	defaultClock {
		^TempoClock.default
	}

	isTimeStructure {^true}
}
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
// StructClock : TempoClock {
// 	var <>tStructure;

// 	play {|task, quant|
// 		this.schedAbs(quant.nextTimeOnGrid(tStructure), task)
// 	}

// 	timeToNextBeat { arg quant=1.0; // logical time to next beat
// 		^quant.nextTimeOnGrid(tStructure) - this.beats
// 	}
// }
//-----------------------------------------------------------------------
//---------------------EXTENSIONS----------------------------------------
+ Clock {
	asClock { ^this}

	isTimeStructure {^false}
}

