package uab_java;
import java.util.*;

//import uab_java.C4.Node;


class C4 {
	int N=8;
	int MAX_LEVEL=2;
	boolean gameOver = false;
	char pt = 'H';
	char ct = 'C';
	
	class Node {
		char board[][]=new char[N][N];
	    double value;
	    //int[] validChildren1;
	    ArrayList<Integer> validChildren = new ArrayList<Integer>();
	    Node children[]=new Node[N];	//equivalent to Node * in C
	    int n_children; //number of children
	    int parentThrowColumn;  // this will hold the column which was thrown on the parent board to yield the child board
	    int dummy = 99;
	}
	
	Node root;
	
	void changeDummy(int num, Node node) {
		node.dummy = num;
	}
	
	void pl(int n) {
		for (int i=0; i<n; i++) {
			System.out.println();
		}
	}
	
	void psl(String txt) {
		System.out.println(" \n "+txt+" \n ");
	}
	void np(String name) {
		
		pl(1);
		ps(" this is: " +name);
	}
	void printarray (int x, int y, double[][]arr, String name){
		
		System.out.println("this is: "+ name);
		
		for (int i=0; i<x; i++) {
			
			
			for (int j=0; j<y; j++) {
				
				System.out.print(" "+arr[i][j]+" ");
			}
			pl(1);
		}pl(2);
	}//end of printarray
	
	void printBoard2(Node root) {
		for (int k=0; k<8; k++) {
			 pl(1);
			for (int l=0; l<8; l++) {
				
				if(root.board[k][l]==' ') {
					System.out.print(" _ ");
				}else {
				System.out.print(root.board[k][l]);
				}
			}
		}
	}
	
	void print3dArray(int x, int y, int z, double [][][]arr, String name) {
		
		np(name);
		
		for (int a=0; a<x; a++) {
			pl(1);
			for (int b=0; b<y; b++) {
					pl(1);
					
					for (int c=0; c<z; c++) {
						
						System.out.print(" "+arr[a][b][c]+" ");
					}
				}
		}pl(1);
		
	}
	
	void setBoardFieldsToSpaces(Node node) {
		for (int i=0; i<8; i++) {
			 for (int j=0; j<8; j++) {
				 node.board[i][j]= ' '; 
			 }
		 }
	}
	
	void copyBoard(char board1_rec[][],char board2_src[][]) {
		
		for(int i=0;i<8;i++){    
			
				for(int j=0;j<8;j++){
					board1_rec[i][j] = board2_src[i][j];
				}
			}
	}
	int calculatesColumn(char board[][],int numChild) {  
		//numChild = 0;
		
		return numChild;
		
	
	} //2 possibilities=> 0,1->3,5
	
	void applyThrow(char board[][],int column) {
		
		for (int i=7; i>=0; i--) { //counts downwards ! assumption is that [0][column] is highest field
			
			
			if (board[i][column]== ' ') { //which symbol is used for empty ?
				board[i][column] = ct ; break; //which symbol is used for filled?
				}
		}
		
	}	//calculates the row and set the piece
	
	
	int setNumberOfChildren(Node node) { 
		//return amount N of rows which are empty
		int n = 0;
		for(int i=0;i<8;i++){ 
			if ( node.board[0][i]== ' '  ) { // is 0 highest row??
				n++;
			}
		}
	
		return n;}	//only need to see the top row 
	//bbtree  bbcreate
	void createTree(Node root) {   //creates a 2 level tree, root.board has to be created already.
	    
		setNodeValues(root, 0); //will set validChildren, n_children, value, but not the board
	    
	    
	    createChildren(root,1);   //creates the 1st level of the tree
	    for(int i=0;i<root.n_children;i++) {   //for creating the 2nd level of the tree
	        createChildren(root.children[i],2);
	    }
	}
	
