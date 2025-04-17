package com.example.sirenmod.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import com.example.sirenmod.client.sound.ProceduralSirenSound;

public class PacketPlaySirenSound implements IMessage {
    private double x, y, z;

    public PacketPlaySirenSound() {}
    public PacketPlaySirenSound(double x, double y, double z) {
        this.x = x; this.y = y; this.z = z;
    }

    @Override public void fromBytes(ByteBuf buf) { x = buf.readDouble(); y = buf.readDouble(); z = buf.readDouble(); }
    @Override public void toBytes(ByteBuf buf) { buf.writeDouble(x); buf.writeDouble(y); buf.writeDouble(z); }

    public static class Handler implements IMessageHandler<PacketPlaySirenSound, IMessage> {
        @Override
        public IMessage onMessage(PacketPlaySirenSound message, MessageContext ctx) {
            Minecraft.getMinecraft().func_152344_a(() -> {
                ProceduralSirenSound.play(message.x, message.y, message.z);
            });
            return null;
        }
    }
}