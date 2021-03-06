// Copyright 2013 Michel Kraemer
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package de.undercouch.citeproc.bibtex;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.undercouch.citeproc.csl.CSLName;

/**
 * Tests the name parser
 * @author Michel Kraemer
 */
public class NameParserTest {
	/**
	 * Tests if a family name can be parsed
	 */
	@Test
	public void familyOnly() {
		CSLName[] names = NameParser.parse("Thompson");
		assertEquals(1, names.length);
		assertEquals("Thompson", names[0].getFamily());
	}
	
	/**
	 * Tests if a simple name can be parsed
	 */
	@Test
	public void simple() {
		CSLName[] names = NameParser.parse("Ken Thompson");
		assertEquals(1, names.length);
		assertEquals("Ken", names[0].getGiven());
		assertEquals("Thompson", names[0].getFamily());
	}
	
	/**
	 * Tests if a name with a middle initial can be parsed
	 */
	@Test
	public void middleName() {
		CSLName[] names = NameParser.parse("Dennis M. Ritchie");
		assertEquals(1, names.length);
		assertEquals("Dennis M.", names[0].getGiven());
		assertEquals("Ritchie", names[0].getFamily());
	}
	
	/**
	 * Tests if a name with initials can be parsed
	 */
	@Test
	public void initials() {
		CSLName[] names = NameParser.parse("S. C. Johnson");
		assertEquals(1, names.length);
		assertEquals("S. C.", names[0].getGiven());
		assertEquals("Johnson", names[0].getFamily());
	}
	
	/**
	 * Tests if a name with a non-dropping particle can be parsed
	 */
	@Test
	public void nonDroppingParticle() {
		CSLName[] names = NameParser.parse("Michael van Gerwen");
		assertEquals(1, names.length);
		assertEquals("Michael", names[0].getGiven());
		assertEquals("van", names[0].getNonDroppingParticle());
		assertEquals("Gerwen", names[0].getFamily());
	}
	
	/**
	 * Tests if a family name with a non-dropping particle can be parsed
	 */
	@Test
	public void nonDroppingParticleFamilyOnly() {
		CSLName[] names = NameParser.parse("van Gerwen");
		assertEquals(1, names.length);
		assertEquals("van", names[0].getNonDroppingParticle());
		assertEquals("Gerwen", names[0].getFamily());
	}
	
	/**
	 * Tests if a name with a comma can be parsed
	 */
	@Test
	public void comma() {
		CSLName[] names = NameParser.parse("Thompson, Ken");
		assertEquals(1, names.length);
		assertEquals("Ken", names[0].getGiven());
		assertEquals("Thompson", names[0].getFamily());
	}
	
	/**
	 * Tests if a name with a suffix can be parsed
	 */
	@Test
	public void commaJunior() {
		CSLName[] names = NameParser.parse("Friedman, Jr., George");
		assertEquals(1, names.length);
		assertEquals("George", names[0].getGiven());
		assertEquals("Jr.", names[0].getSuffix());
		assertEquals("Friedman", names[0].getFamily());
	}
	
	/**
	 * Tests if a name with a given name and two family names can be
	 * parsed correctly and if the given name is not parsed as suffix
	 */
	@Test
	public void commaNoJunior() {
		CSLName[] names = NameParser.parse("Familya Familyb, Given");
		assertEquals(1, names.length);
		assertEquals("Given", names[0].getGiven());
		assertEquals("Familya Familyb", names[0].getFamily());
	}
	
	/**
	 * Tests if a name with a comma and a middle initial can be parsed
	 */
	@Test
	public void commaInitials() {
		CSLName[] names = NameParser.parse("Ritchie, Dennis M.");
		assertEquals(1, names.length);
		assertEquals("Dennis M.", names[0].getGiven());
		assertEquals("Ritchie", names[0].getFamily());
	}
	
