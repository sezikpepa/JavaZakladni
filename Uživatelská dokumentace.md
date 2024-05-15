UŽIVATELSKÁ DOKUMENTACE

**Úvod**

Knihovna poskytuje základní kód pro práci s výroky ve výrokové logice. Poskytuje struktury pro reprezentaci výroku a práci s ním.


**Načtení výroku**

Výroky vstupují do programu ve formě textového řetezce. Mezi každou jednotlivou sloužkou musí být mezera, která zde tedy funguje jako oddělovač. Jedinou výjimkou je negace proměnné, kde mezera naopak být nesmí.

Proměnné mohou být jakkoliv dlouhé řetězce znaků anglické abecedy, kromě znaku "v", který zastupuje disjunkci. Následující znaky reprezentují jednotlivé operátory ve výrokové logice:

¬ : negace proměnné i části výroku
∧ : operátor AND
v : operátor OR
=> : operátor implikace
<=> : operátor ekvivalence
( : závorka otevírající část výroku
) : závorka uzavírající část výroku

Na tomto příkladu lze vidět korektně zapsaný výrok, mezi každou funkční částí se nachází mezera, s výjimkou negace proměnné "q"

--------------------------
( ¬ ( ¬q v p ) => p ) => p
--------------------------

*Třídy používané na načtení výroku*

Knihovna v současnosti poskytuje načítání výroku z konzole a z textového souboru. Na tuto činnost můžeme tedy využít tříd CommandLineParser, respektive FileParser. Metoda loadInput() načte výroky do paměti parseru
pokud bychom chtěli získat výroky ve formě se kterou můžeme dále pracovat v knihovně - stromová struktura výroku - použijeme metodu getExpressions() z těchto tříd. Pokud načítáme výroky z souboru i z konzole, tak
každá řádka reprezentuje samostatný výrok.

*Operátory*

Jelikož výroky se skládají z 5 základních operací - AND, OR, implikace, ekvivalence, NOT - je každý z těchto operátorů reprezentován vlastní třídou. V případě nutnosti implentace dalšího operátoru postačuje zdědit třídu
Operator, která poskytuje základní strukturu pro operátory. Též je možné dynamicky vytvářet výroky v programu, kde nový operátor vytvoříme pouze jednoduše pomocí nového objektu.


*Práce s načteným výrokem*

Již načtené výroky můžeme různě upravovat. Výroky jsou třídy Expression, můžeme tedy využít její metody. Mezi nejzákladnější patří metoda Negate(), která provede negaci výroku. Jelikož takto provedená negace, pouze vloží
negaci před celý výrok, můžeme tuto negaci propagovat do co nejnižších vrstev, což provede metoda SimplifyNots(). Tato metoda udělá propagaci negace až k samotným proměnným, tudíž nebude docházet k negaci jednotlivých
operátorů. U výroků bychom také chtěli zjišťovat jejich pravdivostní hodnoty. Pro tyto účely musíme ale nejprve nastavit hodnoty jednotlivých proměnných. Což udělá metoda setLogicalValuesToVariables(), které vždy
poskytneme HashMapu, kde klíče jsou názvy proměnných a jednotlivé hodnoty jsou pravdivostní hodnoty dané proměnné. Pokud bychom chtěli naopak nastavené hodnoty vymazat, můžeme to provést metodou setAllLogicalValuesToNull()


*Pokročilé operace s výroky*

1) Pokud již máme nastavené pravdivostní hodnoty, můžeme vyhodnotit pravdivostní hodnotu celého výroku - pomocí třídy ExpressionCorrectness. Její metoda isExpressionTrue() na základě
poskytnutých hodnot proměnných rozhodne, zda toto nastavení je korektních řešením výroku a vrátí finální pravděpodobnostní hodnotu.

2) Pokud bychom chtěli získat všechna možná řešení výroku, použijeme metodu getAllSolutions(), která vrátí všechna korektní řešení výroku.

3) Pokud potřebujeme vědět, zda má výrok nějaké řešení efektivní formou. Je v knihovně implementována metoda tablo. Která dokáže efektivně zjistit bez použití hrubé síly, zda má výrok validní řešení. Jelikož tato operace
   využívá kopírování již používaných částí ve stromu a strom je procházen rekurzí, je nutné nastavit dostatečnou paměť zásobníku při používání v aplikaci.


*Uložení výroku*

Pokud jsme již dokončili manipulaci s výrokem a chtěli bychom jej uložit pro další činnosti získáme textovou reprezentaci metodou getStringRepresentation(). Získaný výrok ve formě textu můžeme dále uložit na požadovaná místa.
V současnosti to lze provést jako výpis do konzole, či uložení do souboru.

Pokud jsme zjistit více řešení daného výroku, je implementována třída PossibleSolutionsExpression, která ze všech řešení výroku vytvoří textový řetězec, ze kterého je možné jednoduše
vyčíst všechna řešení.
