import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.channels.*;
import java.nio.*;
import java.net.*;
import java.util.Iterator;

public class Cliente {

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int pto = 9999;
        try {
            //scanner
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            //no block socket
            SocketChannel cl = SocketChannel.open();
            cl.configureBlocking(false);
            Selector sel = Selector.open();
            cl.connect(new InetSocketAddress(host, pto));
            cl.register(sel, SelectionKey.OP_CONNECT);

            while (true) {
                sel.select();
                Iterator<SelectionKey> it = sel.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey k = (SelectionKey) it.next();
                    it.remove();
                    if (k.isConnectable()) {
                        SocketChannel ch = (SocketChannel) k.channel();
                        if (ch.isConnectionPending()) {
                            try {
                                ch.finishConnect();
                                System.out.println("Conexion establecida.. Escribe un mensaje <ENTER> para enviar \"SALIR\" para teminar");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }//catch
                        }//if_conectionpending
                        //Puede ser lectura o escritura
                        ch.register(sel, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        continue;
                    }//if
                    if (k.isWritable()) {
                        SocketChannel ch2 = (SocketChannel) k.channel();
                        String msj = br.readLine();
                        ByteBuffer b = ByteBuffer.wrap(msj.getBytes());
                        ch2.write(b);
                        if (msj.equalsIgnoreCase("SALIR")) {
                            System.out.println("Termina aplicacion...");
                            ch2.close();
                            System.exit(0);
                        } else {
                            k.interestOps(SelectionKey.OP_READ);
                            continue;
                        }//else
                    } else if (k.isReadable()) {
                        SocketChannel ch2 = (SocketChannel) k.channel();
                        ByteBuffer b = ByteBuffer.allocate(30);
                        b.clear();
                        int n = ch2.read(b);
                        b.flip();
                        String msj = new String(b.array(),0,n);
                        if (msj.length() <= 5) {
                            System.out.println("MODO FACIL: ");
                        } else if (msj.length() >= 7 && msj.length() <= 11) {
                            System.out.println("MODO MEDIO: ");
                        } else if(msj.length() > 11) {
                            System.out.println("MODO DIFICIL: ");
                        }

                        int turno = 5;
                        char[] secret = msj.replaceAll(".", "*").toCharArray();
                        boolean victoria = false;
                        while (turno != 0) {
                            if(new String(secret).contains("*")){
                                System.out.println("Oportunidades restantes: "+turno+ " mensaje: "+new String(secret)+" Ingresa una letra: ");
                                String aux = br.readLine();
                                if (msj.contains(aux)) {
                                    for(int i = 0; i< msj.length();i++){
                                        if(msj.charAt(i) == aux.charAt(0)){
                                            secret[i] = aux.charAt(0);
                                        }
                                    }
                                } 
                                else {
                                    turno--;
                                }    
                            }
                            else{
                                victoria = true;
                                break;
                            }
                        }
                        if(victoria){
                            System.out.println(msj+ ", Ganaste!!");
                        }
                        else{
                            System.out.println("La palabra era "+msj+" ,mejor suerte la proxima.");
                            
                        }

                        k.interestOps(SelectionKey.OP_WRITE);
                        continue;
                    }//if
                }//while
            }//while
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//main
}