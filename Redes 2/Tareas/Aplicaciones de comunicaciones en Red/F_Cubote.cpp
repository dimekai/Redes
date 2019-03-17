#include <iostream>
using namespace std;

long long int n;
long long int a,b,c,d,e,f; /*Caras del cubo*/


int main(){
	
	ios::sync_with_stdio(0); 
	cin.tie(0);
	
	cin >> n;
	cin >> a >> b >> c >> d >> e >> f;

	long long int mayor = -1; /*Verifica que cara tiene el mayor valor*/

	mayor = max	(mayor,a);
	mayor = max	(mayor,b);
	mayor = max	(mayor,c);
	mayor = max	(mayor,d);
	mayor = max	(mayor,e);
	mayor = max	(mayor,f);

	if (n==1)
	{
		cout << ( a+b+c+d+e+f - mayor) << "\n"; 
	}else{

		long long int vertices = 4;
		long long int aristas = 4*(n-1) + 4*(n-2);
		long long int caras = 5*n*n - 2*aristas - 3*vertices;

		long long int wCaraMin = 100; /*El valor de cada cara va de [1,50]*/
		wCaraMin = min (wCaraMin,a);
		wCaraMin = min (wCaraMin,b);		
		wCaraMin = min (wCaraMin,c);
		wCaraMin = min (wCaraMin,d);
		wCaraMin = min (wCaraMin,e);
		wCaraMin = min (wCaraMin,f);


		long long int wAristaMin = 150;
		wAristaMin = min (wAristaMin, a + b );
		wAristaMin = min (wAristaMin, a + c );
		wAristaMin = min (wAristaMin, a + d );
		wAristaMin = min (wAristaMin, a + e );
		wAristaMin = min (wAristaMin, b + c );
		wAristaMin = min (wAristaMin, b + d );
		wAristaMin = min (wAristaMin, b + f );
		wAristaMin = min (wAristaMin, c + e );
		wAristaMin = min (wAristaMin, d + e );
		wAristaMin = min (wAristaMin, d + f );
		wAristaMin = min (wAristaMin, e + f );

		long long int wVerticeMin = 200;
		wVerticeMin = min (wVerticeMin,  a + b + c );
		wVerticeMin = min (wVerticeMin,  a + b + d );
		wVerticeMin = min (wVerticeMin,  a + c + e );
		wVerticeMin = min (wVerticeMin,  a + d + e );
		wVerticeMin = min (wVerticeMin,  f + b + c );
		wVerticeMin = min (wVerticeMin,  f + b + d );
		wVerticeMin = min (wVerticeMin,  f + c + e );
		wVerticeMin = min (wVerticeMin,  f + d + e );

		long long int res;
	 	res = caras * wCaraMin + (vertices*wVerticeMin) + (aristas*wAristaMin);
	 	cout << res << "\n";
	}
	return 0;
}