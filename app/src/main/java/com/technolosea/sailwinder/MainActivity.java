package com.technolosea.sailwinder;

import android.app.Activity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class NavyLocationManager
{
    private int REQUEST_CODE_RECOVER_PLAY_SERVICES = 200;

    public boolean checkGooglePlayServices(Activity activity)
    {
        int conneection_status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity);
        if (conneection_status != ConnectionResult.SUCCESS)
        {
            GoogleApiAvailability.getInstance().getErrorDialog( activity, conneection_status,
                    REQUEST_CODE_RECOVER_PLAY_SERVICES).show();

            return false;
        }
        Toast toast = Toast.makeText(activity,
                "Successfully connected to Google location API",
                Toast.LENGTH_SHORT);

        toast.show();

        return true;

    }
}
