antlrjar = antlr-4.13.0-complete.jar

###### FOR LINUX AND MAC -- uncomment the following line if you do not use Windows:
classpath = '$(antlrjar):.'

###### FOR WINDOWS -- comment the following line if you do not use Windows:
# classpath = '$(antlrjar);.'

antlr4 = java -cp $(classpath) org.antlr.v4.Tool
grun = java -cp $(classpath) org.antlr.v4.gui.TestRig
GENERATED = ccListener.java ccBaseListener.java ccParser.java ccLexer.java ccVisitor.java ccBaseVisitor.java

all:	
	make run

ccLexer.java:	cc.g4
	$(antlr4) -visitor cc.g4

ccLexer.class:	ccLexer.java
	javac -cp $(classpath) $(GENERATED)

main.class:	ccLexer.java main.java
	javac -cp $(classpath) $(GENERATED) main.java

run:	main.class
	java -cp $(classpath) main cc.txt

grun:	ccLexer.class cc.txt
	$(grun) cc start -gui -tokens cc.txt 
mclear:
	rm -f ccParser*
	rm -f *.class
	rm -f cc*.java
	rm -f *.tokens
	rm -f *.interp
	rm -f .DS_Store
	rm -rf .idea/
	rm -f *.iml
	rm -rf out/*

wclear:
	del /f /q ccParser*
	del /f /q *.class
	del /f /q cc*.java
	del /f /q *.tokens
	del /f /q *.interp
	del /f /q .DS_Store
	rd /s /q .idea
	del /f /q *.iml
	rd /s /q out\*

