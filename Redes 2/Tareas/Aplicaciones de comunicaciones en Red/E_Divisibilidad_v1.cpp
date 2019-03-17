#include <iostream>
#include <math.h>
using namespace std;

long int N,M;
int main(){
	ios::sync_with_stdio(0); 
	cin.tie(0);
	cin >> N >> M;
	for (int i = (N-1); i>=0; i--)
		cout << fmod(pow(10,i),M) << " ";
	
	cout << "\n";
	return 0;
}