Wordle Utilities
================

This code provides a utility to help solving of Wordle problems.
The daily Wordle challenge can be found here: https://www.powerlanguage.co.uk/wordle/

This Branch
-----------

The 'main' branch implements the wordle utils as a command line application.
This branch refactors the code and utilises Spring Boot to provide an http
API for generating wordle guesses.

Usage
-----
To start the spring boot application:

    java -jar wordle-solver.jar

This starts the server on the default port 8080. Normal spring boot configuration
options can be used to override all the main settings.

The api can be called using the following URI:

   http://localhost:8080/solve?pattern=<pattern>&exclusions=<exclusions>

The pattern query parameter is mandatory and uses the following elements:

* '-' for an unknown letter
* uppercase for a correct letter in the right place
* lowercase for a letter that's in the word but not at this location

Examples:

    pattern=-----   : No guesses
    pattern=S----   : Letter 's' present in the word at the correct position
    pattern=--e--   : Letter 'e' present in the word but not at this position

The exclusions query parameter is optional and if supplied is a list of letters that have been tried and found to
not be present in the word. Example:

    excludions=arf

Therefore, a complete example is:

    curl http://localhost:8080/solve?pattern=S-e--&exclusions=arf

The output of the API call is a json document containing a list of possible words ordered by probability:

    [
      {
        "word": "stile",
        "probability": 0.2883618312189741 
        "letterProbabilities": [
          1.0, 
          0.11858797573083288, 
          0.12465526751241036, 
          0.07225592939878654, 
          0.12630998345284059
        ]
      },
      ...
    }

The first probability is the average probability of each letter being correct.

This list of additional figures is the probabilities of each individual character in the word being correct.

Algorithm
---------

### Part 1
The wordle.service filter uses the following algorithm:

First it filters the wordle.service down to only words that:

* have the correct number of letters
* have the letters at known positions present

It then further filters the first set of word into a second set that only contains those words  that:

* do not contain any of the excluded letters
* contain all the mis-located letters in any of the other locations
* on early passes (< 6 excluded letters) words with repeated letters
are also excluded as this increases the chances of early letter matches.

The result is then two sets of words:

* One with all the words that could occur with just the known letters
* One with all the words that could occur given all the other constraints taken into account

### Part 2
For each of these two sets of words an analysis is then generated that looks
at the probability of each letter occurring at the given location in the set of words.

The candidate generator then takes the 5 most common letters at each location in the
second set of words, generates every possible combination of these letters and restricts
them to only words present in this set of words. These are the guess candidates.

Finally, the solution sorts these words based on the probabilities of each letter
occurring at that position in the wider first set of words.

The words are then listed in descending order of probability.


