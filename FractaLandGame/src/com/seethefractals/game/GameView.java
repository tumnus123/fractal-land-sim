package com.seethefractals.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.*;
import android.graphics.*;
import android.widget.*;

public class GameView extends SurfaceView
{
	private Bitmap bmp;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private FractaLand al;
	private SurfaceHolder holder;
	private GameLoopThread gameLoopThread;
	private int xPosBmp = 0;

	private int xSpeed = 5;
	
	public GameView(Context context, AttributeSet attributeSet)
	{
		super(context, attributeSet);
		gameLoopThread = new GameLoopThread(this);
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.FILL);
		
		//EditText etRadius = (EditText) findViewById(R.id.etRadius);
		
		//EditText etSpeed = (EditText) findViewById(R.id.etSpeed);
		
		
		holder = getHolder();
		holder.addCallback(new SurfaceHolder.Callback()
		{	
			@Override
			public void surfaceDestroyed(SurfaceHolder holder)
			{
				boolean retry = true;
				gameLoopThread.setRunning(false);
				while (retry)
				{
					try
					{
						gameLoopThread.join();
						retry = false;
					}
					catch (InterruptedException e)
					{
						
					}
				}
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder)
			{
				//gameLoopThread.setRunning(true);
				//gameLoopThread.start();
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
			{
			}
		});
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		//
		// Build the AL here?
		al = new FractaLand(2,10);
	}

	public void stopRunning()
	{
		gameLoopThread.setRunning(false);
		gameLoopThread.stop();
	}

	public void startRunning()
	{
		gameLoopThread.setRunning(true);
		gameLoopThread.start();
	}
	
	public void setAL(FractaLand al)
	{
		this.al = al;
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		// called repeatedly, produces animation
		canvas.drawColor(Color.BLACK);
		
		int iSpeed = xSpeed;
		if (xPosBmp == getWidth() - bmp.getWidth()) {
			iSpeed = xSpeed * -1;
		}
		if (xPosBmp == 0) {
			iSpeed = xSpeed;
		}
		xPosBmp = xPosBmp + iSpeed;
		
		canvas.drawBitmap(bmp, xPosBmp, 10, null);
		
		// will want to pass AL here for drawing
		float ctrX = canvas.getWidth() / 2f;
		float ctrY = canvas.getHeight() / 2f;
		
//		int r = al.getRadius();
//		int s = al.getSpacing();
//		for (int x=r*-1;x<=r;x++)
//		{
//			for (int y=r*-1;y<=r;y++)
//			{
//				paint.setColor(Color.WHITE);
//				float x1 = ctrX + (x*s);
//				float y1 = ctrY + (y*s);
//				canvas.drawLine(x1,y1,x1+1,y1+1,paint);
//			}
//		}	
	}
}
