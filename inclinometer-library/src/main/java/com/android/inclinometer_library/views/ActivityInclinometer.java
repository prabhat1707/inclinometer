

package com.android.inclinometer_library.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.android.inclinometer_library.R;
import com.android.inclinometer_library.backgroundservice.HromatkaLog;
import com.android.inclinometer_library.backgroundservice.HromatkaServiceApi;


public class ActivityInclinometer extends AppCompatActivity implements HromatkaServiceBindApi {
    private final String TAG = this.getClass().getSimpleName();
    private HromatkaServiceManager hromatkaServiceManager = new HromatkaServiceManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HromatkaLog.getInstance().enter(TAG);

        /* bind to the service during onCreate().  Once we have successfully bound to the
         * service, we can then display the page
         */
        hromatkaServiceManager.bindServiceConnection(ActivityInclinometer.this, this);

        HromatkaLog.getInstance().exit(TAG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HromatkaLog.getInstance().enter(TAG);
        PageInclinometer.getInstance().onDestroy(this, getHromatkaServiceApi());
        hromatkaServiceManager.unbindServiceConnection(ActivityInclinometer.this);
        HromatkaLog.getInstance().exit(TAG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        HromatkaLog.getInstance().enter(TAG);
        getMenuInflater().inflate(R.menu.menu_inclinometer, menu);
        HromatkaLog.getInstance().exit(TAG);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        HromatkaLog.getInstance().enter(TAG);
        int id = item.getItemId();

        switch(id) {
            case R.id.action_calibrate:
                Intent intent = new Intent(this, ActivityCalibrate.class);
                this.startActivity(intent);
                break;

            default:
                throw new AssertionError("Unhandled option: " + id);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Callback method that HromatkaServiceManager will call once this activity is bound to
     * HromatkaService
     */
    @Override
    public void onHromatkaServiceBind() {
        HromatkaLog.getInstance().enter(TAG);
        setContentView(R.layout.page_inclinometer);
        PageInclinometer.getInstance().onCreate(this, getHromatkaServiceApi());

        Intent intent = new Intent(this, ActivityCalibrate.class);
        this.startActivity(intent);

        HromatkaLog.getInstance().exit(TAG);
    }

    private HromatkaServiceApi getHromatkaServiceApi() {
        return hromatkaServiceManager.getHromatkaServiceApi();
    }
}
