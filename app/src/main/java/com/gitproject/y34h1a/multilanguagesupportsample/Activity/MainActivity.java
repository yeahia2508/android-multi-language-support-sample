package com.gitproject.y34h1a.multilanguagesupportsample.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.gitproject.y34h1a.multilanguagesupportsample.Preference.PrefData;
import com.gitproject.y34h1a.multilanguagesupportsample.R;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Context con;
    ImageView ivLangFlag;
    PrefData prefData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        con = getApplicationContext();
        prefData = new PrefData(con);

        setToolbar();
        setFloatingActionBar();

        ivLangFlag = (ImageView) findViewById(R.id.ivLangFlag);
        setLangFlagImage();
    }

    private void setLangFlagImage() {
        switch (prefData.getCurrentLanguage()){
            case "eng":
                ivLangFlag.setImageResource(R.drawable.en);
                break;
            case "bn":
                ivLangFlag.setImageResource(R.drawable.bn);
                break;
            case "de":
                ivLangFlag.setImageResource(R.drawable.de);
                break;
            case "fr":
                ivLangFlag.setImageResource(R.drawable.fr);
                break;
            default:
                break;
        }
    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
    }

    private void setFloatingActionBar(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lang_select_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.eng:
                setLanguage("eng");
                break;

            case R.id.bn:
                setLanguage("bn");
                break;

            case R.id.fr:
                setLanguage("fr");
                break;

            case R.id.de:
                setLanguage("de");
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setLanguage(String language){

        //setting new configuration
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        con.getApplicationContext().getResources().updateConfiguration(config, null);

        //store current language in prefrence
        prefData.setCurrentLanguage(language);

        //With new configuration start activity again
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void sendMail(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, "yeahia.arif@gmail.com");
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // reset
        prefData.setCurrentLanguage("eng");
    }
}