	void createChildren(Node parent,int level) { //assume that parent has already created the array of children and calculated the number of children
		    
		    setValidChildren(parent); //validChildren is set, maybe redundant
		    
		    int len = parent.validChildren.size();
		   // System.out.println ("len is: " + len  );
		   // System.out.println ("validChildren is: " + parent.validChildren  );
		    
		    for (int i=0;i<len;i++) {
		    	
		    	int childThrowColumn = parent.validChildren.get(i);
		        parent.children[i]=createNode(parent, childThrowColumn, level);
		        //children is a Node array, declared with constructor function
		        // does not make sense, root doesn't have parentThrowColumn
		        
		        
		        
		    }
		}

	Node createNode(Node parent,int childThrowColumn,int level) {
		//level is given by parent function
	    Node p=new Node();		//equivalent to  Node *p=malloc(sizeof(Node));
	    //int column;
	    
	    copyBoard(p.board,parent.board);  //copies the parent board into the 'p' board
	    //column=calculatesColumn(parent.board, parentThrowColumn); //what is numCHild?
	    applyThrow(p.board, childThrowColumn);    //calculate the row by gravity and set the piece pn the p's board
	    // applyThrow works with 0-7
	    
	    
		 p.parentThrowColumn = childThrowColumn;
		 p.value = getBoardValue(p);
	    
		 //different instructions depending on whether we're at MAXLEVEL or not
	    
	    if (level==MAX_LEVEL) {
	        p.n_children=0;
	        p.validChildren = new ArrayList <Integer>(); //constructor function
	        
	        // 
	    }
	    else {
	        p.n_children=setNumberOfChildren(p);
	        setValidChildren(p);
	        
	    }
	    //printBoard2(p);
	    return p;
	}
	
	
	
	
	void setNodeValues(Node node, int lvl) {
			
			//assumption would be, that the board is already set
			if (lvl == 0) {
				
				node.n_children=setNumberOfChildren(node);
				 setValidChildren(node);
				 node.value = getBoardValue(node);
				
				
				 // calculate value
				 
			 
			}else { //for levels 1 and 2
				//init board based on parent
				
			}
			node.value = getBoardValue(node);
			 setValidChildren(node);  //root.validChildren is now set
			 node.n_children = node.validChildren.size(); //n.children is not set
			 
		    
		}
	///////////////////////////////////////////
	//function that creates array of valid children
	void setValidChildren(Node node){
		
		node.validChildren.clear(); //empty array, otherwise length will go over 8
		
		for (int i=0;i<8;i++) {
			if (node.board[0][i]==' ') {
				
				
				node.validChildren.add(i); //properly gets 7 children
			}
		}
		// ==> validChildren geht von 0 bis 7 
	}
	
	
	
	
	void displayNode(Node node,int level) {
	    for(int i=0;i<level;i++)
	        System.out.print("  ");
	    System.out.printf("%f\n",node.value);
	}
	
	//Displays a tree recursively
	void displayLevel(Node parent,int level) {
	    for(int i=0;i<parent.n_children;i++) {
	        displayNode(parent.children[i],level);
	        
	        displayLevel(parent.children[i],level+1);
	    }
	}

	//function that displays a Tree
	void displayTree(Node root) {
	    displayLevel(root,1);
	}
	
	int askcolumnToUser() {
		Scanner sc = new Scanner(System.in);
	    int column= sc.nextInt();
	    return column;
	}
	
	/////////////////////
	
	
		
	////////////////////my stuff
	
