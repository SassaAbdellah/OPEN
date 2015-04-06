


for i in $(find . -name  *.dvi)
do
rm $i
done


for i in $(find . -name  "*.log")
do
rm $i
done

for i in $(find . -name  "*.aux")
do
rm $i
done

for i in $(find . -name  "*~")
do
rm $i
done

for in in $(find . -name  "*.toc")
do
rm $i
done


rm master.pdf
rm master.ps


