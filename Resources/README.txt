Submit a README file summarizing which features you were able to get working successfully
and which features do not work in your final submission.

Working features:
- highlighting a cell and changing the location of the highlighted cell by clicking 
- infinite scrolling
- editing a cell via text input 
- accepting or rejecting cell edits 
- recursive updates


Non-working features:
- the COMPARE function: takes two arguments and returns whether or not the first argument is
  smaller than the second; our < function does the same operation, however it has different
  syntax
- the SMALLESTSTRING function: accepts a list of arguments and returns the string with the 
  shortest length, ignoring non-strings
- cyclic reference checking: we found their representation of a reference cell to be 
  incompatible with our implementation, as ours required the reference as a string, a Hashmap
  from Coord to our cell interface that was the list of referenced cells, in addition to the
  location of the cell that contains the reference; theirs took an ArrayList of Coords referenced 
  by the cell as well as their entire model