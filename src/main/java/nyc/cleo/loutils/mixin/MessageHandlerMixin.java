package nyc.cleo.loutils.mixin;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.message.MessageHandler;
import net.minecraft.network.message.*;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(MessageHandler.class)
public class MessageHandlerMixin {
    private long lastMessage = 0L;
    @Inject(method = "onChatMessage", at = @At("RETURN"))
    public void onChatMessage(SignedMessage message, MessageType.Parameters params, CallbackInfo ci) {
        if (message.getContent().contains(Text.of("chat test"))) {
            send();
        }
    }
    @Inject(method = "onGameMessage", at  = @At("RETURN"))
    public void onChatMessage(Text message, boolean overlay, CallbackInfo ci) {
        if (message.contains(Text.of("chat test"))) {
            send();
        }
    }
    private void send() {
        if (lastMessage > System.currentTimeMillis() - 1000) {
            return;
        }
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.sendChatMessage("cat test <3", null);
        lastMessage = 0L;
    }
}
