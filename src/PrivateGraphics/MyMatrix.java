package PrivateGraphics;


/**
 * @author Mark Dunlop based closely on Ian Ferguson's code
 * Implements a basic matrix that does multiplication - enough for graphics transformations
 */
public class MyMatrix {
	private int rows = 0;
	private int columns = 0;
	private double[][] values;

	// Constructors 
	public MyMatrix(int rows, int columns, double initialValueOfCells){
		this.rows = rows;
		this.columns = columns;
		if (rows == 0) throw new IllegalArgumentException("Matrix creation with 0 size matrix");
		if (columns == 0) throw new IllegalArgumentException("Matrix creation with 0 size matrix");
		values = new double[rows][columns];
		for (int i = 0; i<rows; i++)
			for (int j=0; j<columns; j++)
				values[i][j]=initialValueOfCells;
	}

	public MyMatrix(double[][] initialValues){
		rows = initialValues.length;
		if (rows == 0) throw new IllegalArgumentException("Matrix creation with 0 size matrix");
		columns = initialValues[0].length;
		if (columns == 0) throw new IllegalArgumentException("Matrix creation with 0 size matrix");
		for (int i = 0; i<rows; i++)
			if (initialValues[i].length!=columns)
				throw new IllegalArgumentException("Matrix creation with non-rectangular data");
		values=initialValues;
	}


	// Getters and Setters (Setters limited to contents not structure)

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public double getElement(int i, int j) {
		return values[i][j];
	}

	public void setElement(int i, int j, double value) {
		values[i][j]=value;
	}

	public double[][] getElements() {
		return values;
	}


	// Operations
	/**
	 * Multiplies two matrices together
	 * NB: Slightly different from Ian, made static for simplicity of code use
	 *   
	 * @param matrixA b - the two matrices
	 * @return a x b
	 */
	public static MyMatrix multiply(MyMatrix matrixA, MyMatrix matrixB){
		if (matrixA.columns!=matrixB.rows) throw new IllegalArgumentException("Matrices are not compatible for multiplication");

		//NB: do not need to worry about shape as illegal shapes cannot be created

		MyMatrix matrixAB = new MyMatrix(matrixA.rows, matrixB.columns,0);
		for (int i=0; i<matrixA.rows; i++){ //for each row of a/new
			for (int j=0; j<matrixB.columns; j++){ //for each column of b/new
				double sum = 0;
				for (int k=0; k<matrixB.rows; k++) //do mult and sum
					sum += matrixA.getElement(i, k)*matrixB.getElement(k, j);	
				matrixAB.setElement(i,j,sum);
			}
		}
		return matrixAB;
	}

	/**
	 * Transforms this matrix by essentially doing the calculation this = multiply(transformation, this)
	 * @param transformation
	 */
	public void transform(MyMatrix transformation){
		if ( transformation.getRows()!=transformation.getColumns())
			throw new IllegalArgumentException("Transformation matrix must be square");
		this.values = multiply(transformation, this).getElements();
	}

	// Utility methods
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer("[");
		for (int i = 0; i<rows; i++){
			sb.append(" [");
			for (int j=0; j<columns; j++)
				sb.append(" "+values[i][j]+" ");
			sb.append("] ");
		}
		return sb+"]";
	}

	public static void main(String[] args){// for testing only

		// examples from crib sheet 
		double[][] crib1vectorData = {{2},{4}}; 
		MyMatrix crib1vector = new MyMatrix(crib1vectorData);

		double[][] crib1matrixData = {{2,0},{1,3}};
		MyMatrix crib1matrix = new MyMatrix(crib1matrixData);

		System.out.println(multiply(crib1matrix, crib1vector));

		double[][] crib2Data = {{1,2,3},{4,5,6},{7,8,9}};
		MyMatrix crib2matrix = new MyMatrix(crib2Data);
		System.out.println(multiply(crib2matrix,crib2matrix));

		System.out.println("------------------------");

		//Basic Transformations

		System.out.println("Original vector "+crib1vector);

		double[][] doubleScale = {{2,0},{0,2}}; 
		MyMatrix matrix = new MyMatrix(doubleScale);
		System.out.println("Scale vector "+multiply(matrix, crib1vector));

		double[][] rotateAboutOrigin20 = {{0.939,-0.342},{0.342,0.939}}; 
		matrix = new MyMatrix(rotateAboutOrigin20);
		System.out.println("Rotate 20' around origin "+multiply(matrix, crib1vector));

		System.out.println("------------------------");

		//Homogeneous Transformations

		double[][] homovectorData = {{2},{4},{1}}; MyMatrix homovector = new MyMatrix(homovectorData);

		System.out.println("Original vector "+homovector);
		double[][] doubleScaleHdata = {{2,0,0},{0,2,0},{0,0,1}}; 
		MyMatrix doubleScaleHmatrix = new MyMatrix(doubleScaleHdata);
		System.out.println("Scale vector "+multiply(doubleScaleHmatrix, homovector));

		double[][] rotateAboutOrigin20Hdata = {{0.939,-0.342,0},{0.342,0.939,0},{0,0,1}};
		MyMatrix rotate20Hmatrix = new MyMatrix(rotateAboutOrigin20Hdata);
		System.out.println("Rotate 20' around origin "+multiply(rotate20Hmatrix, homovector));

		double[][] translate3x5Hdata = {{1,0,3},{0,1,5},{0,0,1}};
		MyMatrix translateHmatrix = new MyMatrix(translate3x5Hdata);
		System.out.println("translate by 3 horix and 5 vertical "+multiply(translateHmatrix, homovector));

		MyMatrix combination = multiply(translateHmatrix,doubleScaleHmatrix);// since (AB)C == A(BC)
		System.out.println("double then translate by 3,5 "+multiply(combination, homovector));

		MyMatrix combination2 = multiply(doubleScaleHmatrix,translateHmatrix);
		System.out.println("translate by 3,5 then double "+multiply(combination2, homovector));

	}
}
