package uk.gov.bristol.send.fileupload.adapter;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Tests {@link ByteCountSizeFormatter}
 * 
 * System only allows uploads sizes of about 5Mb so really don't need to bother testing for GB and higher.
 
 */
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ByteCountSizeFormatterTest {
	private ByteCountSizeFormatter formatter;
	

	@BeforeAll
	public void setUp() {
		formatter = new ByteCountSizeFormatter();
	}

	@AfterAll
	public void tearDown() throws Exception {
		formatter = null;
	}
	@Test
	public void format_bytesLessThanUnitSize() {
		assertEquals("100 B", formatter.format(100));
	}
	
	@Test
	public void format_1500bytes() {
		assertEquals("1.5 KB", formatter.format(1500));
	}
	
	@Test
	public void format_15000bytes() {
		assertEquals("15.0 KB", formatter.format(15000));
	}
	
	@Test
	public void format_150000bytes() {
		assertEquals("150.0 KB", formatter.format(150000));
	}
	
	@Test
	public void format_1500000bytes() {
		assertEquals("1.5 MB", formatter.format(1500000));
	}
	
	@Test
	public void format_15000000bytes() {
		assertEquals("15.0 MB", formatter.format(15000000));
	}
	
	@Test
	public void format_150000000bytes() {
		assertEquals("150.0 MB", formatter.format(150000000));
	}
}