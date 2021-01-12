package com.example.assignment2.StepUtils;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.example.assignment2.listener.StepListener;

/**
 *  take the idea from dylan step_detector
 *  dylan provides algorithm
 *  @creator_dylan
 */


public class StepDetec implements SensorEventListener {
        float[] oriValues = new float[3];
        final int ValueNum = 4;
        float[] tempValue = new float[ValueNum];
        int tempCount = 0;
        boolean isDirectionUp = false;
        int continueUpCount = 0;
        int continueUpFormerCount = 0;
    // use Peak Statistics & Dynamic Threshold to decide whether it is a step or not
        boolean lastStatus = false;
        float peakOfWave = 0;
        float valleyOfWave = 0;
        long timeOfThisPeak = 0;
        long timeOfLastPeak = 0;
        long timeOfNow = 0;
        float gravityNew = 0;
        float gravityOld = 0;
        final float InitialValue = (float) 1.3;
        float ThreadValue = (float) 2.0;
        int TimeInterval = 250;
        private StepListener mStepListeners;

        @Override
        public void onSensorChanged(SensorEvent event) {
            for (int i = 0; i < 3; i++) {
                oriValues[i] = event.values[i];
            }
            gravityNew = (float) Math.sqrt(oriValues[0] * oriValues[0]
                    + oriValues[1] * oriValues[1] + oriValues[2] * oriValues[2]);
            detectorNewStep(gravityNew);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}

        public void initListener(StepListener listener) {
            this.mStepListeners = listener;
        }

        /*
         * If peak is detected & interval is fair & threshold is fair, then it should be identified as [one step]
         * */
        public void detectorNewStep(float values) {
            if (gravityOld == 0) {
                gravityOld = values;
            } else {
                if (detectorPeak(values, gravityOld)) {
                    timeOfLastPeak = timeOfThisPeak;
                    timeOfNow = System.currentTimeMillis();
                    if (timeOfNow - timeOfLastPeak >= TimeInterval
                            && (peakOfWave - valleyOfWave >= ThreadValue)) {
                        timeOfThisPeak = timeOfNow;
                        /*
                         * Remind that only 10 continuous steps are detected, the record will be updated, else nothing will be recorded
                         */
                        mStepListeners.countStep();
                    }
                    if (timeOfNow - timeOfLastPeak >= TimeInterval
                            && (peakOfWave - valleyOfWave >= InitialValue)) {
                        timeOfThisPeak = timeOfNow;
                        ThreadValue = peakValleyThread(peakOfWave - valleyOfWave);
                    }
                }
            }
            gravityOld = values;
        }
        /*
         * detect Peak using 4 conditions
         * */
        public boolean detectorPeak(float newValue, float oldValue) {
            lastStatus = isDirectionUp;
            if (newValue >= oldValue) {
                isDirectionUp = true;
                continueUpCount++;
            } else {
                continueUpFormerCount = continueUpCount;
                continueUpCount = 0;
                isDirectionUp = false;
            }
            if (!isDirectionUp && lastStatus
                    && (continueUpFormerCount >= 2 || oldValue >= 20)) {
                peakOfWave = oldValue;
                return true;
            } else if (!lastStatus && isDirectionUp) {
                valleyOfWave = oldValue;
                return false;
            } else {
                return false;
            }
        }
        /*
         * Calculate the Value Thread
         * */
        public float peakValleyThread(float value) {
            float tempThread = ThreadValue;
            if (tempCount < ValueNum) {
                tempValue[tempCount] = value;
                tempCount++;
            } else {
                tempThread = averageValue(tempValue, ValueNum);
                for (int i = 1; i < ValueNum; i++) {
                    tempValue[i - 1] = tempValue[i];
                }
                tempValue[ValueNum - 1] = value;
            }
            return tempThread;

        }

        public float averageValue(float[] value, int n) {
            float averagerVal = 0;
            for (int i = 0; i < n; i++) {
                averagerVal += value[i];
            }
            averagerVal = averagerVal / ValueNum;
            if (averagerVal >= 8) {
                averagerVal = (float) 4.3;
            }
            else if (averagerVal >= 7 && averagerVal < 8) {
                averagerVal = (float) 3.3;
            }
            else if (averagerVal >= 4 && averagerVal < 7) {
                averagerVal = (float) 2.3;
            }
            else if (averagerVal >= 3 && averagerVal < 4) {
                averagerVal = (float) 2.0;
            }
            else {
                averagerVal = (float) 1.3;
            }
            return averagerVal;
        }
    }