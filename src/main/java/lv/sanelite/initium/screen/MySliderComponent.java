package lv.sanelite.initium.screen;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

public class MySliderComponent extends AbstractSliderButton {
    private final String prefix;

    public MySliderComponent(int x, int y, int width, int height, Component prefix, double value) {
        super(x, y, width, height, prefix, value);
        this.prefix = String.valueOf(prefix);
        this.updateMessage();
    }

    @Override
        public void updateMessage() {

        }

        @Override
        public void applyValue() {
        }
        public void setValueFromMouse(double mouseX) {
            double newValue = (mouseX - this.x) / (double) this.width;
            this.value = Math.max(0.0, Math.min(1.0, newValue));
            this.applyValue();
            this.updateMessage();
        }
}
