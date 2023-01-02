package net.tiffit.tconplanner.screen.buttons.modifiers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.tiffit.tconplanner.screen.PlannerScreen;
import net.tiffit.tconplanner.util.ModifierStack;
import net.tiffit.tconplanner.util.TranslationUtil;

public class ModLevelButton extends Button {

    private final PlannerScreen parent;
    private final int change;
    private boolean disabled = false;
    private Component tooltip;

    public ModLevelButton(int x, int y, int change, PlannerScreen parent) {
        super(x, y, 18, 17, new TextComponent(""), e -> {});
        this.parent = parent;
        this.change = change;
    }

    public void disable(Component tooltip){
        this.tooltip = tooltip;
        disabled = true;
    }

    public boolean isDisabled(){
        return disabled;
    }

    @Override
    public void renderButton(PoseStack stack, int mouseX, int mouseY, float p_230431_4_) {
        PlannerScreen.bindTexture();
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1f, 1f, 1f, disabled ? 0.5f : 1f);
        parent.blit(stack, x, y, change > 0 ? 176 : 194, disabled  ? 146 : 163, width, height);
        if(isHoveredOrFocused()){
            renderToolTip(stack, mouseX, mouseY);
        }
    }

    @Override
    public void onPress() {
        if(!disabled) {
            ModifierStack stack = parent.blueprint.modStack;
            stack.setIncrementalDiff(parent.selectedModifier.modifier, 0);
            if(change > 0)stack.push(parent.selectedModifier);
            else stack.pop(parent.selectedModifier);
            parent.refresh();
        }
    }

    @Override
    public void renderToolTip(PoseStack stack, int mouseX, int mouseY) {
        if(disabled) {
            parent.postRenderTasks.add(() -> parent.renderTooltip(stack, tooltip, mouseX, mouseY));
        }else{
            parent.postRenderTasks.add(() -> parent.renderTooltip(stack, TranslationUtil.createComponent(change < 0 ? "modifiers.removelevel" : "modifiers.addlevel").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)), mouseX, mouseY));
        }
    }

    @Override
    public void playDownSound(SoundManager handler) {
        if(!disabled)super.playDownSound(handler);
    }
}
