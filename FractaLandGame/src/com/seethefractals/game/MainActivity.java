package com.seethefractals.game;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.view.View.*;
import android.widget.SeekBar.*;

public class MainActivity extends Activity
{
    private FractaLand al;
	private int iRadius;
	private float fSpacing;
	//private int iMaxSpace;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		
		final GameView gv = (GameView) findViewById(R.id.vGame);
		
		final EditText etRadius = (EditText) findViewById(R.id.etRadius);
		final EditText etSpacing = (EditText) findViewById(R.id.etSpacing);
		
		iRadius = Integer.parseInt(etRadius.getText().toString());
		fSpacing = Float.parseFloat(etSpacing.getText().toString());
		
		Button btnUpdate = (Button) findViewById(R.id.btnDensify);
		btnUpdate.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					fSpacing = fSpacing / 2;
					iRadius = iRadius * 2;
					al = new FractaLand(iRadius,fSpacing);
					gv.setAL(al);
				}
				
			
		});
		
		SeekBar sbRadius = (SeekBar) findViewById(R.id.sbRadius);
		sbRadius.setMax(50);
		sbRadius.setProgress(0);
		sbRadius.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
				@Override
                public void onProgressChanged(SeekBar seekBar, int progress,
											  boolean fromUser) {
                    progress = progress + 1;
				    etRadius.setText(Integer.toString(progress));
                    iRadius = progress;
					al = new FractaLand(iRadius, fSpacing);
					gv.setAL(al);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
		});
		
		SeekBar sbSpacing = (SeekBar) findViewById(R.id.sbSpacing);
		sbSpacing.setMax(50);
		sbSpacing.setProgress(0);
		sbSpacing.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
				@Override
                public void onProgressChanged(SeekBar seekBar, int progress,
											  boolean fromUser) {
                    progress = progress + 2;
				    etSpacing.setText(Integer.toString(progress));
                    fSpacing = progress;
					al = new FractaLand(iRadius, fSpacing);
					gv.setAL(al);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
			});
			
			Button btnMove = (Button) findViewById(R.id.btnMove);
		btnMove.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// pass the start and end values in
					
				}
				
				
			});
    }
}
