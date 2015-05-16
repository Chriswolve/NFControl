package com.example.nfcontrol;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nfcontrol.Clases.BluetoothDroid;


public class MainActivity extends RootActivity {

    private TextView tvTitle;
    private Button btnGetPairedDevices;
    private ListView lvPairedDevices;

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("NFControl");
        getActionBar().setIcon(R.drawable.logo);


        tvTitle = (TextView) findViewById(R.id.lbl_selecciona);
        btnGetPairedDevices = (Button) findViewById(R.id.btn_search);
        lvPairedDevices = (ListView) findViewById(R.id.lv_devices);

        BluetoothDroid.getInstance(MainActivity.this);

        // Obtenemos dispositivos apareados y los mostramos en un listview
        btnGetPairedDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lvPairedDevices.setAdapter(null);
                BluetoothDroid.Disconect();

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        BluetoothDroid.getInstance(MainActivity.this).getNameBtDevices()
                );

                lvPairedDevices.setAdapter(adapter);
            }
        });
        // Nos conectamos con el dispositivo que elijamos del listView

        lvPairedDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {

                final String device_name = lvPairedDevices.getItemAtPosition(position).toString();
                lvPairedDevices.setAdapter(null);

                Intent i =  new Intent(MainActivity.this,DeviceActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("NOMBRE", device_name);
                Log.d("POSITION", position+" ");

                i.putExtra("DEVICE", position);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id)
        {

            case R.id.action_about:
                Intent i =  new Intent(MainActivity.this,AboutActivity.class);
                startActivity(i);
                break;
            case R.id.action_settings:
                /*Intent i =  new Intent(MainActivity.this,MainActivity.class);
                startActivity(i);*/
                break;
            case R.id.action_exit:
                //System.exit(0);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
