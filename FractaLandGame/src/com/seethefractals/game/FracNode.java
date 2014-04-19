package com.seethefractals.game;
import android.graphics.*;

public class FracNode
{
	public double dX;
	public double dY;
	public int iI;

	public FracNode() {
		this.dX = 0d;
		this.dY = 0d;
		this.iI = Color.BLUE;
	}

	public FracNode(double dX, double dY, int iI) {
		this.dX = dX;
		this.dY = dY;
		this.iI = iI;
	}
	
	public void setDX(double dX)
	{
		this.dX = dX;
	}

	public double getDX()
	{
		return dX;
	}

	public void setDY(double dY)
	{
		this.dY = dY;
	}

	public double getDY()
	{
		return dY;
	}

	public void setII(int iI)
	{
		this.iI = iI;
	}

	public int getII()
	{
		return iI;
	}
	
	
	
}
