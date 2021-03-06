package cyclist.view.tool;

import javafx.scene.image.Image;

import org.puremvc.java.multicore.patterns.mediator.Mediator;

import cyclist.view.component.View;
import cyclist.view.tool.mediator.instituteViewMediator;
import cyclist.view.tool.mediator.marketViewMediator;
import cyclist.view.tool.view.instituteView;
import cyclist.view.tool.view.marketView;

public class instituteViewTool implements Tool {

	@Override
	public Image getIcon() {
		return Resources.getIcon("table");
	}

	@Override
	public String getName() {
		return "instituteView";
	}

	@Override
	public View getView() {
		View view = new instituteView();
		view.setParam("Institute Form");
		return view;
	}
	
	@Override
	public Mediator getMediator() {
		return new instituteViewMediator();
	}

}