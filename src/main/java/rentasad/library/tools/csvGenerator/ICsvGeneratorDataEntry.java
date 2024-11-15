package rentasad.library.tools.csvGenerator;


public interface ICsvGeneratorDataEntry
{

	/**
	 * Retrieves the value of the specified column number.
	 *
	 * @param colNumber the column number whose value is to be retrieved.
	 * @return the value of the specified column as a String.
	 * @throws IllegalArgumentException if an invalid colNumber is provided.
	 */
	public String getValueEntry(int colNumber) throws IllegalArgumentException;

	/**
	 * Returns the header of the CSV file as a String array.
	 *
	 * @return an array of Strings representing the header of the CSV file, or null if no header is defined.
	 */
	public String[] getCSVHeaderStringArray();
}
