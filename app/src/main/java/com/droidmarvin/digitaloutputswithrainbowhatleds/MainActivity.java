package com.droidmarvin.digitaloutputswithrainbowhatleds;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.things.pio.PeripheralManagerService;

public class MainActivity extends Activity {

    private static final String BLUE_LED_PIN = "BCM26";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PeripheralManagerService service = new PeripheralManagerService();
    }
}
