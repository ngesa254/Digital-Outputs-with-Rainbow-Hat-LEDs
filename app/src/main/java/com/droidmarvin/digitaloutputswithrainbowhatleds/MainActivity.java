package com.droidmarvin.digitaloutputswithrainbowhatleds;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

    private static final String BLUE_LED_PIN = "BCM26";

    private Gpio mGpio;

    private Handler ledToggleHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PeripheralManagerService service = new PeripheralManagerService();

        //open gpio bus
        try {
            mGpio = service.openGpio(BLUE_LED_PIN);
        } catch (IOException e){
            throw new IllegalStateException(BLUE_LED_PIN + "gpio bus could not open.",e);
        }

        //set direction and active type
        try {
            mGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_HIGH);
            mGpio.setActiveType(Gpio.ACTIVE_HIGH);
        }catch (IOException e){
            throw new IllegalStateException(BLUE_LED_PIN + "gpio bus could not get configured",e);

        }

        ledToggleHandler = new Handler(Looper.getMainLooper());

    }

    @Override
    protected void onStart() {
        super.onStart();
        ledToggleHandler.post(toggleLed);
    }

    private final Runnable toggleLed = new Runnable() {
        @Override
        public void run() {

            boolean ledOn;
            try {
                ledOn = mGpio.getValue();
            }catch (IOException e){
                throw new IllegalStateException(BLUE_LED_PIN + " cannot be read.", e);
            }

            try {
                if(ledOn){
                    mGpio.setValue(false);
                }else {
                    mGpio.setValue(true);
                }
            }catch (IOException e){
                throw new IllegalStateException (BLUE_LED_PIN + " cannot be written.", e);
            }
            ledToggleHandler.postDelayed(this, TimeUnit.SECONDS.toMillis(1));

        }
    };

    @Override
    protected void onStop() {
        ledToggleHandler.removeCallbacks(toggleLed);
        super.onStop();
    }

    //close the ledpin
    @Override
    protected void onDestroy() {

        try {
            mGpio.close();
        }catch (IOException e){
            Log.e("TUT", BLUE_LED_PIN + " gpio bus could not close", e);
        }

        super.onDestroy();
    }
}
