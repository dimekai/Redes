
import java.net.*;
import java.io.*;
import java.util.*;

public class ServidorWeb {

    public static final int PUERTO = 8000;
    ServerSocket ss;

    class Manejador extends Thread {

        protected Socket socket;
        protected PrintWriter pw;
        protected BufferedOutputStream bos;
        protected BufferedReader br;
        protected String FileName;
        protected DataInputStream dis;
        protected Cliente c;

        public Manejador(Socket _socket, Cliente c) throws Exception {
            this.socket = _socket;
            this.c = c;
        }

        public void run() {
            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                bos = new BufferedOutputStream(socket.getOutputStream());
                dis = new DataInputStream(socket.getInputStream());
                pw = new PrintWriter(new OutputStreamWriter(bos));
                String line = br.readLine();
                StringBuilder head = new StringBuilder();
                StringBuilder body = new StringBuilder();
                int contentLength = 0;

                if (line == null) {
                    pw.print("<html><head><title>Servidor WEB");
                    pw.print("</title><body<br>Linea Vacia</br>");
                    pw.print("</body></html>");
                    socket.close();
                    return;
                }
                System.out.println("--------------------------------------------------------------");
                System.out.println("Cliente Conectado desde: " + socket.getInetAddress());
                System.out.println("Por el puerto: " + socket.getPort());
                System.out.println("Datos: " + line + "");

                if (line.toUpperCase().startsWith("POST")) {
                    //leemos el encabezado
                    while (!(line = br.readLine()).equals("")) {
                        head.append('\n' + line);
                        final String contentHeader = "Content-Length: ";
                        if (line.startsWith(contentHeader)) {
                            contentLength = Integer.parseInt(line.substring(contentHeader.length()));
                        }
                    }
                    System.out.println(head);
                    //Lectura de parametros
                    int c = 0;
                    for (int i = 0; i < contentLength; i++) {
                        c = br.read();
                        body.append((char) c);
                    }
                    int indigual = body.indexOf("=") + 1;
                    String nom = body.substring(indigual, body.length());
                    System.out.println("Nombre del archivo:" + nom);
                    File ruta = new File("Archivos/" + nom);
                    if (ruta.exists()) {
                        pw.println("HTTP/1.0 200 OK");
                        pw.flush();
                        pw.println();
                        pw.flush();
                        pw.print("<html><head><title>SERVIDOR WEB");
                        pw.flush();
                        pw.print("</title></head><body><center>");
                        pw.flush();
                        if (nom.contains(".jpg") || (nom.contains(".png")) || (nom.contains(".jpeg")) || (nom.contains(".gif"))) {
                            pw.print("<img src=\"Archivos/" + nom + "\">");
                            pw.flush();
                        } else {
                            if (nom.contains(".pdf") || nom.contains(".txt")) {
                                pw.print("<embed src=\"Archivos/" + nom + "\" width=\"600\" height=\"100%\">");
                                pw.flush();
                            } else {
                                if (nom.contains(".mp3")) {
                                    pw.print("<embed src=\"Archivos/" + nom + "\" width=\"50%\" height=\"50%\" type=\"audio/mpeg\">");
                                    pw.flush();
                                }
                            }
                        }
                        pw.print("</center></body></html>");
                        pw.flush();
                        System.out.println("HTTP/1.0 200 OK");
                    } else {
                        pw.println("HTTP/1.0 404 Not Found");
                        pw.flush();
                        System.out.println("HTTP/1.0 404 Not Found");
                    }
                } else if (line.toUpperCase().startsWith("DELETE")) {
                    //leemos el encabezado
                    while (!(line = br.readLine()).equals("")) {
                        head.append('\n' + line);
                        final String contentHeader = "Content-Length: ";
                        if (line.startsWith(contentHeader)) {
                            contentLength = Integer.parseInt(line.substring(contentHeader.length()));
                        }
                    }
                    System.out.println(head);
                    //Lectura de parametros
                    int c = 0;
                    for (int i = 0; i < contentLength; i++) {
                        c = br.read();
                        body.append((char) c);
                    }
                    System.out.println("Body:" + body);
                    int indigual = body.indexOf("=") + 1;
                    String nom = body.substring(indigual, body.length());
                    System.out.println("Nombre del archivo:" + nom);
                    File ruta = new File("Archivos/" + nom);
                    if (ruta.exists()) {
                        if (ruta.canWrite()) {
                            if (ruta.delete()) {
                                pw.println("HTTP/1.0 200 OK");
                                pw.flush();
                                System.out.println("HTTP/1.0 200 OK");
                            } else {
                                pw.println("HTTP/1.0 500 Internal Server Error");
                                pw.flush();
                                System.out.println("HTTP/1.0 500 Internal Server Error");
                            }
                        } else {
                            pw.println("HTTP/1.0 403 Forbidden");
                            pw.flush();
                            System.out.println("HTTP/1.0 403 Forbidden");
                        }
                    } else {
                        pw.println("HTTP/1.0 404 Not Found");
                        pw.flush();
                        System.out.println("HTTP/1.0 404 Not Found");
                    }
                } else if (line.toUpperCase().startsWith("HEAD")) {
                    String aux = line;
                    while (!(aux = br.readLine()).equals("")) {
                        head.append('\n' + aux);
                        final String contentHeader = "Content-Length: ";
                        if (aux.startsWith(contentHeader)) {
                            contentLength = Integer.parseInt(line.substring(contentHeader.length()));
                        }

                    }
                    System.out.println(head);
                    pw.println("HTTP/1.0 200 Ok");
                    System.out.println("HTTP/1.0 200 OK");
                    pw.flush();
                } else if (line.indexOf("?") == -1) {
                    getArch(line);//obtiene el archivo a enviar
                    if (FileName.compareTo("") == 0) {
                        SendA("index.htm");
                    } else {
                        SendA(FileName);//envia el arhivo solicitado
                    }
                    System.out.println(FileName);
                } else if (line.toUpperCase().startsWith("GET")) {
                    String aux = line;
                    while (!(aux = br.readLine()).equals("")) {
                        head.append('\n' + aux);
                        final String contentHeader = "Content-Length: ";
                        if (aux.startsWith(contentHeader)) {
                            contentLength = Integer.parseInt(line.substring(contentHeader.length()));
                        }

                    }
                    System.out.println(head);

                    StringTokenizer tokens = new StringTokenizer(line, "?");
                    String req_a = tokens.nextToken();
                    String req = tokens.nextToken();
                    System.out.println("Petición:" + req_a);
                    System.out.println("Parametros:" + req);
                    int indigual = req.indexOf("=") + 1;
                    String nom = "";
                    for (int i = indigual; i < req.length(); i++) {
                        if (req.charAt(i) == ' ') {
                            break;
                        }
                        nom += req.charAt(i);
                    }
                    System.out.println("Nombre" + nom);
                    System.out.println("Nombre del archivo:" + nom);
                    File ruta = new File("Archivos/" + nom);
                    if (ruta.exists()) {
                        pw.println("HTTP/1.0 200 OK");
                        pw.flush();
                        pw.println();
                        pw.flush();
                        pw.print("<html><head><title>SERVIDOR WEB");
                        pw.flush();
                        pw.print("</title></head><body><center>");
                        pw.flush();
                        if (nom.contains(".jpg") || (nom.contains(".png")) || (nom.contains(".jpeg")) || (nom.contains(".gif"))) {
                            pw.print("<img src=\"Archivos/" + nom + "\">");
                            pw.flush();
                        } else {
                            if (nom.contains(".pdf") || nom.contains(".txt")) {
                                pw.print("<embed src=\"Archivos/" + nom + "\" width=\"600\" height=\"100%\">");
                                pw.flush();
                            } else {
                                if (nom.contains(".mp3")) {
                                    pw.print("<embed src=\"Archivos/" + nom + "\" width=\"50%\" height=\"50%\" type=\"audio/mpeg\">");
                                    pw.flush();
                                }
                            }
                        }
                        pw.print("</center></body></html>");
                        pw.flush();
                        System.out.println("HTTP/1.0 200 OK");
                    } else {
                        pw.println("HTTP/1.0 404 Not Found");
                        pw.flush();
                        System.out.println("HTTP/1.0 404 Not Found");
                    }
                } else {
                    pw.println("HTTP/1.0 501 Not Implemented");
                    pw.println();
                }
//					pw.flush();
//					bos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                c.deconectar();
            }
        }

