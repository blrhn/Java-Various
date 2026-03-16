### Wnioski co do stosowalności maszyny wirtualnej 

Spróbowano odpalić aplikację w następujących konfiguracjach:

```
-Xmx128m -XX:+/-ShrinkHeapInSteps -XX:+UseSerialGC 
```
```
-Xmx128m -XX:+/-ShrinkHeapInSteps -XX:+UseParNewGC  
```
```
-Xmx128m -XX:+/-ShrinkHeapInSteps -XX:+UseParallelGC   
```
```
-Xmx128m -XX:+/-ShrinkHeapInSteps -XX:+UseG1GC   
```

#### Rozmiar sterty
Zdecydowano się na maksymalny rozmiar stery 128 MB (-Xmx128m), aby zachowanie GC wraz z oddawaniem pamięci były lepiej zauważalne. 

#### Zmniejszanie sterty
W przypadku wykorzystywania -XX:-ShrinkHeapInSteps (wyłączone stopniowe zmniejszanie sterty), maszyna wirtualna zwracała całą odzyskaną pamięć
do systemu operacyjnego, a więc w efekcie obiekty z danymi plików były częściej i szybciej usuwane z pamięci. 

W przypadku wykorzystania -XX:+ShrinkHeapInSteps, sterta jest zmniejszana powoli, dlatego też słabe referencje są w stanie przetrwać parę
cykli odśmiecania. 

#### Algorytmy Garbage Collectorów
-XX:+UseSerialGC: jednowątkowy GC, który na bieżąco usuwał zalegające obiekty

-XX:+UseParNewGC: z uwagi oznaczenie tego GC jako deprecated, program nawet się nie uruchomił

-XX:+UseParallelGC: "wydajnościowy GC", obiekty najdłużej przebywały w pamięci. GC optymalizował czas przerw kosztem rzadszego oddawania pamięci. 
Włączone ShrinkHeapInSteps dało najlepsze rezultaty

-XX:+UseG1GC: domyślny algorytm, zamiast czyszczenia całej sterty, sterta jest dzielona na regiony pamięci