	C4() {	//main program should be here   bbc4
		/// GAME START
		this.root = new Node();
		//Node root = new Node();
		//before calculating, initialise the board to all cells empty
		
		//System.out.println(root.dummy);
		//changeDummy(101, root);
		//System.out.println(root.dummy);
		
		setBoardFieldsToSpaces(root);
		 root.n_children=setNumberOfChildren(root);
		 setValidChildren(root);
		 root.value = getBoardValue(root);
		 
		//c4001.print_board(root.board);
		//System.out.println();
		
		createTree(root);	//before calling this function you must create the root Node and set the number of children
		//displayTree(root);
		
		gameLoop(this.root);
		
		//ps("\n this is root.board after gameloop");
		//printBoard2(root);
		
		/*
		char test2 [] = {'r','y','y',' ',' ','r','r','r'};
		char board_t2 [][] = {
						
						{'r','r',' ',' ','y','r','y','r'},
						{'y','y',' ',' ',' ',' ',' ',' '},
						{' ',' ',' ',' ',' ','r','y','r'},
						{'r',' ',' ',' ','y',' ',' ',' '},
						
						{' ',' ',' ',' ',' ',' ','y',' '},
						{'r','r',' ','r','y','r',' ',' '},
						{' ',' ',' ',' ',' ',' ',' ','r'},
						{'y','r',' ','r','y','r',' ','y'}
						
				};	
		
		Node test15 = new Node();
		test15.board = board_t2;
		int[] test = checkRows(test15);
		ps("this is test-array: ");
for (int i=0; i<4; i++) {
			
			System.out.print(test[i] );
		}
pl(1);
		double test16 = getBoardValue (test15);
		
		System.out.print("test16 is:" + test16);
		*/
		//System.out.println("n_children is: " +   root.n_children    );
		
		/*
		double[][] f=get_sls(root);
		
		
		for(int i1=0;i1<8;i1++){    
			System.out.println();
			for(int j1=0;j1<8;j1++){  
				System.out.print(" "+f[i1][j1]+" ");
			}
		}
		
		*/
		
		/*
		double[][]sls_test = new double[8][8];
		
		for (int i1=0; i1<8; i1++) {
			
			for (int j1=0; j1<8; j1++) {
				
				int randomNum = (int)(Math.random() * 8);  // 0 to 100
				
				
				
				
			}
		}
		*/
		/*
		for(int i2=0;i2<8;i2++){    
			System.out.println();
			for(int j2=0;j2<8;j2++){  
				System.out.print(" "+ sls_test[i2][j2]+" ");
			}
		}
		
		pl(4);
		
		double[][] smalOpt = smallestOptions_from_sls(sls_test);
		
		for (int i1=0; i1<8; i1++) {
			
			pl(1);
			for (int j1=0; j1<2; j1++) {
				
				double x= smalOpt[j1][i1];
				System.out.println(x);
			}
		}
		
		int ct = getComputerThrowFromSmallestOptions(smalOpt);
		System.out.println("here comes ct: " + ct);
		*/
		
	}
///////////////////bbgameloop
//  G A M E L O O P
///////////////////
	
	void gameLoop(Node node) {
		
		
		boolean gameOver = false;
		boolean humTurn = true;
		boolean isDraw = false;
		boolean isWin = false;
		
		while (gameOver == false) {
			
			
			//checker functions
			createTree(node);
			//displayTree(node);
			
			
			if (isWin(node.board)==true){
				ps("4 fields have been connected. there is a winner");
				gameOver = true; break;
				
			}
			
			if (isFull(node.board)== true) {
				ps("Board is full. The game is a draw");
				gameOver = true; break;
				
			}

			//human turn or computer turn 
			
			if(humTurn == true) {
				
				
				
				humanTurn(node);
				humTurn = false;
				
			}else {
				
				compTurn(node);
				humTurn = true;
				
			}
			
			
		}// end of while loop
		
		
	}
	
	void checkerFunction(char[][] board) {
		
		
	}
	
	
	///////////////////
	//  H E U R I S T I C
	///////////////////
	
	void ps(String arr) {
		 
		 System.out.println(arr); 
	}
	void pi(int num, String name) {
		 
		System.out.print("this is " + name + ": ");
		 System.out.println(num); 
	}
		
	
	//bbheuristic
	
