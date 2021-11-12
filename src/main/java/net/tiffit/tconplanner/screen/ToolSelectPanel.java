package net.tiffit.tconplanner.screen;

import net.tiffit.tconplanner.api.TCTool;
import net.tiffit.tconplanner.screen.buttons.BannerWidget;
import net.tiffit.tconplanner.screen.buttons.PaginatedPanel;
import net.tiffit.tconplanner.screen.buttons.ToolTypeButton;
import net.tiffit.tconplanner.util.TranslationUtil;

import java.util.List;

public class ToolSelectPanel extends PlannerPanel{

    public ToolSelectPanel(int x, int y, int width, int height, List<TCTool> tools, PlannerScreen parent) {
        super(x, y, width, height, parent);
        addChild(new BannerWidget(5, 0, TranslationUtil.createComponent("banner.tools"), parent));

        PaginatedPanel<ToolTypeButton> toolsGroup = new PaginatedPanel<>(0, 23, 18, 18, 5, 3, 2,"toolsgroup", parent);
        addChild(toolsGroup);
        for (int i = 0; i < tools.size(); i++) {
            TCTool tool = tools.get(i);
            toolsGroup.addChild(new ToolTypeButton(i, tool, parent));
        }
        toolsGroup.refresh();
    }
}
