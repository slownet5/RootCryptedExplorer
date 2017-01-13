package com.cryptopaths.cryptofm.startup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cryptopaths.cryptofm.R;
import com.cryptopaths.cryptofm.startup.adapters.PagerAdapter;
import com.cryptopaths.cryptofm.utils.ActionHandler;

public class PreStartActivity extends AppCompatActivity {
    //app compat activity can also act as FragmentActivity

    ViewPager viewpager;
    PagerAdapter pagerAdapter;
    int count;
    Button btn=(Button)findViewById(R.id.pre_start_skip_button);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_start);
        viewpager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter padapter = new PagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(padapter);
        count=pagerAdapter.getCount();
        if(count==4){
            btn.setText("Next");
        }
    }

    @ActionHandler(layoutResource = R.id.pre_start_skip_button)
    public void onSkipButtonClick(View v){
        //start the intent for the password activity
        Intent intent=new Intent(this,InitActivity.class);
        //start the activity but not let the user get back to this activity
        startActivityForResult(intent,1);
    }

}