	double[][] get_sls(Node root){
		
		// for this, the .value attribute has to be initialised already in all grandchildren 
		//. parentThrow as well
		
		double[][] sls = new double [8][8];
		
		//initialise all items to -1
	   for (int i=0 ; i<8 ; i++ ) {   //cycle through 1st level children
				for (int j=0 ; j<8 ; j++ ) {   //cycle through 1st level children
					sls[i][j] = -1; 
					}}
		
		
		//space-metaphorical array
	   
	   for (int i=0 ; i<root.validChildren.size() ; i++ ) {   //cycle through 1st level children
			
		   Node child = root.children[i];	
		   int x = child.parentThrowColumn;
			
			for (int j=0 ; j<root.children[i].validChildren.size() ; j++ ) {   //cycle through 1st level children
				
				Node grandchild = root.children[i].children[j];
				
				int y = grandchild.parentThrowColumn;
						
				double score = (double)root.children[ i ].children[ j ].value;
				
				/* alternative
				double scoreAlt = getBoardValue (root.children[ i ].children[ j ] );
				*/
				sls[x][y] = score; 
				
			}//end of for loop j
	   } //end of for loop i
		return sls;
	}
	
	
	
	
	double[][][] sls_rows_from_sls(double[][]sls) {
		

		double[][][] sls_rows = new double[8][2][8];
		double[][]sls_row = new double[2][8];
		
		for (int i = 0; i<8; i++) { //cycle through sls rows 
			for (int j=0; j<8; j++) {//cycle through columns of sls row
				
				sls_row[0][j] = sls[i][j];
				sls_row[1][j] = j;
				
				
			}
			
			//big error by:  sls_rows (i) = sls_row;
			//
			for (int k=0; k<2; k++) {
				for (int h=0; h<8; h++) {
					
					
					sls_rows[i][k][h] = sls_row[k][h];
					
				}
						
			}
			
			
		}
		print3dArray(8,2,8, sls_rows, "sls_rows");
		return sls_rows;
	}// end of sls_rows_from_sls
	
