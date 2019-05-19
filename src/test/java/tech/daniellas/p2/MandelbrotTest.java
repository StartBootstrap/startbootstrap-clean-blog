package tech.daniellas.p2;

import static tech.daniellas.p2.Mandelbrot.mandelbrot;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

import org.junit.Test;

import tech.daniellas.p2.Mandelbrot.Point;

public class MandelbrotTest {
	@Test
	public void generateImage() throws IOException {
		int width = 400; // Image width
		int height = 300; // Image height
		int iterations = 100_000; // Number of Mandelbrot's formula iterations
		List<Point> points = IntStream.range(0, width)
		    .mapToObj(i -> i)
		    .flatMap(x -> IntStream.range(0, height)
		        .mapToObj(y -> new Point(x, y)))
		    .collect(Collectors.toList());
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		points.stream()
		    .parallel()
		    .map(mandelbrot(width, height, iterations))
		    .forEach(p -> image.setRGB(p.x, p.y, p.col));

		ImageIO.write(image, "png", Paths.get("target/mandelbrot.png").toFile());
	}
}
