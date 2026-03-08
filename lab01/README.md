Laboratorium 1: archiwizowanie plików i funkcja skrótu SHA-256
----

### Uruchomienie programu
#### Linia komend (bash)

Kompilacja
```bash
javac --module-source-path pwr.edu.pl.lib=lib/src/main/java --module-source-path pwr.edu.pl.ui=ui/src/main/java --module pwr.edu.pl.lib,pwr.edu.pl.ui -d out
```

Utworzenie plików jar
```bash
mkdir -p modules
```
```bash
jar --create --file modules/pwr.edu.pl.lib.jar -C out/pwr.edu.pl.lib .
```
```bash
jar --create --file modules/pwr.edu.pl.ui.jar --main-class ui.MainApp -C out/pwr.edu.pl.ui . -C ui/src/main/resources .
```

jlink
```bash
jlink --module-path modules --add-modules pwr.edu.pl.ui,pwr.edu.pl.lib --launcher filezipper=pwr.edu.pl.ui/ui.MainApp --output dist
```

Uruchomienie aplikacji
```bash
./dist/bin/filezipper
```

W przypadku niesprecyzowania --launcher
```bash
dist/bin/java -m pwr.edu.pl.ui/ui.MainApp
```

#### Narzędzie budowania (Maven)
```bash
mvn clean package
```
Uruchomienie aplikacji
```bash
./mod-jlink/target/maven-jlink/default/bin/filezipper 
```