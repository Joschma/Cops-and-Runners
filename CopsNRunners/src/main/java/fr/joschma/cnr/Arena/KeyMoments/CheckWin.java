package fr.joschma.cnr.Arena.KeyMoments;

import fr.joschma.cnr.Arena.Arena;

public class CheckWin {
	
	Arena a;
	
	public CheckWin(Arena a) {
		this.a = a;
	}

	public void checkWin(Arena a) {
		if (a.getPrisoned().size() >= a.getRunners().size()) {
			a.getFinishGame().CopsWin();
		} else if (a.getCops().size() == 0) {
			a.getFinishGame().RunnersWin();
		} else if (a.getRunners().size() == 0) {
			a.getFinishGame().CopsWin();
		}
	}
}
