Mark {
	var <>clock, <>time,
	<>beatZero, <>onMarkFuncs, <>area, <>num,
	<>reached;


	*new {|aClock, aTimeUntil|
		^super.newCopyArgs(aClock ? TempoClock.default, aTimeUntil).init;
		
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
			area.current = this;
		});
	}

	until {|mul = 1, add = 0|
		if (reached) {
			nil
		}{
			^((time - (clock.beats - beatZero)) * mul) + add
		}
	}

	on_ {|aFunc|
		onMarkFuncs.add(aFunc)
	}

	fromTo {|onFunc, second, completionMessage|
		var dur = this.area.at(second).time - this.time;
		dur.postln;
		this.on_({onFunc.value(dur)});
		completionMessage !? {
			this.area.at(second).on_({completionMessage.value(dur)})
		};
	}	

	clear {
		super.newCopyArgs(*(nil!5))
	}


}
//--------------------------------------------------------------
//--------------------------------------------------------------
Area {
	var <>name, <>timeArray, <>clock, <>marks;
	var <>plan, <>num;
	var <>current;
	/*

		*new {|aName, aClock, aTimeArray|
			var area, name, markNum;

			if (aName.isArray) {
				name = aName[0];
				markNum = aName[1];
				^area = all[name.asSymbol][markNum];
			} {
				name = aName;
			};

			area = all[name.asSymbol];
			
			if (area.isNil) {
				area = super.new;
				area.name = name.asSymbol;
				area.marks = List.new;
				area.timeArray = aTimeArray;
				area.clock = aClock ? TempoClock.default;
				all[area.name] = area;
			} {
				area.timeArray = aTimeArray ? area.timeArray
			};
			^area;
		}
	*/
	*new {|name, timeArray, clock|
		var checkedName = name;
		^super.newCopyArgs(checkedName, timeArray, clock ? TempoClock.default).init;
	}	

	init {
		marks = List.new;
	}

	makeMarks {
		var previousMark;
		timeArray.do{|addTime, n|
			var time = if (previousMark.isNil) {
						addTime
					} {
						previousMark.time + addTime;
					};
			var mark = Mark(clock, time).area_(this).num_(n.postln);
			marks.add(mark);
			previousMark = mark;

		};
		this.current = marks[0];
	}

	duration {
		^timeArray.sum;
	}

	next {|func|
		if ((current.num + 1) == marks.size) {
			^plan.next(func)		
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
AreaPlan {
	var <>clock, <>areas;
	var <>current;
	var <marks;
	var <>durs;
	var <>zero;

	classvar <>active;

	*new {|clock|
		^super.newCopyArgs(clock ? TempoClock.default).areas_(List.new); 
	}

	make {|...aAreas|
		areas = List.new;
		marks = ();
		durs = ();
		aAreas.do{|area, n|
			var checkedArea = if (area.isArray) {
				area = Area(area[0], area[1], clock);
			} {
				area
			};
			areas.add(area.plan_(this).num_(n));
			area.makeMarks;
			marks[area.name] = area.marks;
			durs[area.name] = area.duration;
		};

		current = areas.first;
		active = this;
	}

	begin {
		var previousArea;
		areas.do{|area|
			var dur = if (previousArea.isNil) {
				area.duration;
			} {
				area.duration + previousArea.duration
			};

			clock.schedAbs(dur, {
				current = area;
			});
			area.marks.do(_.throw);
			previousArea = area;
		}
	}

	clear {
		areas = nil;
		current = nil;
		marks = nil;
		durs = nil;
	}

	next {|func|
		^areas.clipAt(current.num + 1)[0].on_(func)
	}

	at {|areaName, markNum|
		if (markNum.isNil) { 
			if (areaName.isInteger) {
				^marks[current.name][areaName]
			} {
				^marks[areaName];
			};
		};
		^marks[areaName][markNum]
	}




}
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
+ Function {
	
	mark {|areaName, markNum|
		var plan = AreaPlan.active;
		var mark = plan.at(areaName, markNum);
		^mark.on_(this);
	}

	fromTo {|firstNum, secondNum, completionMsg|
		var plan = AreaPlan.active;
		var first = plan.at(plan.current.name, firstNum);
		first.postln.num.postln;
		^first.fromTo(this, secondNum, completionMsg)
	}
}
/*
TO DO: 

Section Shaping;
Area scope Controls,
	Mark scope Controls;
*/