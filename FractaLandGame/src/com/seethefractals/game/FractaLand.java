package com.seethefractals.game;

import java.util.ArrayList;
import java.util.Collections;

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

	public FractaLand(int iRadius, float fSpacing)
	{
		// AL must have a center node, so
		// double radius and add one
		this.iRadius = iRadius;
		int i = (iRadius * 2) + 1;

		// Spacing is the basic space between nodes
		this.fSpacing = fSpacing;

		// create and initialize
		al = new ArrayList<ArrayList<Double>>(i);
		for (int j = 0; j < i; j++)
		{
			ArrayList<Double> col = 
				new ArrayList<Double>(Collections.nCopies(i, 0.0));
			al.add(col);
		}
	}
	
	public void moveTo(float distX, float distY, double distMag){
		
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

	public void setSpacing(int iSpacing) {
		//TODO: make private
		this.fSpacing = iSpacing;
	}
	public float getSpacing()
	{
		return fSpacing;
	}

}
