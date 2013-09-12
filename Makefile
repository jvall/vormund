## Promiscuous
MAINCLASS=edu.cs408.vormund.gui.LoginWindow
LIBDEPS=sqlite-jdboc-3.7.2.jar
JARNAME=Vormund.jar

## Directories
SRCDIR=src/
BUILDDIR=build/
OUTDIR=output/
LIBDIR=libs/

## Binaries
JJ=/usr/bin/java
JC=/usr/bin/javac
JAR=/usr/bin/jar

## Gobals [do not edit]
BUILDLIST=${BUILDDIR}build.txt
MOVELIST=${BUILDDIR}move.txt
SRCDIRESCAPED=${SRCDIR:/=\/}
BUILDDIRESCAPED=${BUILDDIR:/=\/}
CLASSPATH=.:../${LIBDIR}${LIBDEPS}
DISPOSE=${BUILDDIR}dispose.tmp

all: compile jar

compile:
	cp ${LIBDIR}*.jar ${BUILDDIR}
	cd ${BUILDDIR} && find ./ -type f -name "*.jar" | xargs -I file ${JAR} -xf file
	rm ${BUILDDIR}*.jar
	find ${SRCDIR} -name "*.java" -print > ${BUILDLIST}
	${JC} -d ${BUILDDIR} -cp .:libs/swing-layout-1.0.jar @${BUILDLIST}
	find ${SRCDIR} -type f \( -iname "*" ! -iname "*.java" ! -iname "*.md" \) > ${MOVELIST}
	touch ${DISPOSE}
	sed -i '${DISPOSE}' -e "s/${SRCDIRESCAPED}//g" ${MOVELIST}
	cat ${MOVELIST} | xargs -I FL cp ${SRCDIR}FL ${BUILDDIR}FL
	rm -f ${MOVELIST} ${DISPOSE} ${BUILDLIST}

jarwlib:
	#find ${BUILDDIR} -type f \( -iname "*" ! -iname "*.md" ! -iname "build.txt" ! -iname "Manifest.txt" \) -print > ${BUILDLIST}
	#sed -i "s/${BUILDDIRESCAPED}//g" ${BUILDLIST}
	cp Manifest.txt ${OUTDIR}
	touch ${DISPOSE}
	sed -i "${DISPOSE}" -e "s/\[CLASSPATH\]/${LIBDEPS}/g" ${OUTDIR}Manifest.txt
	sed -i "${DISPOSE}" -e "s/\[MAINCLASS\]/${MAINCLASS}/g" ${OUTDIR}Manifest.txt
	rm ${DISPOSE}
	cp ${LIBDIR}*.jar ${OUTDIR}
	${JAR} -cfmv ${OUTDIR}${JARNAME} ${OUTDIR}Manifest.txt -C ${BUILDDIR} .

jar:
	cp Manifest.txt ${OUTDIR}
	touch ${DISPOSE}
	sed -i "${DISPOSE}" -e "s/\[MAINCLASS\]/${MAINCLASS}/g" ${OUTDIR}Manifest.txt
	rm ${DISPOSE}
	${JAR} -cfmv ${OUTDIR}${JARNAME} ${OUTDIR}Manifest.txt -C ${BUILDDIR} .

run:
	cd ${BUILDDIR} && ${JJ} ${MAINCLASS}

runwlib:
	cd ${BUILDDIR} && ${JJ} -cp ${CLASSPATH} ${MAINCLASS}

runjar:
	cd ${OUTDIR} && ${JJ} -jar ${JARNAME}

clean:
	find ${BUILDDIR} \( -iname "*" ! -iname "*.md" ! -iname "build" \) -print0 | xargs -0 -I file rm -rf file
	find ${OUTDIR} \( -iname "*" ! -iname "*.md" ! -iname "output" \) -print0 | xargs -0 -I file rm -rf file

