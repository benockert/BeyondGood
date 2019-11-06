OVERALL DESIGN:

We decided to design our code in a way that ensures every type of cell is distinct from each other and that there were no type overlaps. When looking at the assignment, we realized that each cell could be a type of formula, which is why we created the CellFormula interface and had each cell type implement that. Since a formula can contain a value, a function, or a reference, we made cell types for each of these (with a value being either a boolean, string, or double). Cells that are blank are represented as being null through our implementation.

We also decided to design out model to take in the different functions (SUM, PRODUCT, <, and REPT) as function objects. That way, we could implement a visitor pattern and ensure that each type of cell was handled correctly for each type of function. Each of these function objects implemented the IFunction interface. 

To represent our actual spreadsheet, we decided to represent it as a HashMap with CellFormulas mapped to Coordinates. This was so that each cell has a coordinate when it was created, and therefore a coordinate would not have to be a field of a cell type (and thus is not susceptible to user manipulation). 

Below lists a more detailed structure for each file we added to the given starter code.

----------

INTERFACE/CLASS STRUCTURE (CELL):

(I) CellFormula - represents any type of cell
	(C) CellBoolean - represents a cell with a boolean value
	(C) CellDouble - represents a cell with a double value
	(C) CellString  - represents a cell with a string value
	(C) CellReference  - represents a cell with a reference
	(C) CellFunction - represents a cell with a function

----------

INTERFACE/CLASS STRUCTURE (FUNCTION):

(I) CellVisitor - represents the visitor pattern for determining how to apply function objects to different types of cells
	(I) IFunction - represents any type of function
		(C) Add - represents the function of adding an arbitrary number of inputs
		(C) Multiply - represents the function of multiplying an arbitrary number of inputs
		(C) LessThan - represents the function of checking if one value is less than another
		(C) Repeat - represents the function of repeating a value x number of times

----------

INTERFACE/CLASS STRUCTURE (MODEL):

(I) Worksheet - given in starter code
	(C) BasicWorksheetModel - represents the model for a basic worksheet
	(C) WorksheetBuilderImpl - builds a worksheet 

----------

INTERFACE/CLASS STRUCTURE (SEXP):

(I) SexpVisitor - given in starter code
	(C) SexpVisitorHandler - determines what to do with the visitor pattern and how to apply it to each type of Sexp
