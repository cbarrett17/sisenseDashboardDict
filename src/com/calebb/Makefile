# gnu make Makefile

# define some variables
PROJECT = SDDict
JCC = javac
ST = SDDictTest
SHELL = bin/sh
CLASSPATH = "system-rules-1.18.0.jar;commons-cli-1.5.0.jar;."
RESOURCES = "PWGen_en_US.properties;PWGen_hi_IN.properties;."
DEFAULT_RUN = -a

# define some "targets"
default: compile

compile: SDDict.java
	# command to build target
	echo Compiling...
	$(JCC) -cp $(CLASSPATH) $(PROJECT).java

run-en_US: compile
	java -cp $(CLASSPATH) -Duser.language=en -Duser.country=US $(PROJECT) -h
	java -cp $(CLASSPATH) -Duser.language=en -Duser.country=US $(PROJECT) $(DEFAULT_RUN)

run-hi_IN: compile
	java -cp $(CLASSPATH) -Duser.language=hi -Duser.country=IN $(PROJECT) -h
	java -cp $(CLASSPATH) -Duser.language=hi -Duser.country=IN $(PROJECT) $(DEFAULT_RUN)