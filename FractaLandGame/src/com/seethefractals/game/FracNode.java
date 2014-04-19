package com.seethefractals.game;
import android.graphics.*;

public class FracNode
{
	private double dX;
	private double dY;
	private int iIter;
	private int iPresence;

	public FracNode() {
		this.dX = 0d;
		this.dY = 0d;
		this.iIter = 0;
		this.iPresence = 0;
	}

	public FracNode(double dX, double dY, int iIter, int iPresence) {
		this.dX = dX;
		this.dY = dY;
		this.iIter = iIter;
		this.iPresence = iPresence;
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
