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

import android.app.Application;
import android.content.Intent;

public class NotifyApplication extends Application {
    public void onCreate() {
        super.onCreate();

        // Start up the background beacon monitoring service
        Intent myIntent = new Intent(this, MonitorService.class);
        startService(myIntent);
    }
}
