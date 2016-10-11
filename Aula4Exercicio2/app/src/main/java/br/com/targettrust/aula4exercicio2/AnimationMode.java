package br.com.targettrust.aula4exercicio2;

/**
 * Created by marcelo on 11/10/16.
 */

public enum AnimationMode {
    X_TRANSLATION("X Translation", 1),
    Y_TRANSLATION("Y Translation", 2),
    SCALE("Scale", 3),
    FADE_IN("Fade in", 4),
    HYPERSPACE_IN("Hyperspace in", 5),
    HYPERSPACE_OUT("Hyperspace out", 6),
    WAVE_SCALE("Wave scale", 7),
    PUSH_LEFT_IN("Push left in", 8),
    PUSH_LEFT_OUT("Push left out", 9),
    PUSH_UP_IN("Push up in", 10),
    PUSH_UP_OUT("Push up out", 11),
    SHAKE("Shake", 12),
    ROTATE("Rotate", 13);

    private String description;
    private int id;

    AnimationMode(String description, int id) {
        this.description = description;
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public static AnimationMode getAnimationModeFromId(int id) {
        for (AnimationMode animationMode :
                AnimationMode.values()) {
            if (animationMode.getId() == id) {
                return animationMode;
            }
        }
        return null;
    }
}
