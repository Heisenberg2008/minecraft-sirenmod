package com.example.sirenmod.client.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.audio.ISound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class ProceduralSirenSound extends MovingSound {

    private final Vec3 position;
    private final float pitchRampSpeed;
    private final float volumeRampSpeed;
    private boolean isStopping;

    private float customPitch;
    private float customVolume;

    // Declare missing fields manually
    private int repeatDelay;
    private ISound.AttenuationType attenuationType;

    public ProceduralSirenSound(Vec3 position, float initialPitch, float initialVolume, float rampSpeed) {
        super(new ResourceLocation("sirenmod", "siren_sound"));
        this.position = position;
        this.customPitch = initialPitch;
        this.customVolume = initialVolume;
        this.pitchRampSpeed = rampSpeed;
        this.volumeRampSpeed = rampSpeed;
        this.isStopping = false;

        this.xPosF = (float) position.xCoord;
        this.yPosF = (float) position.yCoord;
        this.zPosF = (float) position.zCoord;

        this.repeat = true;
        this.repeatDelay = 0;
        this.attenuationType = ISound.AttenuationType.LINEAR;
    }

    @Override
    public void update() {
        if (isStopping) {
            customPitch = Math.max(0.2f, customPitch - pitchRampSpeed);
            customVolume = Math.max(0.0f, customVolume - volumeRampSpeed);
            if (customVolume <= 0.0f) {
                donePlaying = true;
            }
        } else {
            customPitch = Math.min(2.0f, customPitch + pitchRampSpeed);
            customVolume = Math.min(1.0f, customVolume + volumeRampSpeed);
        }
    }

    @Override
    public float getPitch() {
        return customPitch;
    }

    @Override
    public float getVolume() {
        return customVolume;
    }

    @Override
    public boolean canRepeat() {
        return repeat;
    }

    @Override
    public int getRepeatDelay() {
        return repeatDelay;
    }

    @Override
    public ISound.AttenuationType getAttenuationType() {
        return attenuationType;
    }

    public void stopFading() {
        isStopping = true;
    }

    // Optional: static play method
    public static void playAt(double x, double y, double z) {
        Vec3 pos = Vec3.createVectorHelper(x, y, z);
        Minecraft.getMinecraft().getSoundHandler().playSound(
            new ProceduralSirenSound(pos, 0.2f, 0.0f, 0.01f)
        );
    }
}
