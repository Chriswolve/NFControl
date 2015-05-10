package com.example.nfcontrol;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.nfcontrol.Clases.BluetoothDroid;


public class DeviceActivity extends RootActivity {

    private int position;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        setTitle("NFControl");
        getActionBar().setIcon(R.drawable.logo);



        BluetoothDroid.LoadBT();
        position = (int)getIntent().getExtras().getSerializable("DEVICE");
        Log.d("POSITION", position + " ");
        pd = new ProgressDialog(DeviceActivity.this);
        pd.setMessage(getString(R.string.connecting));
        pd.setCancelable(false);
        pd.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                BluetoothDroid.getInstance(DeviceActivity.this).connectDevice(position);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Entro", position+" ");
                        pd.dismiss();

                    }
                });
            }
        }).start();

        TextView textview =  (TextView) findViewById(R.id.lbl_device_devicename);
        Bundle bundle = this.getIntent().getExtras();

        textview.setText( bundle.getString("NOMBRE"));




    }

    public void enviar1(View view){

        BluetoothDroid.getInstance(getApplicationContext()).enviarPaqBT("1");
    }

    public void enviar0(View view){

        BluetoothDroid.getInstance(getApplicationContext()).enviarPaqBT("0");
    }
    public void Desconectarme(View view){

        BluetoothDroid.getInstance(DeviceActivity.this).connectDevice(100);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_device, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
