
Directory Structure

    readme.txt    this file
	./src          all source code 
	./tools       Jar files for JFlex and CUP
	./test        put your test files in this directory
	./writeup     put your project write-up in this directory
	
Building

	In Eclipse:
		- JFlex will run automatically whenever ic/lex/ic.flex 
		  is saved.
		- JavaCup will run automatically whenever ic/parser/ic.cup
		  is saved.
		- All Scala/Java files will build whenever a file is saved.
		
	In a shell:
		- run "make" or "make source" to build the Java files
		- run "make dump" to see JavaCup output
		- run "make clean" to remove unnecessary files
		- run "make all" to do all of the above
		
Running

	In Eclipse:
		- Choose "Run -> Run..." from the menu
		- Create a "New Application" run configuration
		- Set the name to something descriptive, ie "PA 1 - test1"
		- Set the project to be the pa1 project
		- Set the Main class to ic.Compiler
		- In the Arguments tab, set the program arguments
		  to be "test/test1.ic", or whatever arguments you
		  wish to pass to the program when it starts.
		- Press Run

		You can run the same configuration again by clicking on the
		green circle with the white triangle in the toolbar.  

	In a shell:
		- run "scala -classpath bin:tools/java-cup-11a.jar ic.Compiler test/test1.ic"

     
SVN

	In Eclipse:
		- To add, remove, commit, etc., Right-Click on the folder/file of interest
		  in the Package Explorer panel, and select options from the "Team" command
	In a shell:
		- use the svn command.  Type "svn help" to see a list of commands.
		
	Note: If you modify project files from outside Eclipse and then use Eclipse, you 
	      must tell Eclipse to refresh its view of the project: select the project 
	      root in the Package Explorer panel, and then chose "File -> Refresh".
	      
