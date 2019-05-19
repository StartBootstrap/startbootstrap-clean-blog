package tech.daniellas.p2;

import java.util.function.Function;

class Mandelbrot {
	static class Point {
		final int x;
		final int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static class ColouredPoint extends Point {
		final int col;

		public ColouredPoint(int x, int y, int col) {
			super(x, y);
			this.col = col;
		}
	}

	static Function<Point, ColouredPoint> mandelbrot(int width, int height, int max) {
		return p -> {
			double re = (p.x - width / 2) * 4.0 / width;
			double im = (p.y - height / 2) * 4.0 / width;
			double x = 0, y = 0;
			int iterations = 0;

			while (x * x + y * y < 4 && iterations < max) {
				double newX = x * x - y * y + re;

				y = 2 * x * y + im;
				x = newX;
				iterations++;
			}
			if (iterations < max) {
				return new ColouredPoint(p.x, p.y, 0);
			}

			return new ColouredPoint(p.x, p.y, 0xEEEEEE);
		};
	}
}
