Wordle Utilities
================

This code provides a utility to help solving of Wordle problems.
The daily Wordle challenge can be found here: https://www.powerlanguage.co.uk/wordle/

Usage
-----

The usage of the utility is as follows:

    java -jar wordle-solver.jar <pattern> [exclusions]

The pattern uses the following elements:

* '-' for an unknown letter
* uppercase for a correct letter in the right place
* lowercase for a letter that's in the word but not at this location

Examples:

    -----   : No guesses
    S----   : Letter 's' present in the word at the correct position
    --e--   : Letter 'e' present in the work but not at this position

The exclusions are a list of letters that have been tried and found to
not be present in the word. Example:

    arf

Therefore, a complete example is:

    java -jar wordle-solver.jar S-e-- arf

The output of the commands is a list of possible words ordered by probability:

    stile: 0.2883618312189741 (1.0, 0.11858797573083288, 0.12465526751241036, 0.07225592939878654, 0.12630998345284059)
    stole: 0.288030888030888 (1.0, 0.11858797573083288, 0.12300055157198014, 0.07225592939878654, 0.12630998345284059)
    stine: 0.2867071152785438 (1.0, 0.11858797573083288, 0.12465526751241036, 0.06398234969663541, 0.12630998345284059)
    stone: 0.28637617209045774 (1.0, 0.11858797573083288, 0.12300055157198014, 0.06398234969663541, 0.12630998345284059)

The first figure after the word is the average probability of each letter being correct.

This list of additional figures is the propbability of each individual character in the word being correct.

Algorithm
---------

### Part 1
The dictionary filter uses the following algorithm:

First it filters the dictionary down to only words that:

* have the correct number of letters
* have the letters at known positions present

From this list of words it then works out the probabilities of each letter appearing
at each position in the subset of dictionary words.

It then further filters the set of word to only those that:

* do not contain any of the excluded letters
* contain all the mis-located letters in any of the other locations

Additionally, on early passes (< 6 excluded letters) words with repeated letters
are also excluded as this increases the chances of early letter matches.

The result is then two sets of words:

* One with all the words that could occur with the given just the known letters
* One with all the words that could occur given all the other constraints taken into account

### Part 2
For each of these two sets of words an analysis is then generated that looks
at the probability of each letter occurring at the given location in the set of words.

The candidate generator then takes the 5 most common letters at each location in the
second set of words, generates every possible combination of these letters and restricts
them to only words present in this set of words. These are the guess candidates.

Finally, the solution sorts these words based on the probabilities of each letter
occurring at that position in the wider first set of words.


