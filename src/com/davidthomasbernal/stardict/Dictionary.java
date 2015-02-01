package com.davidthomasbernal.stardict;

import java.io.File;
import java.util.List;

public class Dictionary {

    protected final DictionaryDefinitions definitions;
    protected final DictionaryIndex index;
    protected final DictionaryInfo info;

    /**
     * Make a dictionary, using the ifo at path
     *
     * The remaining files (minimally an idx and a dict or dict.dz) should be in the same directory, with the same name,
     * but their respective extensions.
     *
     * @param path
     * @return
     */
    public static Dictionary fromIfo(String path) {
        File ifo = new File(path);
        String abs = ifo.getAbsolutePath();

        if (!ifo.isFile() || !ifo.exists()) {
            throw new IllegalArgumentException("File at path is not a file, or does not exist.");
        }

        String ifoName = ifo.getName();
        int dotIndex = ifoName.lastIndexOf(".");
        if (dotIndex < 0 || !ifoName.substring(dotIndex + 1).equals("ifo")) {
            throw new IllegalArgumentException("File at path must be a .ifo file.");
        }

        String ifoPath = ifo.getParentFile().getAbsolutePath();
        String name = ifoName.substring(0, ifoName.lastIndexOf("."));

        File index = new File(ifoPath, name + ".idx");
        boolean hasIdx = index.exists() && index.isFile();
        if (!hasIdx) {
            index = new File(index, ".gz");
            hasIdx = index.exists() && index.isFile();
        }

        if (!hasIdx) {
            throw new IllegalArgumentException("Idx file does not exist");
        }

        File dict = new File(ifoPath, name + ".dict");
        boolean hasDict = false;
        hasDict = index.exists() && index.isFile();

        if (!hasDict) {
            dict = new File(dict, ".dz");
            hasDict = index.exists() && index.isFile();
        }

        if (!hasIdx) {
            throw new IllegalArgumentException("Idx file does not exist");
        }


        return new Dictionary(
                DictionaryInfo.fromIfo(ifo),
                null,
                null
        );
    }

    public Dictionary(DictionaryInfo info, DictionaryIndex index, DictionaryDefinitions definitions) {
        this.info = info;
        this.index = index;
        this.definitions = definitions;
    }

    public String getName() {
        return info.getName();
    }

    public long getWordCount() {
        return info.getWordCount();
    }

    public boolean containsWord(String word) {
        return false;
    }

    public List<String> getDefinitions(String word) {
        return null;
    }

    public List<String> getWords() {
        return null;
    }

    public List<String> searchForWord(String search) {
        return null;
    }
}