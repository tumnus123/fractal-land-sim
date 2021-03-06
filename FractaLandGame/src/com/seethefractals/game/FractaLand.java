
package com.seethefractals.game;

import java.util.ArrayList;
import java.util.Collections;
import android.graphics.*;
import android.util.*;
import java.util.*;

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

	private ArrayList<ArrayList<FracNode>> fl;

	private int iRadius;
	private float fSpacing;
	private float fMaxSpacing;
	private float fMaxViewDist;
	private float fOffsetX;
	private float fOffsetY;
	private float fOffsetMag;
	private float fDeltaX;
	private float fDeltaY;
	private float fDeltaMag;
	private float fSpeedXY;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

	public FractaLand(int iRadius, float fSpacing, float fMaxViewDist)
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
		this.fMaxViewDist = fMaxViewDist;

// create and initialize
		fl = new ArrayList<ArrayList<FracNode>>(i);
		for (int j = 0; j < i; j++)
		{
			ArrayList<FracNode> col = 
				new ArrayList<FracNode>(i);
			for (int y=0;y<i;y++)
			{
				col.add(new FracNode(0d, 0d, Color.WHITE, 255));
			}
			fl.add(col);
		}

// init offsets
		fOffsetX = 0f;
		fOffsetY = 0f;
		fOffsetMag = 0f;
		fSpeedXY = 1f;
	}

	public void moveTo(float fDeltaX, float fDeltaY, float fDeltaMag)
	{
		this.fDeltaX = fDeltaX;
		this.fDeltaY = fDeltaY;
		this.fDeltaMag = fDeltaMag;
	}

	public void update()
	{
		float fIncrementX = 0f;
		float fIncrementY = 0f;
		float fIncrementMag = 0f;
		boolean bDeltaX = Math.abs(fDeltaX) > 0f;
		boolean bDeltaY = Math.abs(fDeltaY) > 0f;
		boolean bDeltaMag = fDeltaMag > 1.0f;
		if (bDeltaX)
		{
			fIncrementX = fDeltaX * 0.1f * fSpeedXY; // revise?
			fDeltaX = fDeltaX - fIncrementX;
			fOffsetX = fOffsetX + fIncrementX;
			if (Math.abs(fOffsetX) >= fSpacing)
			{
				shiftColumns();
			}
		}
		if (bDeltaY)
		{
			fIncrementY = fDeltaY * 0.1f * fSpeedXY; // revise?
			fDeltaY = fDeltaY - fIncrementY;
			fOffsetY = fOffsetY + fIncrementY;
			if (Math.abs(fOffsetY) >= fSpacing)
			{
				shiftRows();
			}
		}
		if (bDeltaMag)
		{
			fIncrementMag = 0.2f; 
			fDeltaMag = fDeltaMag - fIncrementMag;
			fSpacing += fIncrementMag;	
			return;
		}

		if (fSpacing > fMaxSpacing)
		{
// 1) generate a new fl
			iRadius *= 2;
			int iWidth = (iRadius * 2) + 1;
			fSpacing /= 2;
			ArrayList<ArrayList<FracNode>> newfl;
			newfl = new ArrayList<ArrayList<FracNode>>(iWidth);
			for (int x=0;x < iWidth;x++)
			{
				ArrayList<FracNode> col = new ArrayList<FracNode>(iWidth);
				for (int y=0;y < iWidth;y++)
				{
					col.add(new FracNode(0d, 0d, Color.WHITE, 0));
				}
				newfl.add(col);
			}
// 2) pop with old fl values and sub
			int xF = 0;
			int yF = 0;
			FracNode fnOld, fnNew;
			for (int x=0;x < fl.size();x++)
			{
				for (int y=0;y < fl.size();y++)
				{
					xF = x * 2;
					yF = y * 2;
					fnOld = fl.get(x).get(y);
					fnNew = newfl.get(xF).get(yF);
					fnNew.setX(fnOld.getX());
					fnNew.setY(fnOld.getY());
					fnNew.setIter(fnOld.getIter());
					fnNew.setAlpha(fnOld.getAlpha());
				}
			}
			fl = newfl;	
		}
		if (fSpacing * iRadius > fMaxViewDist)
		{
			int iMaxIndex = fl.size() - 1;
			removeRowAt(iMaxIndex);
			removeRowAt(0);
			removeColAt(iMaxIndex);
			removeColAt(0);
			iRadius--;
			iMaxIndex -= 2;
		}
	}

	private void shiftRows()
	{
		if (fOffsetY > 0f)
		{
			addRowAt(0);
			removeRowAt(fl.size() - 1);
			fOffsetY -= fSpacing;
		}
		else
		{
			removeRowAt(0);
			addRowAt(fl.size() - 1);
			fOffsetY += fSpacing;
		}
	}

	private void shiftColumns()
	{
		if (fOffsetX > 0f)
		{
			addColAt(0);
			removeColAt(fl.size() - 1);
			fOffsetX -= fSpacing;
		}
		else
		{
			addColAt(fl.size());
			removeColAt(0);
			fOffsetX += fSpacing;
		}
	}

	private void addRowAt(int index)
	{
		ArrayList<FracNode> thisAL;
		for (int i=0;i < fl.size();i++)
		{
			thisAL = fl.get(i);
			thisAL.add(index, new FracNode(0d, 0d, Color.WHITE, 255));
		}
	}
	private void removeRowAt(int index)
	{
		ArrayList<FracNode> thisAL;
		for (int i=0;i < fl.size();i++)
		{
			thisAL = fl.get(i);
			thisAL.remove(index);
		}
	}
	private void addColAt(int index)
	{
		ArrayList<FracNode> col = new ArrayList<FracNode>(fl.size());
		for (int y=0;y < fl.size();y++)
		{
			col.add(new FracNode(0d, 0d, Color.WHITE, 255));
		}
		fl.add(index, col);
	}
	private void removeColAt(int index)
	{
		fl.remove(index);
	}

	public void draw(Canvas c)
	{
		update();
		c.drawColor(Color.BLACK);

		float ctrX = c.getWidth() / 2f;
		float ctrY = c.getHeight() / 2f;

		paint.setColor(Color.RED);
		c.drawLine(ctrX - 5, ctrY, ctrX + 5, ctrY, paint);
		c.drawLine(ctrX, ctrY - 5, ctrX, ctrY + 5, paint);

		// draw the FracNodes centered at 0,0
		ArrayList row;
		FracNode node;
		int iAlpha;
		int iter;
		for (int x=iRadius * -1;x <= iRadius;x++)
		{
			for (int y=iRadius * -1;y <= iRadius;y++)
			{
				row = fl.get(x + iRadius);
				node = (FracNode) row.get(y + iRadius);
				iter = node.getIter();
				paint.setColor(Color.argb(0,iter,iter,iter));
				// paint.setColor(node.getIter());
				// increment node alphas while zooming
				iAlpha = node.getAlpha();
				if(fDeltaMag>1.0f){
					if (iAlpha < 250){
						node.setAlpha(iAlpha + 10);
					} else {
						node.setAlpha(255);
					}
				}
				paint.setAlpha(iAlpha);
				float x1 = ctrX + (x * fSpacing) + fOffsetX;
				float y1 = ctrY + (y * fSpacing) + fOffsetY;
				c.drawCircle(x1, y1, 1.5f, paint);
			}
		}	
	}

	private FracNode calcIter(FracNode node) {
		int MAX = 255; // TODO: make dynamic
		double px = node.getX();
		double py = node.getY();
		double zx = 0.0, zy = 0.0;
		double zx2 = 0.0, zy2 = 0.0;
		int value = 0;
		while (value < MAX && zx2 + zy2 < 4.0) {
			zy = 2.0 * zx * zy + py;
			zx = zx2 - zy2 + px;
			zx2 = zx * zx;
			zy2 = zy * zy;
			value++;
		}
		node.setIter(value);
		return node;
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

	public void setRadius(int iRadius)
	{
//TODO: make private
		this.iRadius = iRadius;
	}
	public int getRadius()
	{
		return iRadius;
	}

	public void setSpacing(float fSpacing)
	{
//TODO: make private
		this.fSpacing = fSpacing;
	}
	public float getSpacing()
	{
		return fSpacing;
	}

	}

