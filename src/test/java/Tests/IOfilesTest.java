
package Tests;
import static org.junit.Assert.*;

import org.junit.Test;

import BuisnessLogic.IOfiles;

public class IOfilesTest {
	@Test
	public void testIOfiles() {
		IOfiles io= new IOfiles("rfbhuhjn.txt");
		assertNotNull(io);
	}
}
