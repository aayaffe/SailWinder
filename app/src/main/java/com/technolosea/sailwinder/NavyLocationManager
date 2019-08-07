package com.technolosea.sailwinder;

import android.app.Activity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class NavyLocationManager
{
    private int REQUEST_CODE_RECOVER_PLAY_SERVICES = 200;

    private Activity manager_activity;

    public NavyLocationManager(Activity _manager_activity)
    {
        this.manager_activity = _manager_activity;
    }

    public boolean checkGooglePlayServices()
    {
        int conneection_status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this.manager_activity);
        if (conneection_status != ConnectionResult.SUCCESS)
        {
            GoogleApiAvailability.getInstance().getErrorDialog(this.manager_activity, conneection_status,
                    REQUEST_CODE_RECOVER_PLAY_SERVICES).show();

            return false;
        }
        Toast toast = Toast.makeText(this.manager_activity,
                "Successfully connected to Google location API",
                Toast.LENGTH_SHORT);

        toast.show();

        return true;

    }
}
