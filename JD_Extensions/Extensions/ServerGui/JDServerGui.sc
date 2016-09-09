+Server {

	plotTree {|interval=0.5,left=10,top=50,width=200,height=375|
		var onClose, window = Window.new(name.asString + "Node Tree",
			Rect(left, top, width, height),
			scroll:true
		).front;
		window.view.hasHorizontalScroller_(false).background_(Color.grey(0.5));
		onClose = this.plotTreeView(interval, window.view, { defer {window.close}; });
		window.onClose = {
			onClose.value;
		};
	}
}
