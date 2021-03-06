package com.davidthomasbernal.stardict.dictionary;

import java.util.*;
import java.util.logging.Logger;

public class DictionaryIndex {
    private final List<IndexEntry> indexFileEntries;

    // Updated also with indexFileEntries frm the synonyms file, if present.
    private final Map<String, Set<IndexEntry>> entryMap;

    public DictionaryIndex(List<IndexEntry> indexFileEntries) {
        this.indexFileEntries = new ArrayList<>(indexFileEntries);
        entryMap = new HashMap<>(this.indexFileEntries.size());

        addToIndex(indexFileEntries);
    }

    public Set<String> getWords() {
        return entryMap.keySet();
    }

    public List<IndexEntry> getIndexFileEntries() {
        return Collections.unmodifiableList(indexFileEntries);
    }

    public IndexEntry getIndexFileEntry(int index) {
        return indexFileEntries.get(index);
    }

    public boolean containsWord(String searchWord) {
        return entryMap.containsKey(searchWord);
    }

    public Set<IndexEntry> getIndexFileEntries(String word) {
        return entryMap.getOrDefault(word, Collections.emptySet());
    }

    public void addToIndex(Collection<IndexEntry> entries) {
        for (IndexEntry entry : entries) {
            for (String word: entry.words) {
                String indexWord = word.toLowerCase();
                entryMap.putIfAbsent(indexWord, new LinkedHashSet<>());
                entryMap.get(indexWord).add(entry);
            }
        }
    }
}
