package tech.daniellas.p3;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.daniellas.p3.DomCombinators.findFirstChild;
import static tech.daniellas.p3.DomCombinators.hasAttribute;
import static tech.daniellas.p3.DomCombinators.hasChild;
import static tech.daniellas.p3.DomCombinators.hasTextContent;
import static tech.daniellas.p3.DomCombinators.hasName;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class UsersXmlTest {

	Document document;

	@Before
	public void init() throws SAXException, IOException, ParserConfigurationException {
		document = DocumentBuilderFactory
		    .newInstance()
		    .newDocumentBuilder()
		    .parse(this.getClass().getResourceAsStream("/users.xml"));
	}

	static Predicate<Node> isUserOfId(String id) {
		return hasName("user")
		    .and(hasChild(hasName("id")
		        .and(hasTextContent(id))));
	}

	@Test
	public void shouldFindStreet() {
		String street = findFirstChild(isUserOfId("1"))
		    .apply(document.getDocumentElement())
		    .flatMap(findFirstChild("location"))
		    .flatMap(findFirstChild("address"))
		    .flatMap(findFirstChild("street"))
		    .map(Node::getTextContent)
		    .get();

		assertThat(street).isEqualTo("Baker Street");
	}

	@Test
	public void shouldNotFindStreet() {
		Optional<String> street = findFirstChild(isUserOfId("2"))
		    .apply(document.getDocumentElement())
		    .flatMap(findFirstChild("location"))
		    .flatMap(findFirstChild("address"))
		    .flatMap(findFirstChild("street"))
		    .map(Node::getTextContent);

		assertThat(street.isPresent()).isFalse();
	}

	@Test
	public void shouldNotFindStreetOnMissingUser() {
		Optional<String> street = findFirstChild(isUserOfId("3"))
		    .apply(document.getDocumentElement())
		    .flatMap(findFirstChild("location"))
		    .flatMap(findFirstChild("address"))
		    .flatMap(findFirstChild("street"))
		    .map(Node::getTextContent);

		assertThat(street.isPresent()).isFalse();
	}

	@Test
	public void shouldNotFindStreetOnMissingAttribute() {
		Optional<String> street = findFirstChild(
		    hasName("user").and(hasAttribute("name", "Batman")))
		        .apply(document.getDocumentElement())
		        .flatMap(findFirstChild("location"))
		        .flatMap(findFirstChild("address"))
		        .flatMap(findFirstChild("street"))
		        .map(Node::getTextContent);

		assertThat(street.isPresent()).isFalse();
	}

}
