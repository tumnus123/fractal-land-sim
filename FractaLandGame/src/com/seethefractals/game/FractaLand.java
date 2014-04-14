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

	private ArrayList<ArrayList<Double>> fl;
	private int iRadius;
	private float fSpacing;
	private float fOffsetX;
	private float fOffsetY;
	private float fDeltaX;
	private float fDeltaY;
	private float fSpeedXY;
	private final Paint paint = new Paint();

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
		fl = new ArrayList<ArrayList<Double>>(i);
		for (int j = 0; j < i; j++)
		{
			ArrayList<Double> col = 
				new ArrayList<Double>(Collections.nCopies(i, 0.0));
			fl.add(col);
		}
		
		// init offsets
		fOffsetX = 0f;
		fOffsetY = 0f;
		fSpeedXY = 1f;
	}
	
	public void moveTo(float fDeltaX, float fDeltaY, double fDeltaMag){
		this.fDeltaX = fDeltaX;
		this.fDeltaY = fDeltaY;
	}
	
	public void update() {
		float fIncrement = 0f;
		if(Math.abs(fDeltaX)>0f){
			fIncrement = fDeltaX*0.1f*fSpeedXY; // revise?
			fDeltaX=fDeltaX-fIncrement;
			fOffsetX=fOffsetX+fIncrement;
			adjustFLX();
		}
		if(Math.abs(fDeltaY)>0f){
			fIncrement = fDeltaY*0.1f*fSpeedXY; // revise?
			fDeltaY=fDeltaY-fIncrement;
			fOffsetY=fOffsetY+fIncrement;
			adjustFLY();
		}
	}

	private void adjustFLX()
	{
		if(Math.abs(fOffsetX)>=fSpacing){
			shiftColumns();
		}
	}

	private void adjustFLY()
	{
		if(Math.abs(fOffsetY)>=fSpacing){
			shiftRows();
		}
	}

	private void shiftRows()
	{
		if(fOffsetY>0f){
			addRow("north");
			fOffsetY-=fSpacing;
		} else {
			addRow("south");
			fOffsetY+=fSpacing;
		}
	}
	
	private void shiftColumns()
	{
		if(fOffsetX>0f){
			addColumn("east");
			fOffsetX-=fSpacing;
		} else {
			addColumn("west");
			fOffsetX+=fSpacing;
		}
	}

	private void addRow(String addOnSide)
	{
		ArrayList<Double> thisAL;
		if(addOnSide.equals("north")){
			for(int i=0;i<fl.size();i++) {
				thisAL = fl.get(i);
				thisAL.add(0.0);
				thisAL.remove(thisAL.size()-1);
			}
		} else {
			for(int i=0;i<fl.size();i++) {
				thisAL = fl.get(i);
				thisAL.add(thisAL.size()-1,0.0);
				thisAL.remove(0);
			}
		}
	}
	
	private void addColumn(String addOnSide)
	{
		if(addOnSide.equals("west")){
			ArrayList<Double> col = 
				new ArrayList<Double>(Collections.nCopies(fl.size(), 0.0));
			fl.add(col);
			fl.remove(fl.size()-1);
		} else {
			ArrayList<Double> col = 
				new ArrayList<Double>(Collections.nCopies(fl.size(), 0.0));
			fl.add(fl.size()-1,col);
			fl.remove(0);
		}
		
	}
	
	public void draw(Canvas c) {
		update();
		c.drawColor(Color.BLACK);
		
		float ctrX = c.getWidth() / 2f;
		float ctrY = c.getHeight() / 2f;

		paint.setColor(Color.RED);
		c.drawLine(ctrX-5,ctrY,ctrX+5,ctrY, paint);
		c.drawLine(ctrX,ctrY-5,ctrX,ctrY+5, paint);
		
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
		return fl.get(offX).get(offY);
	}

	public void setXY(int x, int y, Double val)
	{
		// center node is 0,0
		int offX = x + iRadius;
		int offY = y + iRadius;
		fl.get(offX).set(offY, val);
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
