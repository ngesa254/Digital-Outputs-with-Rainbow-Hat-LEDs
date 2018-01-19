package com.droidmarvin.digitaloutputswithrainbowhatleds;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;

public class MainActivity extends Activity {

    private static final String BLUE_LED_PIN = "BCM26";

    private Gpio mGpio;

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

    }

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
