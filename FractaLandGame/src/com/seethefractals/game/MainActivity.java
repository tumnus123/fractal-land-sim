package com.seethefractals.game;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.view.View.*;

public class MainActivity extends Activity
{
    private FractaLand al;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		
		final GameView gv = (GameView) findViewById(R.id.vGame);
		
		EditText etRadius = (EditText) findViewById(R.id.etRadius);
		final int iRadius = Integer.parseInt(etRadius.getText().toString());
		
		Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
		btnUpdate.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
						al = new FractaLand(iRadius,10);
						gv.setAL(al);
				}
				
			
		});
		
    }
}