        public void getArch(String line) {
            int i;
            int f;
            if (line.toUpperCase().startsWith("GET")) {
                i = line.indexOf("/");
                f = line.indexOf(" ", i);
                FileName = line.substring(i + 1, f);
            }
        }

        public void SendA(String fileName, Socket sc) {
            //System.out.println(fileName);
            int fSize = 0;
            byte[] buffer = new byte[4096];
            try {
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());

                //sendHeader();
                FileInputStream f = new FileInputStream(fileName);
                int x = 0;
                while ((x = f.read(buffer)) > 0) {
                    //		System.out.println(x);
                    out.write(buffer, 0, x);
                }
                out.flush();
                f.close();
            } catch (FileNotFoundException e) {
                //msg.printErr("Transaction::sendResponse():1", "El archivo no existe: " + fileName);
            } catch (IOException e) {
                //			System.out.println(e.getMessage());
                //msg.printErr("Transaction::sendResponse():2", "Error en la lectura del archivo: " + fileName);
            }

        }

        public void SendA(String arg) {
            String extension = arg.substring(arg.length() - 3, arg.length());
            try {
                int b_leidos = 0;
                BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(arg));
                byte[] buf = new byte[1024];
                int tam_bloque = 0;
                if (bis2.available() >= 1024) {
                    tam_bloque = 1024;
                } else {
                    bis2.available();
                }

                String tipo = extension.equals("jpg") ? "image/jpeg \n" : extension.equals("pdf") ? "application/pdf \n" : extension.equals("png") ? "image/png \n" : extension.equals("gif") ? "image/gif \n" : extension.equals("mp3") ? "audio/mpeg \n" : extension.equals("txt") ? "text/plain \n" : "text/html \n";
                int tam_archivo = bis2.available();
                /**
                 * ********************************************
                 */
                String sb = "";
                sb = sb + "HTTP/1.0 200 ok\n";
                sb = sb + "Server: Rogelio Server/1.0 \n";
                sb = sb + "Date: " + new Date() + " \n";
                sb = sb + "Content-Type: " + tipo;
                sb = sb + "Content-Length: " + tam_archivo + " \n";
                sb = sb + "\n";
                bos.write(sb.getBytes());
                bos.flush();

                /**
                 * ********************************************
                 */
                while ((b_leidos = bis2.read(buf, 0, buf.length)) != -1) {
                    bos.write(buf, 0, b_leidos);

                }
                bos.flush();
                bis2.close();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public ServidorWeb() throws Exception {
        Cliente c = new Cliente();
        System.out.println("Iniciando Servidor.......");
        this.ss = new ServerSocket(PUERTO);
        System.out.println("Servidor iniciado:---OK");
        System.out.println("Esperando por Cliente....");
        for (;;) {
            c.getConexion();
            Socket accept = ss.accept();
            new Manejador(accept, c).start();
        }
    }

    public static void main(String[] args) throws Exception {
        ServidorWeb sWEB = new ServidorWeb();
    }

}