	double[][]smallestOptionsFrom_slsRows(double[][][]sls_rows){
		//print3dArray(8,2,8, sls_rows, "sls_rows");   arrive identically
		double[][]smallestOptions = new double[2][8];
		
		for (int i=0; i<8; i++) {
			
			double[][]sls_row = sls_rows[i];
			
			double[][] pair = findSmallestPair(sls_row);
			
			smallestOptions[0][i]= pair[0][0];
			smallestOptions[1][i]= pair[1][0];
			
		}
		
		
		return smallestOptions ;
	}
	
	
	double[][]findSmallestPair(double[][]a82){
		
		double[][] smallest = new double[2][1];
		
		smallest[0][0] = -1;
		smallest[1][0] = -1;
		
		///initialise with one item > 0, otherwise if first item = -1, it will be returned
		
		for (int l=0; l<8; l++ ) { //cycle through sls_row's items
			
			if (a82[0][l]>0) {  //only init. smallest if item >0, --> not -1 null value
				
				smallest[0][0] = a82[0][l];
				smallest[1][0] = a82[1][l]; // könnte man auch l hinschreiben, kommt aufs gleiche raus
				break;
				
			}				}
		
		for (int k=0 ; k<8 ; k++ ) {  //cycle through fields in a82 to det. smallest
			
			// jump for non-valid board with -1
			
			if (a82[0][k]<0) {     
				continue;
			}
			
			if (a82[0][k] < smallest[0][0]) {
				
				smallest[0][0] = a82[0][k];
				smallest[1][0] = a82[1][k];
				
			}
		
	}
		//printarray(2,1, smallest, "smallest ");
		return smallest;
		
	}// end of findSmallestpair 

	
	int getComputerThrowFromSmallestOptions(double[][]smallestOptions ){
		
		int computer_throw =0;
		
		double[][] sOp = smallestOptions;
		
		double[][] biggest = {{-1},{-1}};
		
		//biggest
				
		for (int i=0; i<8; i++) { //cycle through top row of smallestOptions to find one that is not -1
			
			if (sOp[0][i]>0) {  //only init. biggest if item >0, --> not -1 null value
				
				biggest[0][0] = sOp[0][i];
				biggest[1][0] = i; // not sOp[1][i] like in the precipitating function
				break;
				//sOp has the childnode's parentThrow saved in position, not the second row
			}
			
		} //end of i-loop
		//once you've got an item that is not -1
		
		
		// jump for non-valid board with -1
		
		for (int k=0 ; k<8 ; k++ ) {  //cycle through fields in sOp to det. biggest
			
			
			
			if (sOp[0][k]<0) {    // jump for non-valid board with -1 
				continue;
			}
			
			if (sOp[0][k] > biggest[0][0]) {
				biggest[0][0] = sOp[0][k];
				biggest[1][0] = k; // not sOp[1][i] like in the precipitating function
				
			}
		}//end of k-loop
		
		//choose randomly among turns of equal value
		
		
		
		double[]equalthrows = {-1,-1,-1,-1,-1,-1,-1,-1}; //  
		double val = biggest[0][0];          //
		
		
		// if all of s0p are -1, val will also be -1   bbdraw
		
		if (val == -1) {
			for (int t=0; t<8; t++) {
				
				if (root.board[0][t]==' ') {
					computer_throw = t;
					return computer_throw;
				}
			}
		}
		
		for (int i=0; i<8; i++) {
					
					if (sOp[0][i] == val) {   //check all fields, for duplicates of best option
					
						equalthrows[i]= sOp[1][i];  //read hits into equalthrows
					}
				}
		//choose randomly among items of equalthrows
		
		
		int randomNum = (int)(Math.random() * 7);  // 0 to 7
		
		while(equalthrows[randomNum] == -1) {
			randomNum = (int)(Math.random() * 7);  // 0 to 100
		}
		
		computer_throw = (int) equalthrows[randomNum];
		//computer_throw = (int) biggest[1][0];
		return computer_throw;
	}
	
	
	double[][] smallestOptions_from_sls (double[][] second_level_scores) {
		//what will program produce?
		// is parentThrow 0-7 or 1-8 ?
		
		//double[][] fls= {{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1}}
		
		double[][]sls = second_level_scores;
		
		//human chooses low values, so choose lowest value of each array row 
		//non-assigned fields in sls will have value 99
		
		double[][]sls_row = new double[2][8];
		
		double[][] smallestOptions = new double[2][8];
		double[][] smallest = new double[2][1]; //holds smallest value and its position
		
		for (int i=0; i<8; i++) { //cycle through rows in sls
			
			for (int j=0 ; j<8 ; j++ ) {  //cycle through fields in row i of sls
			
				sls_row [0][j] = sls[i][j];  // first row:  boardScores (aka node.value)
				sls_row [1][j] = j;          //second row: column (aka parentThrow)
				// could also  be j+1, so 1-8 is printed, instead of 0-7
				//get smallest .value from sls_row, return it and its position
				
				
				smallest[0][0] = -1;
				smallest[1][0] = -1;
				
				for (int l=0; l<8; l++ ) { //cycle through sls_row's items
					
					if (sls_row[0][l]>0) {  //only init. smallest if item >0, --> not -1 null value
						
						smallest[0][0] = sls_row[0][l];
						smallest[1][0] = sls_row[1][l];
						break;
						
					}				}
				/*
				smallest[0][0] = sls_row[0][0];
				smallest[1][0] = sls_row[1][0];
				*/
				for (int k=0 ; k<8 ; k++ ) {  //cycle through fields in sls_row to det. smallest
					
					// jump for non-valid board with -1
					
					if (sls_row[0][k]<0) {     
						continue;
					}
					
					if (sls_row[0][k] < smallest[0][0]) {
						
						smallest[0][0] = sls_row[0][k];
						smallest[1][0] = sls_row[1][k];
						
					}
				
					
				}//end of k-loop
				
				
				
			}// end of j-loop
			
			smallestOptions [0][i] = smallest[0][0];//transfer pair to fls array
			smallestOptions [1][i] = smallest[1][0];
			
		}// end of i-loop
		return smallestOptions;
		
	}// end of function get_fls_from_fls
	
	

	
	
