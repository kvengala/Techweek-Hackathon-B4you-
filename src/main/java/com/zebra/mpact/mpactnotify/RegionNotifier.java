//============================================================================
//
//   Zebra Technologies  - MPACT
//
//  Copyright (c) 2015  Zebra Technologies . All Rights Reserved.
//
//  All information contained herein is the property of Zebra Technologies.  All software
//  within this document, of the software, is distributed on an "AS IS" BASIS, WITHOUT
//  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//
//============================================================================
package com.zebra.mpact.mpactnotify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;

import com.zebra.mpact.mpactclient.MPactBeacon;
import com.zebra.mpact.mpactclient.MPactBeaconType;
import com.zebra.mpact.mpactclient.MPactClient;
import com.zebra.mpact.mpactclient.MPactClientConsumer;
import com.zebra.mpact.mpactclient.MPactClientNotifier;
import com.zebra.mpact.mpactclient.MPactLogNotifier;
import com.zebra.mpact.mpactclient.MPactProximity;
import com.zebra.mpact.mpactclient.MPactServerInfo;
import com.zebra.mpact.mpactclient.MPactTag;

public class RegionNotifier implements MPactClientNotifier {
    private static RegionNotifier client = null;
    private Context context;
    private int regionState = MPactClientNotifier.OUTSIDE;

    private MainActivity mainActivity = null;

    public static RegionNotifier getInstanceForApplication(Context context) {
        if (client == null) {
            client = new RegionNotifier(context.getApplicationContext());
        }
        return client;
    }

    protected RegionNotifier(Context context) {
        this.context = context;
    }

    @Override
    public void didDetermineClosestTag(MPactTag mpactTag) {
        String tagid = mpactTag.getTagID();
        //mPactServerInfo.setUseHTTPS("http://54.166.9.255/mpact/mpact/");

        if(tagid.equals("0x237208"))
        {
            notify(" get exiting offers in furniture region");


        }
        else if(tagid.equals("0x237d40"))
        {notify("get exiting offers in Electronics");}
    }

    @Override
    public void didDetermineState(int state) {
        if(state == MPactClientNotifier.INSIDE) {
//             notify(" you are in the region  ");

        } else if(state == MPactClientNotifier.OUTSIDE) {
  //          notify("You are not in the MPact region");
        }
        updateActivityRegionState(state);
        regionState = state;
    }

    @Override
    public void didDetermineState(int state, Integer major, Integer minor) {

    }

    @Override
    public void didChangeIBeaconUUID(String uuid) {

    }

    @Override
    public void didChangeBeaconType(MPactBeaconType beaconType) {
    }

    @Override
    public void didChangeProximityRange(MPactProximity proximityRange) {

    }

    // This method only uses one message id which means there will be at most one notification.
    // Multiple calls to this method will only display the most current message.
    private void notify(String msg) {
        // Generate a notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.iothand)
                .setContentTitle("B4-You Notifier")
                .setContentText(msg);
        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());

        // Turn on the screen if the device is sleeping
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK|PowerManager.ACQUIRE_CAUSES_WAKEUP, "Mpact alert");
        wl.acquire(3000);
        wl.release();
    }

    private void updateActivityRegionState(int state) {
        if(mainActivity == null) {
            return;
        }
        if(state == MPactClientNotifier.INSIDE) {
            //mainActivity.setLabelText(R.id.textViewRegionState, "Inside");
        } else if(state == MPactClientNotifier.OUTSIDE) {
            //mainActivity.setLabelText(R.id.textViewRegionState, "Outside");
        }

    }
    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        updateActivityRegionState(regionState);
    }

}
