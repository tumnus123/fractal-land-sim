package com.seethefractals.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.*;

public class GameView extends SurfaceView
{
	private Bitmap bmp;
	private FractaLand al;
	private SurfaceHolder holder;
	private GameLoopThread gameLoopThread;
	private int x = 0;

	private int xSpeed = 5;
	
	public GameView(Context context, AttributeSet attributeSet)
	{
		super(context, attributeSet);
		gameLoopThread = new GameLoopThread(this);
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
				gameLoopThread.setRunning(true);
				gameLoopThread.start();
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
			{
			}
		});
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		//
		// Build the AL here?
		al = new FractaLand(10,3);
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		canvas.drawColor(Color.BLACK);
		int iSpeed = xSpeed;
		if (x == getWidth() - bmp.getWidth()) {
			iSpeed = xSpeed * -1;
		}
		if (x == 0) {
			iSpeed = xSpeed;
		}
		x = x + iSpeed;
		
		canvas.drawBitmap(bmp, x, 10, null);
		
		// will want to pass AL here for drawing
	}
}
