

package com.android.inclinometer_library.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.android.inclinometer_library.backgroundservice.HromatkaLog;
import com.android.inclinometer_library.backgroundservice.HromatkaService;
import com.android.inclinometer_library.backgroundservice.HromatkaServiceApi;


public class HromatkaServiceManager {
    private final String TAG = this.getClass().getSimpleName();
    private HromatkaServiceBindApi callback = null;
    private HromatkaServiceApi hromatkaServiceApi = null;
    /**
     * Inner class that Android will notify when the requesting activity has been bound to the
     * service
     */
    private ServiceConnection hromatkaServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder binder) {
            HromatkaLog.getInstance().enter(TAG);
            hromatkaServiceApi = (HromatkaServiceApi) binder;

            /* notify the callback class that we are now bound to the service */
            callback.onHromatkaServiceBind();
            HromatkaLog.getInstance().exit(TAG);
        }

        public void onServiceDisconnected(ComponentName className) {
            HromatkaLog.getInstance().enter(TAG);
            hromatkaServiceApi = null;
            HromatkaLog.getInstance().exit(TAG);
        }
    };

    /**
     * method to bind the calling activity to HromatkaService
     * @param context        Android context of the calling activity
     * @param serviceBindApi Class to callback when the service is bound
     */
    public void bindServiceConnection(Context context, HromatkaServiceBindApi serviceBindApi) {
        HromatkaLog.getInstance().enter(TAG);
        callback = serviceBindApi;

        Intent intent = new Intent(context, HromatkaService.class);
        context.bindService(intent, hromatkaServiceConnection, Context.BIND_AUTO_CREATE);
        HromatkaLog.getInstance().exit(TAG);
    }

    /**
     * Unbind this context from HromatkaService
     * @param context Android context of the calling activity
     */
    public void unbindServiceConnection(Context context) {
        try {
            context.unbindService(hromatkaServiceConnection);
        }
        catch (IllegalArgumentException iae) {
            HromatkaLog.getInstance().logError(TAG, "Failed to unbind from service: " + iae.getLocalizedMessage());
        }
    }

    public HromatkaServiceApi getHromatkaServiceApi() {
        HromatkaLog.getInstance().logVerbose(TAG, "hromatkaServiceApi = " + hromatkaServiceApi);
        return hromatkaServiceApi;
    }
}
