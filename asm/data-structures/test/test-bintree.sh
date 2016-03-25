#/bin/bash
VALGRIND=valgrind
INPUTDIR=input
ACTUALDIR=actual
EXPECTEDDIR=expected
BINFILE=../bintree

OK=1
OKVALGRIND=1
mkdir $ACTUALDIR
for i in $( ls $INPUTDIR/*.in); do
    echo $i
    $VALGRIND --leak-check=yes --error-exitcode=1 -q $BINFILE -f $i
    if [ $? != "0" ]; then
        OKVALGRIND=0
    fi
    NAME=`basename $i`
    EXPECTED=$EXPECTEDDIR/${NAME/in/expected.out}
    ACTUAL=$ACTUALDIR/${NAME/in/out}
    diff -q $EXPECTED $ACTUAL
    if [ $? != "0" ]; then
        OK=0
    fi
done
if [ $OK != "0" ] && [ $OKVALGRIND != "0" ]; then
    echo "Tests ended successfully"
fi
rm -rf $ACTUALDIR
