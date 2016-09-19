
//Rhytmic Structures
// (ir)regular metred scheme (metre!bars!phrases!sub_sections)
// widely spaced Landmarks
// Large number of marks with underlying function (Pick Points)
// Combination of schemes

// MusicalStructureIdeas
// Signal -> Response (Large Scale -> variable scale)

// Creating Algorithmic Structures which 
	//Performers can work within/ manipulate utilise

// Structure
// Dynamically/Timbrally: 
// Build To Points 
// Fade From Points
// Sudden Alternation Between States/Types
// Smooth Alternation Between States/Types
// apply asr envelope to intensity for small gestures

// Texturally:

// At Marks (Transitions)
// Sudden Cut
// Crossfade
// changing proportion of alternation
// Significant Gesture 

// Gesture Examples
// Gradual Granular appearance
// sustained tone/texture
// "Melody" -> focal line (algorhythmic or improvised: acoutic/electronic)
// algo_melo_rhythmic pattern
// cascade of swells/fades (freq, amp, distortion)
// swirl -> sound in/out/around direction/amp/timbre

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------

// CLASSES
// Area
// Contain Information about Current Area:
	// clocks / tempo
	// metre
	// marks / gestures

// Marks
	// .activate (trigger count until evaluation)
	// .untilNext
	// .until('area.mark') or until ('mark') (assume current Area)
	// function.atNext

{ 1.postln }.onMark(mark: 1, accuracy: 0)
{ 1.postln }.onNextMark(accuracy: 0)

// If Area Dur = 64
Mark('mark.1').on({1.postln}, accuracy: -8 , norm: false)
Mark('mark.1').on({1.postln}, accuracy: -1/8, norm: true)

//shorthands
'mark.1'.on({1.postln}, accuracy: -1/8, norm: true)
\mark_1.on({1.postln}, accuracy: -1/8, norm: true)

// Pseudo-class
m = ();
m.new = {|self,name, clock|
	//init
	~clock = clock ?? {TempoClock.default};

	m[name] = ();
	m[name].use({

		~funcs = List.new;

		~throwMarker = {|self, time = 16, func|
			~time = time;
			~beatZero = ~clock.postln.beats;
			~clock.schedAbs((~clock.beats + time).round, func);
		};

		~until = {~time - (~clock.beats - ~beatZero)};

	});
};


m.new(\mark_1, t.default)

m[\mark_1]

m[\mark_1].throwMarker(16, {
	m[\mark_1].funcs.do(_.value);
	m[\mark_1].funcs = List.new;
	nil;
});
m[\mark_1].funcs.add({1.postln})
m[\mark_1].funcs.add({\noiseLine.clear})



// MARK USAGE
// NAMED OR NAMELESS
//Ndef
Mark('one.two', {

});

Mark.throwLandmarks( 
	name: \section1, 
	constructionArray: 
		4!4!4!4 
		// Method 1: explicit set
		({[0.5,1,2].choose}!rand(2,10)).normalizeSum * 128 
		// Method 2 : normalise
		{
			var current = 0, target = 30;
			// opt: 1 -> clip
			// opt: 2 -> avg (normalise Sum)
			while({current < target}, {
				var inrease = [0.5,1,2].choose;
				if ()
				current = current + increase;
			});
		} 
		// Method 3 : fill until full
		// Method 4 : with beginning and ending ([begin] ++ func ++ [end])
	);

//USAGE

//setup

\Example Outline
	\intro, "vague textures swelling",
	\spaced_signals, "spaced large perc-shaped gestures",
	\relentless, -> builds to ->
	\change_of_durection, \maintain_energy,
	\build_layers,
	\outro (fade or sudden sustained sound-> disintegrate out)

\Areas
	\t_intro,
	\t_signals,
	\t_relentless,
	\t_patterns,
	\t_outro

For each area:
	phrase: return current phrase
	metre : return current metres

	Array / ArrayGeneratingFunction

Areas:
	\name,
	\Layouts -> [Layout],

	methods:
		new

Layout
	\name,
	\gen_func,
	methods:



//-----------------------------------------------------------------------
//-----------------------------------------------------------------------

Structural envelope


NodeProxies/ Ndef

with Envelope / can be used arbritrali


// EXTEND
Ndef(\area).kr // between 0 and one 
Ndef(\area).kr // area Control Functions