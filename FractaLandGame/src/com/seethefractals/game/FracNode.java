package com.seethefractals.game;
import android.graphics.*;

public class FracNode
{
	private double dX;
	private double dY;
	private int iIter;
	private int iAlpha;

	public FracNode() {
		this.dX = 0d;
		this.dY = 0d;
		this.iIter = 0;
		this.iAlpha = 0;
	}

	public FracNode(double dX, double dY, int iIter, int iAlpha) {
		this.dX = dX;
		this.dY = dY;
		this.iIter = iIter;
		this.iAlpha = iAlpha;
	}

	public void setAlpha(int iAlpha)
	{
		this.iAlpha = iAlpha;
	}

	public int getAlpha()
	{
		return iAlpha;
	}
	
	public void setX(double dX)
	{
		this.dX = dX;
	}

	public double getX()
	{
		return dX;
	}

	public void setY(double dY)
	{
		this.dY = dY;
	}

	public double getY()
	{
		return dY;
	}

	public void setIter(int iIter)
	{
		this.iIter = iIter;
	}

	public int getIter()
	{
		return iIter;
	}
	
	
	
}
