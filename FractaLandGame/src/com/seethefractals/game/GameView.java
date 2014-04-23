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
	private FractaLand fl;
	private SurfaceHolder holder;
	private GameLoopThread gameLoopThread;

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

		// Build the initial FL here
		fl = new FractaLand(10, 10f, 300f);
		// TODO: Implement max view dist
	}

	public FractaLand getFractaLand()
	{
		return this.fl;
	}

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
