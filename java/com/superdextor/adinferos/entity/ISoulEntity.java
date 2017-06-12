package com.superdextor.adinferos.entity;

import com.superdextor.adinferos.entity.monster.EntityGhost;

public interface ISoulEntity {
	
	public boolean canMakeGhost();
	public String getTexture();
	public void onCreateGhost(EntityGhost ghost);

}