	// I:  node   O:  double Boardvalue
	double getBoardValue(Node node) { //fgetboardvalue
		
		double boardValue = -1;
		
		int[] row_results = checkRows(node);
		
		int[] col_results = checkColumns(node.board);
		
		int[] sum_results = new int[4];
		
		for(int i=0;i<4;i++){
			sum_results[i]= col_results[i] + row_results[i];
		} 
		
		//8loop
		
		int hum_score =1+ sum_results[0]*2 + sum_results[1]*4;
		int pc_score = 1+sum_results[2]*2 + sum_results[3]*4;
		
		//pi(hum_score, "hum_score");
		//pi(pc_score, "pc_score");
		
		/*
		if (hum_score==0 || pc_score ==0) {
			boardValue = 1;
		}else {
			boardValue = (double)hum_score / (double)pc_score;
		}
		*/
		boardValue = (double)hum_score / (double)pc_score;
		
		if (boardValue != 1.0) {
			//System.out.println("boardValue is: " + boardValue);
		}
		
		/*
		System.out.println("humscore is" +hum_score );
		System.out.println("pcscore is" + pc_score );
		System.out.println("boardValue is" + boardValue );
		
		*/
		
		return boardValue;
		
	}
	
	
	int[] checkRows (Node node){
		
		int[] row_results = {0,0,0,0};
		
		for(int i=0;i<8;i++){  
			
			int[] row_result = checkRow (node.board[i]); //checkRow returns  result int[] of length 4
			
			for(int j=0;j<4;j++){  
				
				row_results[j]+= row_result[j];
				
			}
			
		}
		
		
		return row_results;
		
	}
	
	
int[] checkRow(char[] row ) {
		int[]result= {0,0,0,0};
		
		for(int i=0; i<7;i++) { //only checks the first 7 items, since last item can't be pattern
			
			//FIELD EMPTY
			if (row[i]== ' ') { //if field empty, skip to next
				continue;}
			
			//CHECK IF 7th ITEM
			if (i==6) {
				
				if (row[6]==row[7]) {
					switch(row[i]) {
					case 'H': result[0]+=1;break; //
					case 'C': result[2]+=1;break; //
					}
				}
				
				
			//instructions. for ITEMS 1-6
			}else { 
			
				if (row[i]==row[i+1]) {
					
					//System.out.println(row[i]);
					//System.out.println(row[i+1]);
					
					
					if (row[i]==row[i+2]) {
						
							switch(row[i]) {
							case 'H': result[1]+=1;break; //Triple found, yellow
							case 'C': result[3]+=1;break; //Triple found, red
							}
							
							i++; // this is so that when a triple is detected, the algorithm does not register the double inside the triple. it starts the algorithm at i+2.
						}else{
							switch(row[i]) {
							case 'H': result[0]+=1;break; //Double found, yellow
							case 'C': result[2]+=1;break; //Double found, red
							}
						}
					}
				
				
			}//end of 1-6/7 else loop
			
		}//end of for loop
		
		
		 //
		return result;
	
}//End of checkRow
char[][] transpose(char[][] original) {
		
	
			
			char[][] transposed = new char[8][8];
			
			for(int i=0;i<8;i++){    
				for(int j=0;j<8;j++){    
				transposed[i][j]=original[j][i];  
				}    
				} 
			return transposed;
	}
	
int[] checkColumns(char[][] board ) {
		
		//TRANSPOSITION
		char[][]b = board;
		
		char[][] transposed = new char[8][8];
		
		for(int i=0;i<8;i++){    
			for(int j=0;j<8;j++){    
			transposed[i][j]=b[j][i];  
			}    
			} 
		
		
		int[] columns_result = new int[4];
		
		for(int i=0;i<8;i++){    
			
			
			int[] col_result = checkRow(transposed[i]);
			
			//add single col result to all result
			
			for(int j=0;j<4;j++){
				columns_result[j]+= col_result[j];
				
			}  
			
			} 
		
		
		
		
		return columns_result;
	}
//////////////// bbboard
//   B O A R D
////////////////
	
