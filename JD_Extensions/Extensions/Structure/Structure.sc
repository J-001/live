Mark {
	var <>clock, <>time, <>dur,
	<>beatZero, <>onMarkFuncs, <>grid, <>num,
	<>reached;


	*new {|clock, time, dur|
		^super.newCopyArgs(clock ? TempoClock.default.permanent_(true), time, dur).init;
		
	}

	init {
		onMarkFuncs = List.new;
		reached = false;
	}

	throw {
		beatZero = clock.beats;
		clock.schedAbs( (clock.beats + time), {
			onMarkFuncs.do(_.value);
			reached = true;
			grid.current = this;
			nil;
		});
	}

	until {|mul = 1, add = 0|
		if (reached) {
			^nil;
		}{
			^((time - (clock.beats - beatZero)) * mul) + add
		}
	}

	onReach_ {|aFunc|
		onMarkFuncs.add(aFunc)
	}

	clear {
		super.newCopyArgs(*(nil!5))
	}
}
//--------------------------------------------------------------
//--------------------------------------------------------------
TimeGrid {
	var <>name, <>timeArray, <>marks;
	var <>area;
	var <>current;

	*new {|name, timeArray|
		^super.newCopyArgs(name, timeArray).init;
	}	

	init {
		marks = List.new;
	}

	makeMarks {|clock, runningTime|
		var previousMark;
		timeArray.do{|addTime, n|
			var time = if (previousMark.isNil) {
					runningTime + addTime
				} {
					runningTime + previousMark.time + addTime;
				};
			var mark = Mark(clock, time, addTime).grid_(this).num_(n);
			marks.add(mark);
			previousMark = mark;
		};
		this.current = marks[0];
	}

	dur {
		^timeArray.sum;
	}

	next {
		if ((current.num + 1) >= marks.size) {

		} {
			^marks.at(current.num + 1);		
		}
	}

	skip {| markOffset = 0|
		if ((current.num + markOffset) >= marks.size) {
			^nil;
		} {
			^marks.at(current.num + markOffset);		
		}
	}

	at {|int|
		^marks[int];
	}

}
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
Area {
	var <>name, <>dur, <>grids, <>clock, <>plan;
	var <>lowest;

	*new {|name, dur|
		^super.newCopyArgs(name, dur).init;
	}
	// No Need for Grids to Be Ordered
	add {|grid|
		if (grid.isArray) {
			grid = TimeGrid(grid[0], grid[1])
		};

		grid.area_(this);

		if (lowest.isNil) {
			lowest = grid;
		} {
			if (lowest.marks.size < grid.marks.size) {
				lowest = grid;
			}
		};
		if (dur.isNil) {
			dur = grid.dur;
			grids[grid.name] = grid;
		} {
			if (dur != grid.dur) {
				("Not required duration of:" + dur.asString).postln;
			}{
				grids[grid.name] = grid;
			}
		};
	}

	addN {| ... gridList|
		if (gridList.size > 0) {
			gridList.do{|grid|
				this.add(grid);
			}
		}
	}
	//make
	init {
		grids = ();
	}

	grid { | gridKey |
		^(grids[gridKey] ? this.lowest)
	}

	make { | func |
		func.value(this, dur)
	}
}
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
TimeStructure : TempoClock {

	var <>areaSeq, <>areas;
	var <>areaIndex;
	var <>durs, <>runningDur;
	var <>zero, <>isRunning;
	var <>isDefaultClock;

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
			if (area.isArray) {
				area = Area(area[0], area[1]);
			};
			areaSeq.add(area.name);

			area.grids.do{ | grid |
				grid.makeMarks(this, runningTime);
			};
			runningTime = runningTime + area.dur;
			areas[area.name] = area;
			durs[area.name] = area.dur;
		};
		areaIndex = 0;
	}

	begin {
		var previousArea;
		// Begin On Next Integer Beat
		this.schedAbs( this.nextTimeOnGrid, {
			zero = this.beats;
			isRunning = true;

			areaSeq.do{ | name, n |
				var area = areas[name];

				var dur = if (previousArea.isNil.postln) {
					area.dur;
				} {
					area.dur + previousArea.dur
				};

				area.grids.do{ | grid |
					grid.marks.postln.do( _.throw );
				};	
				this.postln;

				areaIndex.postln;
				this.schedAbs( ( dur ), {
					areaIndex = n;
					nil;
				});

				previousArea = area;
			};
			nil
		})	
	}

	remaining { | areaKey, gridKey |
		//get current mark in grid and return array of durations until next area
		var area = areas[areaKey] ? this.current;
		var grid = area.grids[gridKey] ? area.lowest;
		var marks = grid.marks;
		var n = grid.current.num;
		var t_remaining = grid.timeArray.copy[n .. grid.timeArray.size];
		^t_remaining
	}

	//Access
	at { | ... argList |
		switch (argList.size)
			{0}	{ ^this.current.lowest.current }
			{1}	{ 
				if (argList[0].isKindOf(Symbol)) {
					^this.current.grid( argList[0] )
				} {
					^this.current.lowest.marks[ argList[0] ]
				}
			}
			{2}	{ 
				if (argList[1].isKindOf(Symbol)) {
					^this
					.area( argList[0] )
					.grid( argList[1] ) 
				} {
					^this.current
					.grid( argList[0] )
					.marks[ argList[1] ] 
				}
			}
			{3}	{ 
				^this
				.area( argList[0] )
				.grid( argList[1] )
				.marks[ argList[2] ] 
			}
	}
	//Mark
	currentMark { | grid |
		this.current.grids[grid].current
	}

	time { | ... argList |
		var mark = this.at(*argList);
		if (mark.class == Mark) {
			^(mark.time)
		}; 
	}
	//Grid

	onNext {|lvl, func|
		this.schedAbs( this.nextTimeOnStructure(lvl), {func.value; nil})
	}
	//AREA

	area {|areaKey|
		^(areas[areaKey] ? this.current)
	}
	current {
		^areas[ areaSeq[ areaIndex ] ]
	}

	nextArea {
		^areas[ areaSeq[ areaIndex + 1] ]
	}

	//CALLED IN STRUCT QUANT
	nextTimeOnStructure {|gridLvl = \lowest, markOffset = 1, beatOffset = 0|
		var waitTime;
		var grid;

		if (gridLvl == \lowest) {
			grid = this.current.lowest;
		} {
			grid = this.current.grids[gridLvl];
		};
		waitTime = grid.skip(markOffset).time;

		^(zero + waitTime + beatOffset);
	}

	asQuant { ^StructQuant() }
	
	//CLOCK Methods
	
	asClock { ^this }

	play {| task, quant |
		this.schedAbs(quant.nextTimeOnGrid(this), task)
	}

	timeToNextBeat { arg quant = 1.0; // logical time to next beat
		^quant.nextTimeOnGrid(this) - this.beats
	}

	default_ { | bool |
		isDefaultClock = bool;
		if (bool) {
			TempoClock.default = this;
		} {
			TempoClock.default = TempoClock.new;
		}
	}

	default {
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

