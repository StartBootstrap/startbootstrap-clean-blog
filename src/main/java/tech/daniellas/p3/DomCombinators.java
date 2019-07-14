package tech.daniellas.p3;

import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class DomCombinators {

	static class NodeListIterator implements Iterator<Node> {
		final NodeList nodeList;
		int idx = 0;

		NodeListIterator(NodeList children) {
			this.nodeList = children;
			this.idx = 0;
		}

		@Override
		public boolean hasNext() {
			return idx < nodeList.getLength() - 1;
		}

		@Override
		public Node next() {
			return nodeList.item(idx++);
		}
	}

	static Stream<Node> stream(NodeList nodes) {
		return StreamSupport
		    .stream(
		        Spliterators
		            .spliteratorUnknownSize(
		                new NodeListIterator(nodes),
		                Spliterator.IMMUTABLE
		                    | Spliterator.NONNULL
		                    | Spliterator.ORDERED),
		        false);
	}

	static Predicate<Node> hasName(String name) {
		return node -> Optional.of(node)
		    .map(Node::getNodeName)
		    .filter(name::equals)
		    .isPresent();
	}

	static Predicate<Node> hasTextContent(String value) {
		return node -> Optional.of(node)
		    .map(Node::getTextContent)
		    .filter(value::equals)
		    .isPresent();
	}

	static Predicate<Node> hasAttribute(String name, String value) {
		return node -> Optional.of(node)
		    .map(Node::getAttributes)
		    .map(i -> i.getNamedItem(name))
		    .map(Node::getNodeValue)
		    .filter(value::equals)
		    .isPresent();
	}

	static Function<Node, Optional<Node>> findFirstChild(Predicate<Node> condition) {
		return parent -> stream(parent.getChildNodes())
		    .filter(condition)
		    .findFirst();
	}

	static Function<Node, Optional<Node>> findFirstChild(String name) {
		return findFirstChild(hasName(name));
	}

	static Predicate<Node> hasChild(Predicate<Node> condition) {
		return node -> findFirstChild(condition)
		    .apply(node)
		    .isPresent();
	}
}
