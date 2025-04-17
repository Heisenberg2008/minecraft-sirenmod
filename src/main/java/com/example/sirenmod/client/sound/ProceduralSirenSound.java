package com.example.sirenmod.client.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3;

public class ProceduralSirenSound implements ISound {

    private final Vec3 position;
    private float volume;
    private float pitch;
    private boolean isStopping;

    private final float pitchRampSpeed;
    private final float volumeRampSpeed;

    public ProceduralSirenSound(Vec3 position, float initialPitch, float initialVolume, float rampSpeed) {
        this.position = position;
        this.pitch = initialPitch;
        this.volume = initialVolume;
        this.pitchRampSpeed = rampSpeed;
        this.volumeRampSpeed = rampSpeed;
        this.isStopping = false;
    }

    public static void play(double x, double y, double z) {
        Vec3 pos = new Vec3(x, y, z);
        Minecraft.getInstance().getSoundHandler().playSound(
            new ProceduralSirenSound(pos, 0.2f, 0.0f, 0.01f)
        );
    }

    public static void stop(double x, double y, double z) {
        // Placeholder - Add logic to stop sound
    }

    @Override
    public ResourceLocation getSoundLocation() {
        return new ResourceLocation("sirenmod", "siren_sound");
    }

    @Override
    public float getVolume() {
        return volume;
    }

    @Override
    public float getPitch() {
        return pitch;
    }

    @Override
    public float getXPosF() {
        return (float) position.xCoord;
    }

    @Override
    public float getYPosF() {
        return (float) position.yCoord;
    }

    @Override
    public float getZPosF() {
        return (float) position.zCoord;
    }

    @Override
    public boolean canRepeat() {
        return true;
    }

    @Override
    public int getRepeatDelay() {
        return 0;
    }

    @Override
    public AttenuationType getAttenuationType() {
        return AttenuationType.LINEAR;
    }

    public void update() {
        if (isStopping) {
            pitch = Math.max(0.2f, pitch - pitchRampSpeed);
            volume = Math.max(0.0f, volume - volumeRampSpeed);
        } else {
            pitch = Math.min(2.0f, pitch + pitchRampSpeed);
            volume = Math.min(1.0f, volume + volumeRampSpeed);
        }
    }

    public void stop() {
        isStopping = true;
    }

    public boolean isDonePlaying() {
        return volume <= 0.0f;
    }

    public Vec3 getPos() {
        return position;
    }
}
