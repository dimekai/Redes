import java.net.*;
import java.util.LinkedList;
import java.io.*;
public class Cliente{
    public static void main(String args[]){
        try{
            int pto = 9999;
            String dir = "::1";
            InetAddress gpo = null;
            try{
                gpo = InetAddress.getByName(dir);
            }catch(UnknownHostException u){
                System.err.println("Direccion no valida");
                System.exit(1);
            }
            Socket cl = new Socket(dir,pto);
            
            System.out.println("Eliga la dificultad del juego:");
            System.out.println("1.-Novato");
            System.out.println("2.-Intermedio");
            System.out.println("3.-Leyenda");
                        
            //para enviar cadenas (lineas)
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
            String datos=null;
            //lee de consola
            BufferedReader br1= new BufferedReader(new InputStreamReader(System.in));
            //para leer cadenas
            BufferedReader br2 = new BufferedReader(new InputStreamReader(cl.getInputStream()));
            while(true){
                datos = br1.readLine();
                //si no recibe mensaje o se manda salir, termina la  conexion
                if(datos.equals("quit") || datos.equals("")){
                    break;
                }
                //de los contrario, manda el mensaje y escucha el echo
                pw.println(datos);
                pw.flush();
                String echo= br2.readLine();
                String answer = echo.substring(0,echo.length()-1);
                LinkedList<String> hiddenString = new LinkedList<String>();
                for(int i=0;i<answer.length();i++){
                    hiddenString.add("_");
                }

                int intentos = 5, exitos = 0;
                boolean coincidencia = false;
                while(true){
                    coincidencia = false;
                    for(int i=0;i<answer.length();i++){
                        System.out.print(hiddenString.get(i)+" ");
                    }
                    System.out.println("");
                    System.out.println("Te quedan " +intentos+ " oportunidades");
                    String intento = br1.readLine();
                    for(int i=0;i<answer.length();i++){
                        for(int j=0;j<intento.length();j++){
                            if(answer.charAt(i) == intento.charAt(j)){
                                hiddenString.set(i,String.valueOf(answer.charAt(i)));
                                coincidencia = true;
                                exitos++;
                            }
                        }
                    }
                    if(coincidencia == false){
                        intentos--;
                    }
                    if(intentos == 0){
                        System.out.print("Ya perdiste.");
                        break;
                    }
                    if(exitos == answer.length()){
                        for(int i=0;i<answer.length();i++){
                            System.out.print(hiddenString.get(i)+" ");
                        }
                        System.out.println("");
                        System.out.print("Ya ganaste :DDDDDD.");
                        break;
                    }
                }
            }            
            cl.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

//para los atributos sin valor:
// primitivos = 0
// objetos = null