	/**
	 * Tests if a name with a comma and a non-dropping particle can be parsed
	 */
	@Test
	public void commaNonDroppingParticle() {
		CSLName[] names = NameParser.parse("van Gerwen, Michael");
		assertEquals(1, names.length);
		assertEquals("Michael", names[0].getGiven());
		assertEquals("van", names[0].getNonDroppingParticle());
		assertEquals("Gerwen", names[0].getFamily());
	}
	
	/**
	 * Tests if a name with a comma and multiple non-dropping particles can be parsed
	 */
	@Test
	public void commaNonDroppingParticles() {
		CSLName[] names = NameParser.parse("Van der Voort, Vincent");
		assertEquals(1, names.length);
		assertEquals("Vincent", names[0].getGiven());
		assertEquals("Van der", names[0].getNonDroppingParticle());
		assertEquals("Voort", names[0].getFamily());
	}
	
	/**
	 * Tests if multiple names can be parsed
	 */
	@Test
	public void and() {
		CSLName[] names = NameParser.parse("Michael van Gerwen and Vincent van der Voort");
		assertEquals(2, names.length);
		assertEquals("Michael", names[0].getGiven());
		assertEquals("van", names[0].getNonDroppingParticle());
		assertEquals("Gerwen", names[0].getFamily());
		assertEquals("Vincent", names[1].getGiven());
		assertEquals("van der", names[1].getNonDroppingParticle());
		assertEquals("Voort", names[1].getFamily());
	}
	
	/**
	 * Tests if multiple names with commas can be parsed
	 */
	@Test
	public void andComma() {
		CSLName[] names = NameParser.parse("van Gerwen, Michael and Van der Voort, Vincent");
		assertEquals(2, names.length);
		assertEquals("Michael", names[0].getGiven());
		assertEquals("van", names[0].getNonDroppingParticle());
		assertEquals("Gerwen", names[0].getFamily());
		assertEquals("Vincent", names[1].getGiven());
		assertEquals("Van der", names[1].getNonDroppingParticle());
		assertEquals("Voort", names[1].getFamily());
	}
	
	/**
	 * Tests if multiple names with commas can be parsed
	 */
	@Test
	public void andComma2() {
		CSLName[] names = NameParser.parse("van Gerwen, Michael and van der Voort, Vincent");
		assertEquals(2, names.length);
		assertEquals("Michael", names[0].getGiven());
		assertEquals("van", names[0].getNonDroppingParticle());
		assertEquals("Gerwen", names[0].getFamily());
		assertEquals("Vincent", names[1].getGiven());
		assertEquals("van der", names[1].getNonDroppingParticle());
		assertEquals("Voort", names[1].getFamily());
	}
	
	/**
	 * Tests if multiple names with commas can be parsed
	 */
	@Test
	public void andCommaMix() {
		CSLName[] names = NameParser.parse("van Gerwen, Michael and Vincent van der Voort");
		assertEquals(2, names.length);
		assertEquals("Michael", names[0].getGiven());
		assertEquals("van", names[0].getNonDroppingParticle());
		assertEquals("Gerwen", names[0].getFamily());
		assertEquals("Vincent", names[1].getGiven());
		assertEquals("van der", names[1].getNonDroppingParticle());
		assertEquals("Voort", names[1].getFamily());
	}
	
	/**
	 * Tests if a name with a suffix can be parsed
	 */
	@Test
	public void junior() {
		CSLName[] names = NameParser.parse("George Friedman, Jr.");
		assertEquals(1, names.length);
		assertEquals("George", names[0].getGiven());
		assertEquals("Jr.", names[0].getSuffix());
		assertEquals("Friedman", names[0].getFamily());
	}
	
	/**
	 * Tests if a non-parseable name renders a literal string
	 */
	@Test
	public void nonParseable() {
		String str = "Jerry Peek and Tim O'Reilly and Mike Loukides and other authors of the Nutshell handbooks";
		CSLName[] names = NameParser.parse(str);
		assertEquals(1, names.length);
		assertEquals(str, names[0].getLiteral());
	}
}
