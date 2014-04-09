package com.seethefractals.game;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.view.View.*;
import android.widget.SeekBar.*;

public class MainActivity extends Activity
{
    private GameView gv;
	EditText etDist;
	SeekBar sbDist;
	private int iRadius;
	private float fSpacing;
	private float fMoveDist;
	
	
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
		etDist = (EditText) findViewById(R.id.etDist);
		
		iRadius = Integer.parseInt(etRadius.getText().toString());
		fSpacing = Float.parseFloat(etSpacing.getText().toString());
		fMoveDist = Float.parseFloat(etDist.getText().toString());
		
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
		
		sbDist = (SeekBar) findViewById(R.id.sbDist);
		sbDist.setMax(50);
		
		sbDist.setProgress(0);
		sbDist.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
				@Override
                public void onProgressChanged(SeekBar seekBar, int progress,
											  boolean fromUser) {
                    progress = progress + 1;
				    etDist.setText(Float.toString(progress));
                    //fl.setRadius(progress);
					fMoveDist = Float.parseFloat(progress+"");
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
					makeToast(sbDist.getProgress() + "");
					fl.moveTo((sbDist.getProgress())*-1,0f,0f); // TEST
					
				}
				
				
			});
    }
	
	private void makeToast(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
}
