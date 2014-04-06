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
	private FractaLand fl;
	private SurfaceHolder holder;
	private GameLoopThread gameLoopThread;
	private int xPosBmp = 0;
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
//		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		//
		// Build the initial FL here
		fl = new FractaLand(1, 2f);
	}

	public FractaLand getFractaLand()
	{
		// TODO: Implement this method
		return this.fl;
	}



//	public void setAL(FractaLand fl)
//	{
//		this.fl = fl;
//	}
//
	public GameLoopThread getThread()
	{
		return gameLoopThread;
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		// called repeatedly, produces animation
		fl.draw(canvas);
	}

}
