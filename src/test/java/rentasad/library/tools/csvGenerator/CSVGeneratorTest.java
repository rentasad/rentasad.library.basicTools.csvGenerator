package rentasad.library.tools.csvGenerator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for CSVGenerator to ensure CSV generation works as expected.
 *
 * Unterdrücken der Warnung:
 *
 * 1.
 *     - `-XX:+EnableDynamicAgentLoading`
 */
public class CSVGeneratorTest {

	private CSVGenerator csvGenerator;
	private final String trennzeichen = ",";
	private final String satzTrenner = "\"";
	private final boolean writeHeader = true;

	@BeforeEach
	public void setUp() {
		csvGenerator = new CSVGenerator(trennzeichen, satzTrenner, writeHeader);
	}

	@Test
	public void testGenerateCSVFromICsvGeneratorDataEntry() throws Exception {
		// Arrange
		String csvFileName = "test.csv";
		ICsvGeneratorDataEntry entry1 = Mockito.mock(ICsvGeneratorDataEntry.class);
		ICsvGeneratorDataEntry entry2 = Mockito.mock(ICsvGeneratorDataEntry.class);

		Mockito.when(entry1.getCSVHeaderStringArray()).thenReturn(new String[]{"Header1", "Header2"});
		Mockito.when(entry1.getValueEntry(0)).thenReturn("Value1_1");
		Mockito.when(entry1.getValueEntry(1)).thenReturn("Value1_2");

		Mockito.when(entry2.getCSVHeaderStringArray()).thenReturn(new String[]{"Header1", "Header2"});
		Mockito.when(entry2.getValueEntry(0)).thenReturn("Value2_1");
		Mockito.when(entry2.getValueEntry(1)).thenReturn("Value2_2");

		List<ICsvGeneratorDataEntry> valueEntriesList = Arrays.asList(entry1, entry2);

		// Act
		boolean result = csvGenerator.generateCSVFromICsvGeneratorDataEntry(csvFileName, valueEntriesList);

		// Assert
		assertTrue(result, "CSV generation should be successful");
		File file = new File(csvFileName);
		assertTrue(file.exists(), "Generated CSV file should exist");
		assertTrue(file.length() > 0, "Generated CSV file should not be empty");

		// Clean up
		file.delete();
	}

	@Test
	public void testGenerateCSVFromICsvGeneratorDataEntryUTF8() throws Exception {
		// Arrange
		String csvFileName = "test_utf8.csv";
		ICsvGeneratorDataEntry entry1 = Mockito.mock(ICsvGeneratorDataEntry.class);
		ICsvGeneratorDataEntry entry2 = Mockito.mock(ICsvGeneratorDataEntry.class);

		Mockito.when(entry1.getCSVHeaderStringArray()).thenReturn(new String[]{"Header1", "Header2"});
		Mockito.when(entry1.getValueEntry(0)).thenReturn("Value1_1");
		Mockito.when(entry1.getValueEntry(1)).thenReturn("Value1_2");

		Mockito.when(entry2.getCSVHeaderStringArray()).thenReturn(new String[]{"Header1", "Header2"});
		Mockito.when(entry2.getValueEntry(0)).thenReturn("Value2_1");
		Mockito.when(entry2.getValueEntry(1)).thenReturn("Value2_2");

		ICsvGeneratorDataEntry[] valueEntriesArray = {entry1, entry2};

		// Act
		boolean result = csvGenerator.generateCSVFromICsvGeneratorDataEntryUTF8(csvFileName, valueEntriesArray);

		// Assert
		assertTrue(result, "UTF-8 CSV generation should be successful");
		File file = new File(csvFileName);
		assertTrue(file.exists(), "Generated UTF-8 CSV file should exist");
		assertTrue(file.length() > 0, "Generated UTF-8 CSV file should not be empty");

		// Clean up
		file.delete();
	}
}