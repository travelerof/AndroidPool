package com.hyg.hvideo;

import android.content.Context;

/**
 * @Author 韩永刚
 * @Date 2021/04/02
 * @Desc
 */
public class HmMediaPlayer {

    private HmMediaPlayer(ControllerOptions controllerOptions) {

    }

    public static class Builder {
        ControllerOptions mControllerOptions;

        public Builder(Context context) {
            mControllerOptions = new ControllerOptions();
            mControllerOptions.controllerHeight = 40;
            mControllerOptions.progressHeight = 1;
        }

        public Builder setPlayerBackgroundColor(int backgroundColor) {
            mControllerOptions.playerBackgroundColor = backgroundColor;
            return this;
        }

        public Builder setControllerHeight(int controllerHeight) {
            mControllerOptions.controllerHeight = controllerHeight;
            return this;
        }

        public Builder setControllerBackgroundColor(int backgroundColor) {
            mControllerOptions.controllerBackgroundColor = backgroundColor;
            return this;
        }

        public Builder setProgressHeight(int progressHeight) {
            mControllerOptions.progressHeight = progressHeight;
            return this;
        }

        public Builder setProgressReloadColor(int reloadColor) {
            mControllerOptions.progressReloadColor = reloadColor;
            return this;
        }

        public Builder setProgressDefaultColor(int defaultColor) {
            mControllerOptions.progressDefaultColor = defaultColor;
            return this;
        }

        public Builder setProgressPlayColor(int playColor) {
            mControllerOptions.progressPlayColor = playColor;
            return this;
        }

        public Builder setFastForward(boolean fastForward) {
            mControllerOptions.allowFastForward = fastForward;
            return this;
        }

        public Builder setFastForwardMillisecond(long millisecond) {
            mControllerOptions.fastForwardMillisecond = millisecond;
            return this;
        }

        public Builder setRewind(boolean rewind) {
            mControllerOptions.allowRewind = rewind;
            return this;
        }

        public Builder setRewindMillisecond(long millisecond) {
            mControllerOptions.rewindMillisecond = millisecond;
            return this;
        }

        public Builder setAllowSpeed(boolean allowSpeed) {
            mControllerOptions.allowSpeed = allowSpeed;
            return this;
        }

        public Builder setSpeeds(@Speed int[] speeds) {
            mControllerOptions.speed = speeds;
            return this;
        }

        public Builder setScreenChanged(boolean screenChanged) {
            mControllerOptions.allowScreenChanged = screenChanged;
            return this;
        }

        public Builder setClickDouble(boolean clickDouble) {
            mControllerOptions.allowClickDouble = clickDouble;
            return this;
        }

        public Builder setAutoPlayNext(boolean autoPlayNext) {
            mControllerOptions.allowAutoPlayNext = autoPlayNext;
            return this;
        }

        public Builder setShowNext(boolean showNext) {
            mControllerOptions.allowShowNext = showNext;
            return this;
        }

        public HmMediaPlayer create() {
            return new HmMediaPlayer(mControllerOptions);
        }
    }
}
