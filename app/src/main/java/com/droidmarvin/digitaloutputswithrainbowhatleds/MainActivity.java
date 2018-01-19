package com.droidmarvin.digitaloutputswithrainbowhatleds;

import android.app.Activity;
import android.os.Bundle;

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

        try {

            mGpio = service.openGpio(BLUE_LED_PIN);

        } catch (IOException e){

            throw new IllegalStateException(BLUE_LED_PIN + "gpio bus could not open.",e);
        }
    }
}
