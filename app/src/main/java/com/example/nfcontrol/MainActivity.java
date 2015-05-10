package com.example.nfcontrol;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends RootActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("NFControl");
        getActionBar().setIcon(R.drawable.logo);

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
