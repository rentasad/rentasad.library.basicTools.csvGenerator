package rentasad.library.tools.csvGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Gustini GmbH (2018)
 * Creation: 06.11.2015
 * gustini.library.basicTools
 * rentasad.library.tools.csvGenerator
 *
 * @author Matthias Staud
 * <p>
 * <p>
 * Description: CSV Generation Tool
 */
public class CSVGenerator
{
	private final String trennzeichen;
	private final String satzTrenner;
	private final boolean writeHeader;

	/**
	 * @param trennzeichen - Trennzeichen der CSV-Datei
	 * @param satzTrenner  - Satztrenner
	 * @param writeHeader  - soll Header geschrieben werden?
	 */
	public CSVGenerator(String trennzeichen, String satzTrenner, boolean writeHeader)
	{
		super();
		this.trennzeichen = trennzeichen;
		this.satzTrenner = satzTrenner;
		this.writeHeader = writeHeader;
	}

	/**
	 * Generates a CSV file from a list of ICsvGeneratorDataEntry objects.
	 *
	 * @param csvFileName      Name of the CSV file to be generated.
	 * @param valueEntriesList List of ICsvGeneratorDataEntry objects containing data for the CSV.
	 * @return true if the CSV file was successfully created, otherwise false.
	 * @throws IOException if an I/O error occurs during file writing.
	 */
	public boolean generateCSVFromICsvGeneratorDataEntry(final String csvFileName, final List<ICsvGeneratorDataEntry> valueEntriesList) throws IOException
	{
		ICsvGeneratorDataEntry[] valueEntries = valueEntriesList.toArray(new ICsvGeneratorDataEntry[0]);
		return generateCSVFromICsvGeneratorDataEntry(csvFileName, valueEntries);
	}

	/**
	 * Generates a CSV file from the provided ICsvGeneratorDataEntry array.
	 *
	 * @param fileName     the name of the CSV file to be created.
	 * @param valueEntries an array of ICsvGeneratorDataEntry objects containing the data entries for the CSV.
	 * @return true if the CSV file was successfully created, false otherwise.
	 * @throws IOException if an I/O error occurs while writing to the file.
	 */
	public boolean generateCSVFromICsvGeneratorDataEntry(final String fileName, final ICsvGeneratorDataEntry[] valueEntries) throws IOException
	{

		boolean success = false;

		FileWriter fileWriter = new FileWriter(fileName);

		for (int i = 0; i < valueEntries.length; i++)
		{
			ICsvGeneratorDataEntry value = valueEntries[i];
			String[] headerStringArray = value.getCSVHeaderStringArray();

			if (i == 0 && this.writeHeader)
			{
				StringBuilder headerString = new StringBuilder();
				/*
				 * HEADER der CSV schreiben
				 */
				for (int col = 0; col < headerStringArray.length; col++)
				{
					if (col == 0)
					{
						headerString.append(this.satzTrenner)
									.append(headerStringArray[col])
									.append(this.satzTrenner);
					}
					else
					{
						headerString.append(trennzeichen)
									.append(this.satzTrenner)
									.append(headerStringArray[col])
									.append(this.satzTrenner);
					}
				}
				fileWriter.write(headerString.toString());
				fileWriter.append('\r');
				fileWriter.append('\n');
			}
			/**
			 * Zeilen schreiben.
			 */
			StringBuilder zeile = new StringBuilder();
			for (int col = 0; col < headerStringArray.length; col++)
			{

				if (col == 0)
				{
					zeile.append(this.satzTrenner)
						 .append(value.getValueEntry(col))
						 .append(this.satzTrenner);
				}
				else
				{
					zeile.append(trennzeichen)
						 .append(this.satzTrenner)
						 .append(value.getValueEntry(col))
						 .append(this.satzTrenner);
				}

			}

			fileWriter.write(zeile.toString());
			fileWriter.append('\r');
			fileWriter.append('\n');
		}
		fileWriter.close();
		File checkFile = new File(fileName);
		success = checkFile.exists();
		return success;
	}

	/**
	 * Schreibt UTF8-konforme CSV-Datei aus implementierten
	 * ICSVGeneratorDataEntry - Interface
	 *
	 * @param fileName
	 * @param valueEntries
	 * @return boolean ob Erstellung der CSV-Datei geklappt hat.
	 * @throws IOException
	 */
	public boolean generateCSVFromICsvGeneratorDataEntryUTF8(final String fileName, final ICsvGeneratorDataEntry[] valueEntries) throws IOException
	{

		boolean success = false;

		OutputStream os = new FileOutputStream(fileName);
		/*
		 * Bytes Prefix faer UTF8-Codierung
		 * 239 EF
		 * 187 BB
		 * 191 BF
		 */
		os.write(239);
		os.write(187);
		os.write(191);

		PrintWriter out = new PrintWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));

		for (int i = 0; i < valueEntries.length; i++)
		{
			ICsvGeneratorDataEntry value = valueEntries[i];
			String[] headerStringArray = value.getCSVHeaderStringArray();

			if (i == 0 && this.writeHeader)
			{
				StringBuilder headerString = new StringBuilder();
				/*
				 * HEADER der CSV schreiben
				 */
				for (int col = 0; col < headerStringArray.length; col++)
				{
					if (col == 0)
					{
						headerString.append(this.satzTrenner)
									.append(headerStringArray[col])
									.append(this.satzTrenner);
					}
					else
					{
						headerString.append(trennzeichen)
									.append(this.satzTrenner)
									.append(headerStringArray[col])
									.append(this.satzTrenner);
					}
				}
				out.print(headerString);
				out.append('\n');
			}
			/**
			 * Zeilen schreiben.
			 */
			String zeile = "";
			for (int col = 0; col < headerStringArray.length; col++)
			{
				if (col == 0)
				{
					zeile += this.satzTrenner + value.getValueEntry(col) + this.satzTrenner;
				}
				else
				{
					zeile += trennzeichen + this.satzTrenner + value.getValueEntry(col) + this.satzTrenner;
				}

			}

			out.print(zeile);
			out.append('\n');
		}
		out.flush();
		out.close();
		File checkFile = new File(fileName);
		success = checkFile.exists();
		return success;
	}
}
