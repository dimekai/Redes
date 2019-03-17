#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <unistd.h>//read
#include <stdlib.h>//exit
#include <netdb.h> //getaddrinfo() getnameinfo() freeaddrinfo()
#define pto "9999"

int randnum;

void error(const char * msj){
 perror(msj);
 exit (1);
}//error

// get sockaddr, IPv4 or IPv6:
void *get_in_addr(struct sockaddr *sa)
{
    if (sa->sa_family == AF_INET) {
        return &(((struct sockaddr_in*)sa)->sin_addr);
    }

    return &(((struct sockaddr_in6*)sa)->sin6_addr);
}

int random_num()
{
    randnum = rand()%4;
    return randnum;
}

int main(){

 int sd,cd,n,n1,v=1,rv,op=0;
 socklen_t ctam;
 char s[INET6_ADDRSTRLEN], hbuf[NI_MAXHOST], sbuf[NI_MAXSERV];
 //struct sockaddr_in sdir,cdir;
 struct addrinfo hints, *servinfo, *p;
 struct sockaddr_storage their_addr; // connector's address 
 ctam= sizeof(their_addr);
 memset(&hints, 0, sizeof (hints));  //indicio
 hints.ai_family = AF_INET6;    /* Allow IPv4 or IPv6  familia de dir*/
 hints.ai_socktype = SOCK_STREAM;
 hints.ai_flags = AI_PASSIVE; // use my IP
 hints.ai_protocol = 0;          /* Any protocol */
 hints.ai_canonname = NULL;
 hints.ai_addr = NULL;
 hints.ai_next = NULL;

    if ((rv = getaddrinfo(NULL, pto, &hints, &servinfo)) != 0) {
        fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(rv));
        return 1;
    }//if

    for(p = servinfo; p != NULL; p = p->ai_next) {
        if ((sd = socket(p->ai_family, p->ai_socktype,p->ai_protocol)) == -1) {
            perror("server: socket");
            continue;
        }

        if (setsockopt(sd, SOL_SOCKET, SO_REUSEADDR, &v,sizeof(int)) == -1) {
            perror("setsockopt");
            exit(1);
        }

	if (setsockopt(sd, IPPROTO_IPV6, IPV6_V6ONLY, (void *)&op, sizeof(op)) == -1) {
            perror("setsockopt   no soporta IPv6");
            exit(1);
        }

        if (bind(sd, p->ai_addr, p->ai_addrlen) == -1) {
            close(sd);
            perror("server: bind");
            continue;
        }

        break;
    }

    freeaddrinfo(servinfo); // all done with this structure

    if (p == NULL)  {
        fprintf(stderr, "servidor: error en bind\n");
        exit(1);
    }

   listen(sd,5);
   printf("Servidor listo.. Esperando clientes \n");
  
  for(;;){
  
    ctam = sizeof their_addr;
    cd = accept(sd, (struct sockaddr *)&their_addr, &ctam);
    if (cd == -1) {
        perror("accept");
        continue;
    }
    if (getnameinfo((struct sockaddr *)&their_addr, sizeof(their_addr), hbuf, sizeof(hbuf), sbuf,sizeof(sbuf), NI_NUMERICHOST | NI_NUMERICSERV) == 0)
    printf("cliente conectado desde %s:%s\n", hbuf,sbuf);

    FILE *f = fdopen(cd,"w+");
    char buf[1024];

    /*arreglos para tipos de dificultades*/
    /*novato*/
    const char *a[4];
    a[0] = "dos";
    a[1] = "oro";
    a[2] = "uno";
    a[3] = "tecla";        
    /*novato*/
    const char *b[4];
    b[0] = "caballero";
    b[1] = "damicela";
    b[2] = "botella";
    b[3] = "pantalon";
    /*novato*/
    const char *c[4];
    c[0] = "otorrinolaringologo";
    c[1] = "electrocardiograma";
    c[2] = "paralelepipedo";
    c[3] = "caleidoscopio";
        for(;;){
            bzero(buf,sizeof(buf));
            n=read(cd,buf,sizeof(buf));
            //validando errores
            if(n<0){
                perror("Error de lectura\n");
                close(cd);
                break;
            }
            else if(n==0){
                perror("Socket cerrado\n");
                break;
            }
            printf("recibido:  %s  longitud:%d \n",buf,(int)strlen(buf));
            //char *tmp = (char *) malloc(strlen(buf));
            if(strstr(buf, "SALIR")!=NULL){
                printf("escribio SALIR\n");
                fclose(f);
                close(cd);
                break;
            }
            else if(strstr(buf, "1")!=NULL){
                printf("Novato\n");
                int n = random_num();
                send(cd,a[n], strlen(a[n])+1,1);
                n1= write(cd,buf,n-1);
                fflush(f);
            }
            else if(strstr(buf, "2")!=NULL){
                printf("Intermedio\n");
                int n = rand()%4;
                send(cd,b[n], strlen(b[n])+1,1);
                n1= write(cd,buf,n-1);
                fflush(f);
            }
            else if(strstr(buf, "3")!=NULL){
                printf("Leyenda\n");
                int n = rand()%4;
                send(cd,c[n], strlen(c[n])+1,1);
                n1= write(cd,buf,n-1);
                fflush(f);
            }            
            else{
                
            }
        }//for
   }//for
  close(sd);
return 0;
}//main