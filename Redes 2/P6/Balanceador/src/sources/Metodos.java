package sources;

public interface Metodos {

    public void doGet(HttpRequest httpRequest);
    public void doPost(HttpRequest httpRequest);
    public void doPut(HttpRequest httpRequest);
    public void doHead(HttpRequest httpRequest);
    public void doDelete(HttpRequest httpRequest);
    public void doConnect(HttpRequest httpRequest);
    public void doOptions(HttpRequest httpRequest);
    public void doTrace(HttpRequest httpRequest);
    
}