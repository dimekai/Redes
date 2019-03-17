package sources;
import java.util.*;
import java.util.regex.Pattern;

public class HttpRequest {
	private String peticion;
	private Map<String,String> encabezado;

	public HttpRequest(String peticion){
		this.peticion=peticion;
		encabezado=new HashMap<String,String>();
		getEncabezado(peticion);
	}

	public String getTipo(){
		switch (peticion.substring(0,peticion.indexOf(" "))) {
			case "GET":
				return "GET";
			case "POST":
				return "POST";
			case "HEAD":
				return "HEAD";
			case "DELETE":
				return "DELETE";
			default:
				return "HTTP/1.0 501 Not Implemented";			
		}
	}

	public void getEncabezado(String linea){
		String [] headerParams = linea.split(Pattern.quote("\n"));
        for(int i = 0; i<headerParams.length;i++){
            try{
                if(i==0){
                    String headerValues[] = headerParams[i].split(Pattern.quote(" "));
                    if(headerValues[0].length() > 0){
                        System.out.println("header REC: "+headerValues[0]+" value: "+headerValues[1]);
                        encabezado.put(headerValues[0], headerValues[1]);
                    }
                }else{
                    if(headerParams[i].length() > 0){
                        String headerValues[] = headerParams[i].split(Pattern.quote(":"));
                        encabezado.put(headerValues[0], headerValues[1]);
                    }
                }
            }catch(ArrayIndexOutOfBoundsException e){
                
            }
        }
	}

	public void imprimeEncabezado(){
		Iterator iterador=encabezado.keySet().iterator();
		while(iterador.hasNext()){
			String parametro=(String)iterador.next();
			System.out.println(parametro+": "+encabezado.get(parametro));
		}
	}

	public String getValue(String header){
        //System.out.println("Header: "+header);
        if(encabezado.containsKey(header)){
            //System.out.println("Value: "+encabezado.get(header));
            return encabezado.get(header);
        }else{
            return "-1";
        }
    }

    public String getUri(){
    	String parametrosEncabezado[]=peticion.split(Pattern.quote("\n"));
    	String valorEncabezados[]=parametrosEncabezado[0].split(Pattern.quote(" "));
    	return valorEncabezados[1].substring(1,valorEncabezados[1].length());
    }
}