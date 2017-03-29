# SWA SS17, 1. Praktikumsaufgabe: Reflection
Die Idee hinter dieser Aufgabe ist es, einen Mechanismus zu bauen, mit dem sich Informationen über Objekte komfortabel ausgeben lassen. So etwas bietet beispielsweise ein debugger, mit dem sich der Zustand von Objekten inspizieren lässt. Denkbar wäre auch, die toString()-Methode(n) zu redefinieren durch etwas in der Art:

```
@Override  
public String toString() {
    return new Renderer(this).render();
}
```

Dieses Projekt wurde mit Maven erstellt:

[Maven Downloadsite](https://maven.apache.org/download.cgi)

Projekt mittels Maven erstellen für die eigene IDE:
------
**Eclipse:** `mvn eclipse:eclipse`

Testen: 
------
`mvn test`