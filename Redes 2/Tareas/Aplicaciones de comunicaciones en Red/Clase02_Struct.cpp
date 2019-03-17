Struct:
	Cuando queremos utilizar cosas relacionadas.

struct nombre_struct{
	tipoDeDato nomb_var;
}

struct punto{
	int x;
	int y;
};

nombre_struct variable;
punto origen;

origen.x=0;
origen.y=0;

punto estad[102];
estad[27].x=12;

/*------------------------------------------*/
struct amigo{
	string nombre;
	short edad;
	string correo;
	float dineroDebo;
};

amigo Erick;
Erick.nombre="Erick";
Erick.edad = 18;
Erick.correo = "erick1@gmail.com";
Erick.dineroDebo = 352.50;
/*----------------------------------------*/
struct amigo{
	string nombre;
	short edad;
	string correo;
	float dineroDebo;
	short calif[502];
};

amigo ESCOM[3000];
ESCOM[0].calif[5]=7;

/*--------------------------*/
struct amigo{
	string nombre;
	short edad;
	string correo;
	float dineroDebo;
	short calif[502];
	punto fav[1000];
};

amigo ESCOM[3000];
ESCOM[50].fav[741].y=8; 

También podemos tomar como una cuestión bidimensional.

struct amigo{
	string nombre;
	short edad;
	long amigos;
	string correo;
	float dineroDebo;
	short calif[502];
	punto fav[1000];
};

Puedo tener 3000 amigos, y mi amigo tiene 3000 amigos.
amigo AmigosDeAmigos[3000][3000];
amigo ESCOM[3000];


float total;
int posErick;
while(ESCOM[posErick].nombre!="Erick")
	posErick++;

for (int i = 0; i < ESCOM[posErick].amigos; i++)
	total += AmigosDeAmigos[posErick][i].dineroDebo;

cout << "Le deben: " << total;
