Mark {
	var <>clock, <>time, <>dur,
	<>beatZero, <>onMarkFuncs, <>grid, <>num,
	<>reached;


	*new {|clock, time, dur|
		^super.newCopyArgs(clock ? TempoClock.default.permanent_(true), time, dur).init;
		
	}

	*throwFrom {|mark, timeOffset|
		^Mark(mark.clock, mark.time + timeOffset)
	}

	*throw {|timeOffset|
		^Mark(this.clock, this.time + timeOffset)
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
		^timeArray.postln.sum;
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

	time {
		^this.current.time;
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

	make { | func |
		func.value(this, dur)
		
	}
}
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
TimeStructure {
	var <>clock, <>areaSeq, <>areas;
	var <>areaIndex;
	var <>durs;
	var <>zero, <>isRunning;

	classvar <>active;

	*new {|clock|
		^super.newCopyArgs(clock ? TempoClock.default.permanent_(true)).init; 
	}

	init {
		areaSeq = List.new;
		areas = ();
		durs = ();
		zero = this.clock.beats;
		isRunning = false;
	}

	make {|...aAreas|
		var runningTime = 0;
		aAreas.do{|rawArea, n|
			var area = rawArea;
			if (area.isArray) {
				area = Area(area[0], area[1]);
			};
			areaSeq.add(area.name);
			area.grids .do{ | grid |
				grid.makeMarks(this.clock, runningTime.postln);
			};
			runningTime = runningTime + area.dur;
			areas[area.name] = area;
			durs[area.name] = area.dur;
		};
		areaIndex = 0;
		active = this;
	}

	begin {
		var previousArea;
		// Begin On Next Integer Beat
		clock.schedAbs( clock.nextTimeOnGrid, {
			zero = clock.beats;
			isRunning = true;

			areaSeq.do{|name, n|
				var area = areas[name];
				var dur = if (previousArea.isNil) {
					area.dur;
				} {
					area.dur + previousArea.dur
				};

				area.grids.do{|grid|
					grid.marks.do(_.throw);
				};	

				clock.schedAbs(dur, {
					areaIndex = n;
				});
				previousArea = area;
			}
		})	
	}

	// at {| ... argList|
	// 	argList.do{|arg|
	// 		switch (argList.size)
	// 			{0}	{^nil}		//current mark
	// 			{1}	{}			//mark || area if is symbol
	// 			{2}	{}			//grid
	// 			{3} {};			//area
			
	// 	}
	// }

	current {
		^areas[ areaSeq[ areaIndex ] ]
	}

	nextArea {
		^areas[ areaSeq[ areaIndex + 1] ]
	}

	nextTime {|gridLvl = \lowest, markOffset = 0, beatOffset = 0|
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

	elapsed {
		^(clock.beats - zero)
	}

	asQuant { ^StructQuant(this) }

	play {|task, quant|
		this.clock.schedAbs(quant.nextTimeOnGrid(this), task)
	}

	sched {|task, quant|
		this.clock.sched(task, quant)
	}

	asClock { ^this.clock }

}
//-----------------------------------------------------------------------
//---------------------EXTENSIONS----------------------------------------
+ Function {

}
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------


/*
TO DO: 

Section Shaping;
area scope Controls,
	Mark scope Controls;
*/

+ Clock {
	asClock { ^this}

}