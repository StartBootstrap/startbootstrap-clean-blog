package tech.daniellas.p1.fun;

class Player {
	// Note that attributes are final, effectively immutable
	final String name;
	final int score;

	Player(String name, int score) {
		this.name = name;
		this.score = score;
	}

	static Player addScore(int score, Player player) {
		return new Player(player.name, player.score + score);
	}
}
