package com.seethefractals.game;

import java.util.ArrayList;
import java.util.Collections;
import android.graphics.*;

/**
  * @author tumnus123
 * @author tarah.west
  * 
  */
public class FractaLand
{
	// this class contains an ArrayList of ArrayLists
	// and methods for performing ops on that AL
	// add a row/col, remove a row/col, suppress a row/col,
	// erodeHeights...
	//
	// this class depends on the ScapeNode class
	//
	// the ScapeNode class represents a point as a
	// position on a grid of unevenly-spaced points
	// Properties include iXpos, iYpos, fHeight, fOffset  
	// Origin is center of grid
	// Offset is dist away from next innermost node
	// This allows node spacing to vary by dist to origin
	//
	// once a node's height is calculated and eroded, 
	// it does not change while the node exists.
	// New rows/cols of nodes are added as needed as a 
	// result of magnification
	// Newly added nodes are fully suppressed at initialization,
	// meaning their initial height is the 

	private ArrayList<ArrayList<Double>> al;
	private int iRadius;
	private float fSpacing;
	private float fOffsetX;
	private float fOffsetY;
	private float fDeltaX;
	private float fDeltaY;
	private float fSpeedXY;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

	public FractaLand(int iRadius, float fSpacing)
	{
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.FILL);
		
		// AL must have a center node, so
		// double radius and add one
		this.iRadius = iRadius;
		int i = (iRadius * 2) + 1;

		// Spacing is the screen space between nodes
		this.fSpacing = fSpacing;

		// create and initialize
		al = new ArrayList<ArrayList<Double>>(i);
		for (int j = 0; j < i; j++)
		{
			ArrayList<Double> col = 
				new ArrayList<Double>(Collections.nCopies(i, 0.0));
			al.add(col);
		}
		
		// init offsets
		fOffsetX = 0f;
		fOffsetY = 0f;
		fSpeedXY = 1f;
	}
	
	public void moveTo(float fDeltaX, float fDeltaY, double fDeltaMag){
		fOffsetX+=fDeltaX;
		fOffsetY+=fDeltaY;
	}
	
	public void update() {
		if(Math.abs(fDeltaX)>0f){
			fDeltaX+=fSpeedXY;
			fOffsetX+=fSpeedXY;
		} else {
			
		}
	}
	
	public void draw(Canvas c) {
		c.drawColor(Color.BLACK);
		float ctrX = c.getWidth() / 2f;
		float ctrY = c.getHeight() / 2f;

		for (int x=iRadius * -1;x <= iRadius;x++)
		{
			for (int y=iRadius * -1;y <= iRadius;y++)
			{
				paint.setColor(Color.WHITE);
				float x1 = ctrX + (x * fSpacing) + fOffsetX;
				float y1 = ctrY + (y * fSpacing) + fOffsetY;
				c.drawLine(x1, y1, x1 + 1, y1 + 1, paint);
			}
		}
	}

	public Double getXY(int x, int y)
	{
		// center node is 0,0
		int offX = x + iRadius;
		int offY = y + iRadius;
		return al.get(offX).get(offY);
	}

	public void setXY(int x, int y, Double val)
	{
		// center node is 0,0
		int offX = x + iRadius;
		int offY = y + iRadius;
		al.get(offX).set(offY, val);
	}

	public void setRadius(int iRadius) {
		//TODO: make private
		this.iRadius = iRadius;
	}
	public int getRadius()
	{
		return iRadius;
	}

	public void setSpacing(float fSpacing) {
		//TODO: make private
		this.fSpacing = fSpacing;
	}
	public float getSpacing()
	{
		return fSpacing;
	}

}