	void humanTurn(Node node) {
		
		print_board(node.board);
		psl(" This is the current board");
		placeHumanThrow(node.board); //takes column choice from user and applies to board
		
		print_board(node.board);
		psl(" Your throw has been placed");
	}
	
	void compTurn(Node node) { //fcompturn
		//ps("this is the board compTurn is working with");
		//printBoard2(node);
		
		double[][] sls = get_sls(node);
		System.out.print("This is an 8x8 table showing all board value of the 64 children. ");
		printarray(8,8, sls, "sls");
		
		
		
		double[][][]sls_rows = sls_rows_from_sls(sls);
		//np("sls_rows"); printarray
		
		
		double [][] smallestOptions = smallestOptionsFrom_slsRows (sls_rows);
		System.out.println("This is a 2x8 array showing the smallest board value of each potential computer turn");
		printarray(2,8, smallestOptions, "smallestOptions");
		
		int compThrowColumn = getComputerThrowFromSmallestOptions (smallestOptions);
		
		
		ps("The computer's turn is: "); System.out.println(compThrowColumn+1);
		
		
		
		int ctc = compThrowColumn;
		
		//System.out.println("ctc:" + ctc);
		
		
		/*
		
		double [][] smallestOptions = smallestOptions_from_sls (sls);
		
		int compThrowColumn = getComputerThrowFromSmallestOptions (smallestOptions);
		
		int ctc = compThrowColumn;
		*/
		//throw on highest field in column
		
		for (int i=7; i>=0; i--) {
			
			//System.out.println("ctc is: " + ctc);
			if (node.board[i][ctc]== ' ') {
				node.board[i][ctc] = 'C'; break;
			}
			}//end of i loop
		
		
	}
	
	 void ps1(String arr) {
		 
		 System.out.println(arr);
	
}

	 char[][] init_board() {
		char[][] board = new char[8][8];  //will hold NULL at each field
		
		//FILL ARRAY WITH SPACE CHARACTER
		int i,j;
		 for (i=0; i<8; i++) {
			 for (j=0; j<8; j++) {
				 board[i][j]= ' '; 
			 }
		 }
		return board;
	}


 void print_board(char[][] board){ // PRINT BOARD
	//FIELDS
	int i=0; int j=0; 
	 for (i=0; i<8; i++) {
		 System.out.print("\n"); 
		 for (j=0; j<8; j++) {
			 char x= board[i][j];
			 System.out.print("| "+x+" |"); 
		 }
	 }
	 //COLUMN NUMBERS
	 System.out.print("\n");
	 for (i=0; i<8; i++) {
		 int x=i+1;
		 System.out.print("  "+x+"  "); 
		 
	 }
}
 boolean isFull(char[][] board) {
	
	for(int i=0; i<8; i++) {
		if( board[0][i] ==  ' ' ) {
			return false; 
		}
	}
	
	return true ;
}
 
 boolean checkRowforWin(char[][] board) {
	 
	 for (int i=0; i<8;i++) {
			
			for (int j=0; j<5;j++) {
				
				char start0 = board[i][j];
				char start1 = board[i][j+1];
				char start2 = board[i][j+2];
				char start3 = board[i][j+3];
				
				if (start0==start1) {
					if (start0==start2) {
						if (start0==start3) {
							if(start0 == ct) {
								ps("computer has won");
								return true;
							}
							
							if(start0==pt) {
								ps("player has won");
								System.out.println("4connect starts on coordinates "+(i+1)+","+(j));
								return true;
							}
							
							//==board[i][j+2]==board[i][j+3])
						}
						
						//==board[i][j+2]==board[i][j+3])
					}
					
					//==board[i][j+2]==board[i][j+3])
				}//end of if loops
			}
		}//end of for loop i 
	 return false;
 }
 
 boolean isWin(char[][] board) {
	 
	 char[][] transposed_board = transpose(board);
	//check rows
	 if(checkRowforWin(board)== true || checkRowforWin(transposed_board)== true) {
		 return true;
	 }

	
	return false ;
}


