package com.davidthomasbernal.stardict;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class IfoParserTest {

    @Test
    public void testParseNormal() throws Exception {
        String ifo =
                "StarDict's dict ifo file\n" +
                "version=2.4.2\n" +
                "wordcount=15291\n" +
                "idxfilesize=2301921\n" +
                "bookname=Some dictionary\n" +
                "description=This is a test\n" +
                "date=2015-02-02\n" +
                "sametypesequence=m\n";

        IfoParser parser = new IfoParser(ifo);
        DictionaryInfo info = parser.parse();

        assertEquals("2.4.2", info.getVersion());
        assertEquals(15291, info.getWordCount());
        assertEquals(2301921, info.getIdxFileSize());
        assertEquals("Some dictionary", info.getName());
        assertEquals("m", info.getSameTypeSequence());
        assertEquals("2015-02-02", info.getProperty("date"));
        assertEquals("This is a test", info.getProperty("description"));
    }
    @Test
    public void testParseSpacey() throws Exception {
        String ifo =
                "StarDict's dict ifo file \n" +
                "version = 2.4.2\n" +
                "wordcount=15291 \n" +
                "idxfilesize= 2301921\n" +
                "bookname= Some dictionary\n" +
                "description =This is a test\n" +
                " date=2015-02-02\n" +
                " sametypesequence =m\n";

        IfoParser parser = new IfoParser(ifo);
        DictionaryInfo info = parser.parse();

        assertEquals("2.4.2", info.getVersion());
        assertEquals(15291, info.getWordCount());
        assertEquals(2301921, info.getIdxFileSize());
        assertEquals("Some dictionary", info.getName());
        assertEquals("m", info.getSameTypeSequence());
        assertEquals("2015-02-02", info.getProperty("date"));
        assertEquals("This is a test", info.getProperty("description"));
    }

    @Test(expected=NumberFormatException.class)
    public void testBadFileSize() throws Exception {
        String ifo =
                "idxfilesize=not a number\n";

        IfoParser parser = new IfoParser(ifo);
        DictionaryInfo info = parser.parse();
    }

    @Test(expected=NumberFormatException.class)
    public void testBadWordCount() throws Exception {
        String ifo =
                "wordcount=not a number\n";

        IfoParser parser = new IfoParser(ifo);
        DictionaryInfo info = parser.parse();
    }

    @Test(expected=RuntimeException.class)
    public void testBadlyFormattedIfo() throws Exception {
        String ifo =
                "wordcount: 7212\n";

        IfoParser parser = new IfoParser(ifo);
        DictionaryInfo info = parser.parse();
    }
}