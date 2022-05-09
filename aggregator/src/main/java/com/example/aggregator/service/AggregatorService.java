package com.example.aggregator.service;

import com.example.aggregator.client.AggregatorRestClient;
import com.example.aggregator.model.Entry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AggregatorService {

    private AggregatorRestClient restClient;

    public AggregatorService(AggregatorRestClient restClient) {
        this.restClient = restClient;
    }

    public Entry getDefinitionFor(String word) {

        return restClient.getDefinitionFor(word);
    }

    public List<Entry> getWordsThatContainSuccessiveLettersAndStartsWith(String chars) {

        List<Entry> wordsThatStartWith = restClient.getWordsStartingWith(chars);
        List<Entry> wordsThatContainSuccessiveLetters = restClient.getWordsThatContainConsecutiveLetters();

        List<Entry> common = new ArrayList<>(wordsThatStartWith);
        common.retainAll(wordsThatContainSuccessiveLetters);

        return common;
    }

    public List<Entry> getAllPalindromes() {

        final List<Entry> candidates = new ArrayList<>();

        // Iterate from a to z
        IntStream.range('a', '{')
                .mapToObj(i -> Character.toString(i))
                .forEach(c -> {

                    // get words starting and ending with character
                    List<Entry> startsWith = restClient.getWordsStartingWith(c);
                    List<Entry> endsWith = restClient.getWordsEndingWith(c);

                    // keep entries that exist in both lists
                    List<Entry> startsAndEndsWith = new ArrayList<>(startsWith);
                    startsAndEndsWith.retainAll(endsWith);

                    // store list with existing entries
                    candidates.addAll(startsAndEndsWith);

                });

        // test each entry for palindrome, sort and return
        return candidates.stream()
                .filter(entry -> {
                    String word = entry.getWord();
                    String reverse = new StringBuilder(word).reverse()
                            .toString();
                    return word.equals(reverse);
                })
                .sorted()
                .collect(Collectors.toList());
    }

    /* Extra credit but I not enought time to test. It has some errors, but I think I will fixed if I have more times
    Thank you so much for your class (Mr. Ray Hedgecock) 
    public List<int> getAllPalindromes1() {

        static List<int> candidates = new ArrayList<>();

        for(int i=1;i<=size;i++)
        {
            List<Entry> startsWith = restClient.getWordsStartingWith(c);
            List<Entry> endsWith = restClient.getWordsEndingWith(c);
            List<Entry> start&EndsWith = new ArrayList<>(startsWith); starts&EndsWith.retainAll(endsWith);
            candidates.addAll(starts&EndsWith);
        });
        return candidates.stream()
        void reverse(int n)
        {
            int reverse= 0;

            while (n!=0) {

                int reverse = reverse*10 + n%10;

                n = n/10;}

            return reverse;


        }

    }*/
}
