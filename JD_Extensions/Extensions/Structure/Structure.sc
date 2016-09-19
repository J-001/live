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

	on_ {|aFunc|
		onMarkFuncs.add(aFunc)
	}

	fromTo {|onFunc, second, completionMessage|
		var dur = this.grid.at(second).time - this.time;
		dur.postln;
		this.on_({onFunc.value(dur)});
		completionMessage !? {
			this.grid.at(second).on_({completionMessage.value(dur)})
		};
	}	

	clear {
		super.newCopyArgs(*(nil!5))
	}
}
//--------------------------------------------------------------
//--------------------------------------------------------------
TimeGrid {
	var <>name, <>timeArray, <>marks;
	var <>plan, <>num;
	var <>current;

	*new {|name, timeArray|
		^super.newCopyArgs(name, timeArray).init;
	}	

	init {
		marks = List.new;
	}

	makeMarks {|clock|
		var previousMark;
		timeArray.do{|addTime, n|
			var time = if (previousMark.isNil) {
					addTime
				} {
					previousMark.time + addTime;
				};
			var mark = Mark(clock, time, addTime).grid_(this).num_(n.postln);
			marks.add(mark);
			previousMark = mark;

		};
		this.current = marks[0];
	}

	dur {
		^timeArray.postln.sum;
	}

	next {|func|
		if ((current.num + 1) >= marks.size) {
			// ^plan.next(func)		
		} {
			^marks.at(current.num + 1).on_(func);		
		}
	}

	after {|marks|
		^marks.clipAt(current.num + marks);		
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

	make {|func|
		func.value(this, dur)
		
	}
}
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
TimeStructure {
	var <>clock, <>areaSeq, <>areas;
	var <>current;
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
		zero = {this.clock.beats};
		isRunning = false;
	}

	make {|...aAreas|

		aAreas.do{|rawArea, n|
			var area = rawArea;
			if (area.isArray) {
				area = Area(area[0], area[1]);
			};
			areaSeq.add(area.name);
			area.grids .do{ | grid |
				grid.makeMarks(this.clock);
			};
			areas[area.name] = area;
			durs[area.name] = area.dur;
		};

		current = areaSeq.first;
		active = this;
	}

	begin {
		var previousArea;
		// Begin On Next Integer Beat
		clock.schedAbs( clock.nextTimeOnarea, {
			zero = clock.beats;
			isRunning = true;

			areaSeq.do{|name|
				var area = areas[name];
				var dur = if (previousArea.isNil) {
					area.dur;
				} {
					area.dur + previousArea.dur
				};

				area.grids.do{|grid|
					grid.marks(_.throw);
				};	

				clock.schedAbs(dur, {
					current = area;
				});
				previousArea = area;
			}
		})	
	}

	clear {
		areaSeq = nil;
		current = nil;
		durs = nil;
		isRunning = false;
	}

	at {| ... argList|
		argList.do{|arg|
			switch (argList.size)
				{0}	{^nil}		//current mark
				{1}	{}			//mark || area if is symbol
				{2}	{}			//grid
				{3} {};			//area
			
		}
	}

	nextTimeOnGrid {
		^ (zero + this.current.next.time)
	}

	asQuant { ^StructQuant(this)}

}
//-----------------------------------------------------------------------
//---------------------EXTENSIONS----------------------------------------
+ Function {
	
	mark {|areaName, markNum|
		var plan = TimeStructure.active;
		var mark = plan.at(areaName, markNum);
		^mark.on_(this);
	}

	fromTo {|firstNum, secondNum, completionMsg|
		var plan = TimeStructure.active;
		var first = plan.at(plan.current.name, firstNum);
		first.postln.num.postln;
		^first.fromTo(this, secondNum, completionMsg)
	}
}
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------


/*
TO DO: 

Section Shaping;
area scope Controls,
	Mark scope Controls;
*/