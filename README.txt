Programátorskou dokumentaci lze vygenerovat pomocí mvn javadoc:javadoc a poté se nachází na target\site\apidocs\index.html


Na řádek 38 a 118 v souboru Main, je nutné doplnit cesty k textovým souborům. ( Z prvního se výroky načítají, do druhého se ukládají ). Soubor se vstupními výroky, jehož cestu lze nastavit,
se nachází v kořenové složce projektu a má název "testovacivyroky.txt", jako výstupní lze zvolit jakýkoliv jiný textový soubor. Jelikož v ukázkovém spouštění se nastavují hodnoty proměnných
pro ukázku zjištění správnosti výroku, je vhodné použít tyto výroky, jinak je nutné upravit nastavování proměnných.

Spuštění programu, poté co jsou nastaveny cílové soubory - mvn compile exec:java


Pokud program spustím tímto způsobem, nevypisují se znaky typu "¬". Problém by měl být jenom na Windows, který je nepodporuje v příkazovém řádku. Na Linuxu, by se mělo zobrazovat vše v pořádku.