package com.seethefractals.game;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.view.View.*;
import android.widget.SeekBar.*;

public class MainActivity extends Activity
{
    private EditText etDistX;
	private SeekBar sbDistX;
	private float fMoveDistX;
	private EditText etDistY;
	private SeekBar sbDistY;
	private float fMoveDistY;
	private EditText etMag;
	private SeekBar sbMag;
	private float fMoveMag;
	
	private GameView gv;
	private int iRadius;
	private float fSpacing;
	
	@Override
	public void onDestroy(){
		// clean up
		gv.destroyDrawingCache();
		this.finish();
		
	}
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		
		gv = (GameView) findViewById(R.id.vGame);
		
		final FractaLand fl = gv.getFractaLand();
		
		final EditText etRadius = (EditText) findViewById(R.id.etRadius);
		final EditText etSpacing = (EditText) findViewById(R.id.etSpacing);
		
		iRadius = Integer.parseInt(etRadius.getText().toString());
		fSpacing = Float.parseFloat(etSpacing.getText().toString());
				
		Button btnUpdate = (Button) findViewById(R.id.btnDensify);
		btnUpdate.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					fl.setRadius(fl.getRadius()*2);
					fl.setSpacing(fl.getSpacing()/2);
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
                    fl.setRadius(progress);
					iRadius = progress;
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
                    fl.setSpacing(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
			});
		
		etMag = (EditText) findViewById(R.id.etMag);
		fMoveMag = Float.parseFloat(etMag.getText().toString());
		etMag.setText(fMoveMag+"");
		sbMag = (SeekBar) findViewById(R.id.sbMag);
		sbMag.setMax(4);
		sbMag.setProgress(1);
		sbMag.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
				@Override
                public void onProgressChanged(SeekBar seekBar, int progress,
											  boolean fromUser) {
                    
					switch (progress) {
						case 0: etMag.setText("0.5");
								break;
						case 1: etMag.setText("1.0");
								break;
						case 2: etMag.setText("2.0");
							break;
						case 3: etMag.setText("4.0");
							break;
						case 4: etMag.setText("8.0");
							break;
						default: etMag.setText("1.0");
							break;
					}							  
					fMoveMag = Float.parseFloat(etMag.getText().toString());
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
			});
			
		etDistX = (EditText) findViewById(R.id.etDistX);
		fMoveDistX = Float.parseFloat(etDistX.getText().toString());
		etDistX.setText(fMoveDistX+"");
		sbDistX = (SeekBar) findViewById(R.id.sbDistX);
		sbDistX.setMax(100);
		sbDistX.setProgress(50);
		sbDistX.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
				@Override
                public void onProgressChanged(SeekBar seekBar, int progress,
											  boolean fromUser) {
                    progress = progress - 50;
				    etDistX.setText(Float.toString(progress));
                    fMoveDistX = Float.parseFloat(etDistX.getText().toString());
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
			});
			
		etDistY = (EditText) findViewById(R.id.etDistY);
		fMoveDistY = Float.parseFloat(etDistY.getText().toString());
		etDistY.setText(fMoveDistY+"");
		sbDistY = (SeekBar) findViewById(R.id.sbDistY);
		sbDistY.setMax(100);
		sbDistY.setProgress(50);
		sbDistY.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
				@Override
                public void onProgressChanged(SeekBar seekBar, int progress,
											  boolean fromUser) {
                    progress = progress - 50;
				    etDistY.setText(Float.toString(progress));
                    fMoveDistY = Float.parseFloat(etDistY.getText().toString());
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
					fl.moveTo(fMoveDistX,fMoveDistY,fMoveMag); 
					
				}
				
				
			});
    }
	
	private void makeToast(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
}
