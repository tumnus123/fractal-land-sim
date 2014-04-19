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
	// this class depends on the FracNode class
	//
	// the FracNode class represents a point as a
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

	//private ArrayList<ArrayList<Double>> fl;
	private ArrayList<ArrayList<FracNode>> fl;
	
	private int iRadius;
	private float fSpacing;
	private float fMaxSpacing;
	private float fOffsetX;
	private float fOffsetY;
	private float fOffsetMag;
	private float fDeltaX;
	private float fDeltaY;
	private float fDeltaMag;
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
		this.fMaxSpacing = fSpacing * 2;

		// create and initialize
		//fl = new ArrayList<ArrayList<Double>(i);
		fl = new ArrayList<ArrayList<FracNode>>(i);
		for (int j = 0; j < i; j++)
		{
			ArrayList<FracNode> col = 
				new ArrayList<FracNode>(Collections.nCopies(i, new FracNode()));
			fl.add(col);
		}
		
		// init offsets
		fOffsetX = 0f;
		fOffsetY = 0f;
		fOffsetMag = 0f;
		fSpeedXY = 1f;
	}
	
	public void moveTo(float fDeltaX, float fDeltaY, float fDeltaMag){
		this.fDeltaX = fDeltaX;
		this.fDeltaY = fDeltaY;
		this.fDeltaMag = fDeltaMag;
	}
	
	public void update() {
		float fIncrementX = 0f;
		float fIncrementY = 0f;
		float fIncrementMag = 0f;
		if(Math.abs(fDeltaX)>0f){
			fIncrementX = fDeltaX*0.1f*fSpeedXY; // revise?
			fDeltaX=fDeltaX-fIncrementX;
			fOffsetX=fOffsetX+fIncrementX;
			adjustFLX();
		}
		if(Math.abs(fDeltaY)>0f){
			fIncrementY = fDeltaY*0.1f*fSpeedXY; // revise?
			fDeltaY=fDeltaY-fIncrementY;
			fOffsetY=fOffsetY+fIncrementY;
			adjustFLY();
		}
		if(fDeltaMag>1.0f){
			fIncrementMag = 0.05f; 
			fDeltaMag=fDeltaMag-fIncrementMag;
			adjustFLMag(fIncrementMag);
		}
	}

	private void adjustFLMag(float fIncrement)
	{
		fSpacing+=fIncrement;
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
			//addRow("north");
			addRowAt(0);
			removeRowAt(fl.size()-1);
			fOffsetY-=fSpacing;
		} else {
			//addRow("south");
			addRowAt(fl.size()-1);
			removeRowAt(0);
			fOffsetY+=fSpacing;
		}
	}
	
	private void shiftColumns()
	{
		if(fOffsetX>0f){
			addColAt(0);
			removeColAt(fl.size()-1);
			fOffsetX-=fSpacing;
		} else {
			addColAt(fl.size()-1);
			removeColAt(0);
			fOffsetX+=fSpacing;
		}
	}
	
	private void addRowAt(int index) {
		ArrayList<FracNode> thisAL;
		for (int i=0;i<fl.size();i++) {
			thisAL = fl.get(i);
			thisAL.add(index, new FracNode());
		}
	}
	private void removeRowAt(int index) {
		ArrayList<FracNode> thisAL;
		for (int i=0;i<fl.size();i++) {
			thisAL = fl.get(i);
			thisAL.remove(index);
		}
	}
	private void addColAt(int index) {
		ArrayList<FracNode> col = new ArrayList<FracNode>(Collections.nCopies(fl.size(), new FracNode()));
		fl.add(index, col);
	}
	private void removeColAt(int index) {
		fl.remove(index);
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
				paint.setColor(fl.get(x+iRadius).get(y+iRadius).getII());
				paint.setAlpha(200);
				float x1 = ctrX + (x * fSpacing) + fOffsetX;
				float y1 = ctrY + (y * fSpacing) + fOffsetY;
				c.drawCircle(x1,y1,1.5f, paint);
			}
		}
	}

	public FracNode getXY(int x, int y)
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
		fl.get(offX).set(offY, new FracNode());
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
