t = TempoClock(1).permanent_(true);

// a.areas.do{|a|a.name.postln};"";
// ~area_names = [\intro, \a, \b, \t1, \c, \outro];
// ~timeArrays = nil!~area_names.size;
~timeArrays = nil!2;

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
(
	var current = 0, target = 96;
	var func = {[4,2,8].wchoose([0.5,0.2,0.3])};
	var next = func.value;
	var arr = [];

	while ({ (current + next) < target}, {
		
		current = current + next;

		if ((current + next) >= target) {
			next = target - current;
		};
		arr = arr.add(next);
		next = func.value;
	});

	~timeArrays[0] =  arr;
)
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
(
	var current = 0, target = 128;
	var func = { exprand(16,32.0)};
	var next = func.value;
	var arr = [];

	while ({ (current + next) < target}, {
		
		current = current + next;

		if ((current + next) >= target) {
			next = target - current;
		};
		arr = arr.add(next);
		next = func.value;
	});

	~timeArrays[1] =  arr;
)
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
(
	var current = 0, target = 128;
	var num = 6;
	var func = { 1.gauss(0.1).round(0.05)};
	var next = func.value;
	var arr = [];

	arr = func!num;
	~timeArrays[1] =  ([1.2] ++ arr).normalizeSum * target;
)

a = AreaPlan(t);
a.make(
	[\intro, ~timeArrays[0]],
	[\a, ~timeArrays[1]],
	);
