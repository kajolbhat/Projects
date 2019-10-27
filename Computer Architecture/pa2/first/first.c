#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// This is a project that allowed us to learn how to use and manipulate bit code

unsigned short set (unsigned short x, int n, int v)
{
	//printf("set entered");
	if (v == 1)
	{
		unsigned short temp = 1;
		unsigned short shift = temp<<n;
		unsigned short ret = (x | shift);
		//printf("ret<<1= %d\n", ret<<1);
		return ret;
	}
	
	if (v == 0)
	{
		unsigned short temp = 1;
		unsigned short shift = temp<<n;
		unsigned short ret = (x & (~shift));
		//printf("ret<<1= %d\n", ret<<1);
		return ret;
	}
	return(0);
	
	  
}

unsigned short get (unsigned short n, int x)

{
		unsigned short temp = n;
		unsigned short shift = temp>>x;
		unsigned short ret = (shift & 1);
		//printf("ret<<1= %d\n", ret<<1);
		return ret;
}

unsigned short comp (unsigned short x, int n)

{
	unsigned short gotten = get(x,n);
	if (gotten ==0)
	{
		
		unsigned short retVal = set(x, n, 1);
		return retVal;
	}
	if (gotten ==1)
	{
		unsigned short retVal = set(x,n,0);
		return retVal;
	}

	return(-1);

}


int readFromFile(char* filename)
{
	//printf("read from file");
	unsigned short num = 0;

	char operator [8];
	int x=0;
	int v=0;
	//int n =0;
	
	
	FILE *inputfile = fopen(filename, "r");
	
	int ret = fscanf(inputfile, "%hd", (unsigned short *)&num);
	while (!(feof(inputfile)))
	{
		ret = fscanf(inputfile, "%s %d %d", operator, &x, &v);
		if (ret == EOF)
		{
			break;
		}
		//printf("operator = %s\n, x = %d\n, v= %d\n, num=%d\n", operator, x,v, num); 
		if (operator[0] == 's')
		{
			unsigned short output;
			//printf("BEFORE printing bits set");
			//printBits(2, &num);
			output = set (num,x,v);
			num = output;		
			printf("%d\n", num);
			//printf("printing bits after set");
			//printBits(2, &output);
		}

		if (operator[0] == 'g')
		{
			unsigned short output;
			//printf("BEFORE printing bits get");
			//printBits(2, &num);
			output = get (num,x);
			printf("%d\n", output);
			//printf("printing bits after get");
			//printBits(2, &output);
		}
		if (operator[0] == 'c')
		{
			//printf("BEFORE printing bits COMP");
			//printBits(2, &num);
			unsigned short output;
			output = comp(num,x);
			num = output;
			printf("%d\n", num);
			//printf("AFTER printing bits COMP\n");
			//printBits(2, &output);
			
		}

		
	}
	//printBits(2, &num);
	if (ret ==0)
	{	fclose(inputfile);
		exit(0);
	}

	
	if (inputfile == NULL)
	{
		printf("error");
		fclose(inputfile);
		return(-1);
	}
	//printf("inside");
	//printf("operator[0] = %c\n", operator[0]);
	/*
	if (operator[0] == 'c')
	{
		printf("comp read");
		//call comp
	}

*/	fclose(inputfile);
	return(0);

}
	
	
int main (int argc, char** argv)
{
	//unsigned short x=0;
	//int n=0;
	//unsigned short v=0;
	//FILE *inputfile = NULL;
	char filename[1024];
	strncpy (filename, (char*) argv[1], 1023);
	int retInt = readFromFile(filename);
	if (retInt ==0)
	{
		exit(0);
	}
	//unsigned short retVal1 = set(x,n,v);
	//printf("return : %c", retVal1);
	//
	exit(0);


}
