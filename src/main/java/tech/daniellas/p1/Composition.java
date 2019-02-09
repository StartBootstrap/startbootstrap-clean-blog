package tech.daniellas.p1;

public class Composition {

// Increment function
static Integer inc(Integer v) {
	return v + 1;
}

// Doubling function
static Integer doub(Integer v) {
	return v * 2;
}

// Here we compose doub and inc functions
Integer res = inc(doub(1));
}
