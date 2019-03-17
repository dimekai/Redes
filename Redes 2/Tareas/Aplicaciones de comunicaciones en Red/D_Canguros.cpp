#include <queue>
#include <iostream>
using namespace std;
int main()
{
	ios::sync_with_stdio(0); 
	cin.tie(0);

	int C, N,Mi;
	cin >> C >> N;
	char accion; 
	queue<int> cG;	
	while(N--){
		cin >> accion;
		switch(accion){
			case 'N':
				cin >> Mi; 
				cG.push(Mi+1);
				break;
			case 'A':
				C -= cG.front();
				cG.pop();
				break;
			case 'C':
				cout << cG.size()<<'\n';
				break;
			case 'R':
				cout<<C<<"\n";
				break;
		}
	}
	return 0;
}