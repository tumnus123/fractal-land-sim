package com.seethefractals.game;

public class FracDelta
{
	// This class is used to move the fractal landscape
	// from its current state to a new target state
	// in a smooth motion that combines mag, dir and dist
	
	private FractaLand fl;
	private GameView gv;
	
	
	public FracDelta(FractaLand fl, float fMag, float fDir, float fDist, GameView gv){
		this.fl = fl;
		this.gv = gv;
	}
	
	public void animate(){
		// works... but how to control speed? check tutorial...
		// fl = new FractaLand(1,50f);
		// gv.setAL(fl);
	}
}
