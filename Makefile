antlrjar = antlr-4.13.0-complete.jar

###### FOR LINUX AND MAC -- uncomment the following line if you do not use Windows:
# classpath = '$(antlrjar):.'

###### FOR WINDOWS -- comment the following line if you do not use Windows:
classpath = '$(antlrjar);.'

antlr4 = java -cp $(classpath) org.antlr.v4.Tool
grun = java -cp $(classpath) org.antlr.v4.gui.TestRig
SRCFILES = main.java
GENERATED = ccListener.java ccBaseListener.java ccParser.java ccLexer.java

all:	
	make grun

ccLexer.java:	cc.g4
	$(antlr4) cc.g4

ccLexer.class:	ccLexer.java
	javac -cp $(classpath) $(GENERATED)

grun:	ccLexer.class cc.txt
	$(grun) cc start -gui -tokens cc.txt 
