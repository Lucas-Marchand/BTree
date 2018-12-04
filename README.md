# README

Final Project 321 BTree

	This is the README for the BTree final group Project. These source files
	represent a BTree that reads in a text file representing the human genome,
	and allows the user to search the sequences for a given key.

# Entry points

GeneBankCreateBTree.java : 

	Entry point to create the BTree based on a file given as
	argument


GeneBankSearch.java :

	Entry point to search nodes stored in BTree for given gene sequence.

# Source files
	GeneBankCreateBTree.java - entry point to make the BTree Data Structure
	GeneBankSearch.java      - entry point to search the BTree nodes for a gene sequence.
	BTree.java               - A node based representation of a BTree data structure
	                           implemented with a RandomAccess file of arranged bytes.
	BTreeNode.java           - Represents a single node in a BTree.
	TreeObject.java          - Represents the keys inside of each BTree node.
	
# File Byte Arrangements
	
	BTree file Layout:
	
	| int location of root | int degree | BTreeNode #1 | ... | BTreeNode #(n-1) | root | 
	
	BTreeNode Layout:
	
	| int nodeOffset | int numObject | Boolean leaf | TreeObject #1 | ... | TreeObject #(2*degree-1) | int childOffset #1 | ... | int childOffet #(2*degree) |
	
	TreeObject Layout:
	
	| long key | int frequency |

# Observations

	on onyx our program takes 1 minute to run test3.gbk to create and print out the BTree to a file. 
	So far no cache has been implemented to speed up node searching