 void placeHumanThrow(char[][] the_board) {
	
	Scanner sc = new Scanner(System.in);
	
	/*int un= sc.nextInt(); //un = user_number*/
	
	char[][] board = the_board;
	boolean isEmpty = false; 
	boolean isColumn = false;
	//int arr8[] = {1,2,3,4,5,6,7,8};
	int un =99 ; //= 99; 
	while (isEmpty == false || isColumn == false )
	{
		
		ps("Please provide an integer between 1 and 8 to choose your column. \n");
		int lun = sc.nextInt(); //un = user_number
		un= lun;
		
		if ((lun >= 1) && (lun <= 8)) {  //(lun >= 1) && (lun <= 8)
			isColumn = true; 
		}else {
			continue;
		}
		
		if( board[0][lun-1] == ' ') {
			isEmpty = true;
		}else {
			continue;
		}
		//System.out.println("lun is:" + lun);
	} //end of while loop
	
	//DETERMINE HIGHEST FIELD AND PRINT
	
	//System.out.println(String(un));
	
	for (int i=7; i>=0; i--) {
		int un2 = un; 
		//System.out.println("un2 is: " + un2);
		if (board[i][un2-1]== ' ') {
			board[i][un2-1] = pt; break;
		}
		
		
	}//end of for loop i 
	
	//sc.close();  throws error
}
	////////////////bbtest
	//   T E S T
	////////////////
	
	
	
void test() {
		
		
	char board_t1 [][] = {
			
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{'r',' ',' ',' ',' ',' ',' ',' '},
			{'r',' ',' ',' ',' ',' ',' ',' '},
			{'r',' ',' ',' ',' ',' ',' ',' '}
			
	};
	/*
		int[] col_result = checkColumns(board_t1);
		
		System.out.println("yellow doubles:  " + col_result[0]);
		System.out.println("yellow triples:  " + col_result[1]);
		System.out.println("   red doubles:  " + col_result[2]);
		System.out.println("   red triples:  " + col_result[3]);
		System.out.println();	
		
		
		
		System.out.println("transp. boardt2");
		
		char[][] trans = transpose(board_t1);
		
		for(int i=0;i<8;i++){    
			System.out.println();
			for(int j=0;j<8;j++){    
				
			if (trans[i][j]== ' ') {
				System.out.print(    "-"           );
			}else {
				System.out.print(    trans[i][j]           );
			}    
			} }
		*/
		char test1 [] = {' ',' ','y','y','r','r','y','r'};
		char test2 [] = {'r','y','y',' ',' ','r','r','r'};
		char test3 [] = {' ',' ',' ',' ',' ',' ',' ',' '};
		
		int[] result = checkRow(test2);
		/*
		System.out.println("yellow doubles:  " + result[0]);
		System.out.println("yellow triples:  " + result[1]);
		System.out.println("   red doubles:  " + result[2]);
		System.out.println("   red triples:  " + result[3]);
		
		
		String name = test1[3].getClass().getName();
		
		System.out.println(name);
		*/
		
		
		
char board_t2 [][] = {
				
				{'r','r',' ',' ','y','r','y','r'},
				{'y','y',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ','r','y','r'},
				{'r',' ',' ',' ','y',' ',' ',' '},
				
				{' ',' ',' ',' ',' ',' ','y',' '},
				{'r','r',' ','r','y','r',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ','r'},
				{'y','r',' ','r','y','r',' ','y'}
				
		};	

		Node testNode= new Node();  // outside of c4 class, you need to add c4
		testNode.board=board_t2;
		/*
		
		System.out.println("value = " + value);
		System.out.println(2/4);
		*/
		
		
		
		
		
		
	}


	
	///////////////////
	 //     M A I N
	///////////////////bbmain
	
	public static void main(String[] args) {
		C4 c4=new C4();	//just to call the C4 constructor, which contains the main program
		c4.test();
		
		
	}

}//End of Class C4 