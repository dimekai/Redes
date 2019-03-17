package sources;
import java.nio.channels.*;
import java.nio.*;
import java.net.*;
import java.util.Iterator;
import java.io.*;
import java.util.*;

public class AdministradorHttp {
    public static void main(String[] args){
       int pto=9999;
       try{
          ArrayList<Integer> servidores=new ArrayList<Integer>();
          BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
          System.out.println("Numero de Servidores:");
          int nServidores=Integer.parseInt(br.readLine());
          for (int i=0;i<nServidores ; i++) {
            System.out.println("Dame el puerto para el servidor"+(i+1));
            int puerto=Integer.parseInt(br.readLine());
            servidores.add(puerto);
          }
          int puerto1=-1,posicion=0;
          String respuesta="";
          ServerSocketChannel s = ServerSocketChannel.open();
          s.configureBlocking(false);
          s.setOption(StandardSocketOptions.SO_REUSEADDR, true);
          s.socket().bind(new InetSocketAddress(pto));
          Selector sel = Selector.open();
          s.register(sel,SelectionKey.OP_ACCEPT);
          GestorServidores serv=new GestorServidores(3);
           System.out.println("Servicio iniciado..esperando clientes..");
          while(true){
              sel.select();
              Iterator<SelectionKey>it= sel.selectedKeys().iterator();
              while(it.hasNext()){
                  SelectionKey k = (SelectionKey)it.next();
                  it.remove();
                  if(k.isAcceptable()){
                      SocketChannel cl = s.accept();
                      System.out.println("Cliente conectado desde->"+cl.socket().getInetAddress().getHostAddress()+":"+cl.socket().getPort());
                      cl.configureBlocking(false);
                      cl.register(sel,SelectionKey.OP_READ);
                      puerto1=servidores.get(posicion);
                      Iterator<Integer> nombreIterator = servidores.iterator();
                      while(nombreIterator.hasNext()){
                        int elemento = nombreIterator.next();
                        System.out.print(elemento+" / ");
                      }
                      continue;
                  }//if
                  if(k.isReadable()){
                      SocketChannel ch =(SocketChannel)k.channel();
                      ByteBuffer b = ByteBuffer.allocate(2000);
                      b.clear();
                      int n = ch.read(b);
                      
                      if ( n > -1) {
                        String msj = new String(b.array(),0,n);
                        respuesta="";
                        System.out.println("Peticion Recibida: "+msj);                        
                        ConexionServidores cs=new ConexionServidores(serv,puerto1,puerto1,msj);
                        cs.start();
                        cs.join();
                        respuesta=cs.getMensaje();
                        respuesta=respuesta.replace("%n","\n");
                        ByteBuffer b2=ByteBuffer.wrap(respuesta.getBytes());
                        ch.write(b2);
                        k.interestOps(SelectionKey.OP_WRITE);
                        continue;
                      }
                  }//if_readable
                  if(k.isWritable()){
                    if (!respuesta.equals("")) {
                      System.out.println("\n\nRespuesta a enviar:\n "+respuesta);
                       SocketChannel ch2 = (SocketChannel)k.channel();
                       ByteBuffer b = ByteBuffer.wrap(respuesta.getBytes());
                       ch2.write(b);
                       k.interestOps(SelectionKey.OP_READ);
                       System.out.println("La respuesta fue enviada");
                       servidores.add(puerto1);
                       posicion++;
                       puerto1=-1;
                       respuesta="";
                       continue;
                    }
                   }//if_writable
              }//while
          }//while
       }catch(Exception e){
           e.printStackTrace();
       }//catch
    }//main
}